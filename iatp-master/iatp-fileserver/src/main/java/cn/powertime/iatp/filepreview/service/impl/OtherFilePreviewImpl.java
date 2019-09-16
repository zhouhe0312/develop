package cn.powertime.iatp.filepreview.service.impl;

import cn.powertime.iatp.filepreview.model.FileAttribute;
import cn.powertime.iatp.filepreview.service.FilePreview;
import cn.powertime.iatp.filepreview.utils.Constants;
import cn.powertime.iatp.filepreview.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by kl on 2018/1/17.
 * Content :其他文件
 */
@Service
public class OtherFilePreviewImpl implements FilePreview {
    @Autowired
    FileUtils fileUtils;

    @Override
    public Map<String,Object> filePreviewHandle(String url, Map<String,Object> model) {
        FileAttribute fileAttribute=fileUtils.getFileAttribute(url);

        model.put("fileType",fileAttribute.getSuffix());
        model.put("message", "系统还不支持该格式文件的在线预览，" +
                "如有需要请按下方显示的邮箱地址联系系统维护人员");
        model.put("code", Constants.FILE_NOT_SUPPORTED_CODE);
        return model;
    }
}
