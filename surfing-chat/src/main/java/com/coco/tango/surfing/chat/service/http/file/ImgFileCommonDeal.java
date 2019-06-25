package com.coco.tango.surfing.chat.service.http.file;

import com.alibaba.fastjson.JSONObject;
import com.coco.tango.surfing.chat.config.HttpFileConfig;
import io.netty.handler.codec.http.multipart.FileUpload;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

/**
 * HTTP 图片 常用处理
 *
 * @author ckli01
 * @date 2019-06-25
 */
@Service
@Slf4j
public class ImgFileCommonDeal extends AbstractFileCommonDeal {


    @Autowired
    private HttpFileConfig httpFileConfig;




    private static final int THUMB_MAX_WIDTH = 100;
    private static final int THUMB_MAX_HEIGHT = 100;


    @Override
    public String savePath() {
        return httpFileConfig.getImgPath() + "/" + LocalDate.now().toString();
    }

    @Override
    public String requestPath(String fileName) {
        return httpFileConfig.getImgRequestPath() + "/" + LocalDate.now().toString() + "/" + fileName;
    }


    @Override
    protected JSONObject otherDeal(FileUpload fileUpload, String filePath, String uniqueBaseName, String extension) {
        JSONObject jsonObject = new JSONObject();

        if (isImageExtension(extension)) {
            String thumbname = createThumbnail(filePath, uniqueBaseName, extension);
            jsonObject.put("thumb", requestPath(thumbname));
        }
        jsonObject.put("normal", requestPath(uniqueBaseName + extension));
        return jsonObject;
    }


    /**
     * 压缩图片
     *
     * @param fileFullPath
     * @param fileNameBase
     * @param extension
     * @return
     */
    private String createThumbnail(String fileFullPath, String fileNameBase, String extension) {
        String thumbImgName = fileNameBase + "_thumb" + extension; // thumbnail image base name
        String thumbImageFullPath = savePath() + "/" + thumbImgName; // all thumbs are jpg files
        try {
            Thumbnails.of(new File(fileFullPath))
                    .size(500, 500)
                    .toFile(new File(thumbImageFullPath));
        } catch (IOException ex) {
            log.error("ImgFileCommonDeal createThumbnail error for : {}", ex.getMessage(), ex);
            thumbImgName = "";
        }
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


    private static BufferedImage resizeImage(BufferedImage originalImage, int type) {

        BufferedImage resizedImage = new BufferedImage(THUMB_MAX_WIDTH, THUMB_MAX_HEIGHT, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, THUMB_MAX_WIDTH, THUMB_MAX_HEIGHT, null);
        g.dispose();

        return resizedImage;
    }

}

    
    
  