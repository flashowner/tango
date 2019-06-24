package com.coco.tango.surfing.chat.service.http;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.coco.tango.surfing.chat.bean.ChatMessage;
import com.coco.tango.surfing.chat.bootstrap.server.handler.AbstractHttpHandler;
import com.coco.tango.surfing.chat.constant.ChatMessageConstants;
import com.coco.tango.surfing.chat.service.ws.HandlerBaseService;
import com.coco.tango.surfing.chat.util.HttpRequestDealUtil;
import com.coco.tango.surfing.common.utils.SpringContextUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.multipart.*;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.util.CollectionUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * http 连接 处理
 *
 * @author ckli01
 * @date 2019-06-24
 */
@Slf4j
public class HttpChannelServiceImpl implements HttpChannelService {

    private static final HttpDataFactory factory = new DefaultHttpDataFactory(true);

    // query param used to download a file
    private static final String FILE_QUERY_PARAM = "file";

    private HttpPostRequestDecoder decoder;


    private static final int THUMB_MAX_WIDTH = 100;
    private static final int THUMB_MAX_HEIGHT = 100;

    private JSONObject jsonObject;

    /**
     * 文件类型
     */
    private String fileType;


    @Override
    public void uploadFile(ChannelHandlerContext ctx, FullHttpRequest request, String fileType) {
        try {
            this.fileType = fileType;
            decoder = new HttpPostRequestDecoder(factory, request);

            if (request instanceof HttpContent) {
                // New chunk is received
                HttpContent chunk = request;
                decoder.offer(chunk);
                // example of reading only if at the end
                if (chunk instanceof LastHttpContent) {
                    readHttpDataChunkByChunk(ctx);
                    reset();
                }
            } else {
                sendError(ctx, HttpResponseStatus.BAD_REQUEST, "Not a http request");
            }
        } catch (HttpPostRequestDecoder.ErrorDataDecoderException e) {
            log.error("HttpChannelServiceImpl uploadImgFile error for : {}", e.getMessage(), e);
            sendError(ctx, HttpResponseStatus.BAD_REQUEST, "Failed to decode file data");
            return;
        }

    }


    private void reset() {
        //request = null;
        // destroy the decoder to release all resources
        decoder.destroy();
        decoder = null;
    }

    /**
     * 一块一块循环遍历 获取 请求中的值
     *
     * @param ctx
     */
    private void readHttpDataChunkByChunk(ChannelHandlerContext ctx) {
        if (decoder.isMultipart()) {
            try {
                List<InterfaceHttpData> list = decoder.getBodyHttpDatas();
                if (!CollectionUtils.isEmpty(list)) {
                    for (InterfaceHttpData data : list) {
                        writeHttpData(data, ctx);
                        data.retain();
                    }
                    // todo 消息发送给接收人
                    ChatMessage chatMessage = JSONObject.parseObject(jsonObject.toJSONString(), ChatMessage.class);
                    HandlerBaseService handlerBaseService = SpringContextUtil.getBean(HandlerBaseService.class);
                    handlerBaseService.sendToText(chatMessage);
                }
            } catch (Exception e) {
                log.error("HttpChannelServiceImpl readHttpDataChunkByChunk error for : {}", e.getMessage(), e);
            }
        } else {
            sendError(ctx, HttpResponseStatus.BAD_REQUEST, "Not a multipart request");
        }
    }

    /**
     * 判断字段类型 文件 或者 参数 。。。
     *
     * @param data
     * @param ctx
     */
    private void writeHttpData(InterfaceHttpData data, ChannelHandlerContext ctx) throws IOException {
        if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute) {
            if (jsonObject == null) {
                jsonObject = new JSONObject();
            }
            Attribute attribute = (Attribute) data;
            // todo 获取 请求中 参数
            jsonObject.put(attribute.getName(), attribute.getValue());

        }

        if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.FileUpload) {
            if (jsonObject == null) {
                jsonObject = new JSONObject();
            }

            FileUpload fileUpload = (FileUpload) data;

            if (fileUpload.isCompleted()) {
                saveFileToDisk(fileUpload);
                sendUploadedFileName(jsonObject, ctx);
            } else {
                log.error("HttpChannelServiceImpl writeHttpData File to be continued but should not!");
                sendError(ctx, HttpResponseStatus.BAD_REQUEST, "Unknown error occurred");
            }
        }
    }

    /**
     * Saves the uploaded file to disk.
     *
     * @param fileUpload FileUpload object that'll be saved
     * @return name of the saved file. null if error occurred
     */
    private JSONObject saveFileToDisk(FileUpload fileUpload) {
        // full path of the new file to be saved
        String filePath = null;
        String upoadedFileName = fileUpload.getFilename();

        // get the extension of the uploaded file
        String extension = "";
        int i = upoadedFileName.lastIndexOf('.');
        if (i > 0) {
            // get extension including the "."
            extension = upoadedFileName.substring(i);
        }

        String uniqueBaseName = getUniqueId();
        String fileName = uniqueBaseName + extension;

        try {
            if (ChatMessageConstants.SEND_PICTURE.equals(fileType)) {
                // 图片
                JSONObject jj = new JSONObject();
                filePath = HttpRequestDealUtil.imgBasePath() + "/" + fileName;
                jj.put("normal", filePath);
                // enable to move into another
                fileUpload.renameTo(new File(filePath));
                if (isImageExtension(extension)) {
                    String thumbname = createThumbnail(filePath, uniqueBaseName, extension);
                    jj.put("thumb", HttpRequestDealUtil.imgBasePath() + "/" + thumbname);
                }
                jsonObject.put("value", jj);
            } else if (ChatMessageConstants.SEND_YY.equals(fileType)) {
                // 语音
                filePath = HttpRequestDealUtil.yyBasePath() + "/" + fileName;
                // enable to move into another
                fileUpload.renameTo(new File(filePath));
                jsonObject.put("value", fileName);
            }

        } catch (IOException | JSONException ex) {
            log.error("HttpChannelServiceImpl saveFileToDisk error for : {}", ex.getMessage(), ex);
        }

        return jsonObject;
    }

    private void sendUploadedFileName(JSONObject fileName, ChannelHandlerContext ctx) {
        String msg = "Unexpected error occurred";
        String contentType = "application/json; charset=UTF-8";
        HttpResponseStatus status = HttpResponseStatus.OK;

        if (fileName != null) {
            msg = fileName.toJSONString();
        } else {
            Logger.getLogger(AbstractHttpHandler.class.getName()).log(
                    Level.SEVERE, "uploaded file names are blank");
            status = HttpResponseStatus.BAD_REQUEST;
            contentType = "text/plain; charset=UTF-8";
        }

        sendResponse(ctx, msg, contentType, status);

    }

    private void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        sendError(ctx, status, "Failure: " + status.toString() + "\r\n");
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int type) {

        BufferedImage resizedImage = new BufferedImage(THUMB_MAX_WIDTH, THUMB_MAX_HEIGHT, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, THUMB_MAX_WIDTH, THUMB_MAX_HEIGHT, null);
        g.dispose();

        return resizedImage;
    }

    /**
     * Creates a thumbnail of an image file
     *
     * @param fileFullPath full path of the source image
     * @param fileNameBase Base name of the file i.e without extension
     */
    private String createThumbnail(String fileFullPath, String fileNameBase, String extension) {
        String thumbImgName = fileNameBase + "_thumb" + extension; // thumbnail image base name
        String thumbImageFullPath = HttpRequestDealUtil.imgBasePath() + "/" + thumbImgName; // all thumbs are jpg files

        try {
            Thumbnails.of(new File(fileFullPath))
                    .size(100, 100)
                    .toFile(new File(thumbImageFullPath));
        } catch (IOException ex) {
            Logger.getLogger(AbstractHttpHandler.class.getName()).log(Level.SEVERE, null, ex);
            thumbImgName = "";
        }

//        Logger.getLogger(HttpStaticFileServerHandler.class.getName()).log(Level.SEVERE, null,
//                "Creating thumbnail of image " + fileFullPath);
//
//        //Scalr.resize(null, THUMB_MAX_WIDTH, null);
//        try {
//            BufferedImage img = ImageIO.read(new File(fileFullPath));
//            BufferedImage scaledImg = Scalr.resize(img, THUMB_MAX_WIDTH);
//
//            //BufferedImage scaledImg = Scalr.resize(img, Mode.AUTOMATIC, 640, 480);
//            File destFile = new File(thumbImageFullPath);
//
//            ImageIO.write(scaledImg, "jpg", destFile);
//        } catch (ImagingOpException | IOException | IllegalArgumentException e) {
//            Logger.getLogger(HttpStaticFileServerHandler.class.getName()).log(Level.SEVERE, null, e);
//            e.printStackTrace();
//            System.out.println(e.toString());
//            thumbImgName = "";
//        }

        return thumbImgName;

    }


    /**
     * 判断是否是图片 后缀
     *
     * @param extension
     * @return
     */
    private static boolean isImageExtension(String extension) {
        boolean isImageFile = false;
        String extensionInLowerCase = extension.toLowerCase();

        isImageFile = extensionInLowerCase.equals(".jpg");
        isImageFile |= extensionInLowerCase.equals(".png");
        isImageFile |= extensionInLowerCase.equals(".jpeg");
        isImageFile |= extensionInLowerCase.equals(".gif");

        return isImageFile;
    }


    /**
     * generates and returns a unique string that'll be used to save an uploaded
     * file to disk
     *
     * @return generated unique string
     */
    private String getUniqueId() {
        UUID uniqueId = UUID.randomUUID();
        return uniqueId.toString();
    }

}




