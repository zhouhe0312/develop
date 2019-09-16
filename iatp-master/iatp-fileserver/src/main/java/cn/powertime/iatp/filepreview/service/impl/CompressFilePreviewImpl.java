package cn.powertime.iatp.filepreview.service.impl;

import cn.powertime.iatp.filepreview.model.FileAttribute;
import cn.powertime.iatp.filepreview.service.FilePreview;
import cn.powertime.iatp.filepreview.utils.Constants;
import cn.powertime.iatp.filepreview.utils.FileUtils;
import cn.powertime.iatp.filepreview.utils.ZipReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * Created by kl on 2018/1/17.
 * Content :处理压缩包文件
 */
@Service
public class CompressFilePreviewImpl implements FilePreview{

    @Autowired
    FileUtils fileUtils;

    @Autowired
    ZipReader zipReader;

    @Override
    public Map<String,Object> filePreviewHandle(String url, Map<String,Object> model) {
        FileAttribute fileAttribute=fileUtils.getFileAttribute(url);
        String fileName=fileAttribute.getName();
        String suffix=fileAttribute.getSuffix();
        String fileTree = null;
        // 判断文件名是否存在(redis缓存读取)
        if (!StringUtils.hasText(fileUtils.getConvertedFile(fileName))) {
            String filePath = fileAttribute.getPath();
            if ("zip".equalsIgnoreCase(suffix) || "jar".equalsIgnoreCase(suffix) || "gzip".equalsIgnoreCase(suffix)) {
                fileTree = zipReader.readZipFile(filePath, fileName);
            } else if ("rar".equalsIgnoreCase(suffix)) {
                fileTree = zipReader.unRar(filePath, fileName);
            } else if ("7z".equalsIgnoreCase(suffix)) {
                fileTree = zipReader.read7zFile(filePath, fileName);
            }
            if (fileTree != null && !"null".equals(fileTree)) {
                fileUtils.addConvertedFile(fileName, fileTree);
            }
        } else {
            fileTree = fileUtils.getConvertedFile(fileName);
        }

        if (fileTree != null && !"null".equals(fileTree)) {
            model.put("fileTree", fileTree);
            model.put("code", Constants.SUCCESS_CODE);
            return model;
        } else {
            model.put("fileType", suffix);
            model.put("message", "压缩文件类型不受支持，尝试在压缩的时候选择RAR4格式");
            model.put("code", Constants.FILE_NOT_SUPPORTED_CODE);
            return model;
        }
    }
}
