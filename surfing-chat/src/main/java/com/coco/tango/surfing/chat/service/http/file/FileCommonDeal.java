package com.coco.tango.surfing.chat.service.http.file;

import io.netty.handler.codec.http.multipart.FileUpload;

import java.io.IOException;
import java.util.UUID;

/**
 * HTTP 文件常用处理
 *
 * @author ckli01
 * @date 2019-06-25
 */
public interface FileCommonDeal {


    /**
     * 保存文件
     *
     * @param fileUpload
     * @param <T>
     * @return
     */
    <T> T save(FileUpload fileUpload) throws IOException;


    /**
     * 文件唯一编码
     *
     * @return
     */
    default String getUniqueId() {
        UUID uniqueId = UUID.randomUUID();
        return uniqueId.toString();
    }

}
