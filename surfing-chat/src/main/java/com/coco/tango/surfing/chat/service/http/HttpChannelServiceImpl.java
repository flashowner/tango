package com.coco.tango.surfing.chat.service.http;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.coco.tango.surfing.chat.bootstrap.server.handler.AbstractHttpHandler;
import com.coco.tango.surfing.chat.constant.ChatMessageConstants;
import com.coco.tango.surfing.chat.service.http.file.ImgFileCommonDeal;
import com.coco.tango.surfing.chat.service.http.file.YYFileCommonDeal;
import com.coco.tango.surfing.chat.service.ws.HandlerBaseService;
import com.coco.tango.surfing.common.utils.SpringContextUtil;
import com.coco.tango.surfing.core.dal.domain.chat.ChatMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.multipart.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;
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

    private HttpPostRequestDecoder decoder;

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
                    //  消息发送给接收人
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
     * 保存图片到服务器
     *
     * @param fileUpload
     * @return
     */
    private JSONObject saveFileToDisk(FileUpload fileUpload) {
        try {
            if (ChatMessageConstants.SEND_PICTURE.equals(fileType)) {
                JSONObject jj = SpringContextUtil.getBean(ImgFileCommonDeal.class).save(fileUpload);
                jsonObject.put("value", jj);
            } else if (ChatMessageConstants.SEND_YY.equals(fileType)) {

                String fileName = SpringContextUtil.getBean(YYFileCommonDeal.class).save(fileUpload);
                jsonObject.put("value", fileName);
            }

        } catch (IOException | JSONException ex) {
            log.error("HttpChannelServiceImpl saveFileToDisk error for : {}", ex.getMessage(), ex);
        }

        return jsonObject;
    }

    /**
     * 返回上传数据信息
     *
     * @param fileName
     * @param ctx
     */
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


}




