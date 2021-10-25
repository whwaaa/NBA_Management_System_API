package com.jumaojiang.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * ssm_api
 *
 * @author wuhanwei
 * @version 1.0
 * @date 2021/10/23
 */
@Component
public class FileUtil {
    /**
     * 生成文件名称
     * @param file
     * @return
     */
    public static String fileRename(MultipartFile file){
        String uuid = UUID.randomUUID().toString().replace("-","");
        return uuid + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
    }
}
