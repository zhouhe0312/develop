package cn.powertime.iatp.filepreview.service;

import java.util.Map;

/**
 * Created by kl on 2018/1/17.
 * Content :
 */
public interface FilePreview {
    /**
     * 转换文件到存储
     * @param url
     * @param model
     * @return
     */
    Map<String,Object> filePreviewHandle(String url, Map<String,Object> model);
}
