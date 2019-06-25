package com.coco.tango.surfing.chat.service.http.file;

import com.coco.tango.surfing.chat.config.HttpFileConfig;
import io.netty.handler.codec.http.multipart.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * 语音文件处理
 *
 * @author ckli01
 * @date 2019-06-25
 */
@Service
public class YYFileCommonDeal extends AbstractFileCommonDeal {


    @Autowired
    private HttpFileConfig httpFileConfig;


    @Override
    protected String otherDeal(FileUpload fileUpload, String filePath, String uniqueBaseName, String extension) {
        return requestPath(uniqueBaseName + extension);
    }

    @Override
    public String savePath() {
        return httpFileConfig.getYyPath() + "/" + LocalDate.now().toString();
    }

    @Override
    public String requestPath(String fileName) {
        return httpFileConfig.getYyRequestPath() + "/" + LocalDate.now().toString() + "/" + fileName;
    }
}

    
    
  