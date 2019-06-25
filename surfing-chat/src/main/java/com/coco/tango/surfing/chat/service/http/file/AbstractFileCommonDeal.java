package com.coco.tango.surfing.chat.service.http.file;

import com.alibaba.fastjson.JSONObject;
import io.netty.handler.codec.http.multipart.FileUpload;

import java.io.File;
import java.io.IOException;

/**
 * HTTP 文件常用处理抽象类
 *
 * @author ckli01
 * @date 2019-06-25
 */
public abstract class AbstractFileCommonDeal implements FileCommonDeal {

    @Override
    public <T> T save(FileUpload fileUpload) throws IOException {
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

        // 保存文件 目录
        String directory = savePath();

        File file = new File(directory);
        if (!file.exists()) {
            file.mkdirs();
        }
        // 访问文件地址
//        jj.put("normal", requestPath(fileName));

        // 保存文件地址
        filePath = directory + "/" + fileName;
        // enable to move into another
        fileUpload.renameTo(new File(filePath));
        return otherDeal(fileUpload, filePath, uniqueBaseName,extension);
    }


    protected abstract <T> T otherDeal(FileUpload fileUpload, String filePath, String uniqueBaseName,String extension);


    /**
     * 保存 目录 地址
     *
     * @return
     */
    protected abstract String savePath();

    /**
     * 请求访问地址
     *
     * @param fileName
     * @return
     */
    protected abstract String requestPath(String fileName);


}

    
    
  