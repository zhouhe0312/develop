package cn.powertime.iatp.filepreview.service;

import cn.powertime.iatp.filepreview.model.FileAttribute;
import cn.powertime.iatp.filepreview.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by kl on 2018/1/17.
 * Content :
 */
@Service
public class FilePreviewFactory {

    @Autowired
    FileUtils fileUtils;

    @Autowired
    ApplicationContext context;

    public FilePreview get(String uri) {
        Map<String, FilePreview> filePreviewMap = context.getBeansOfType(FilePreview.class);
        FileAttribute fileAttribute = fileUtils.getFileAttribute(uri);
        return filePreviewMap.get(fileAttribute.getType().getInstanceName());
    }
}
