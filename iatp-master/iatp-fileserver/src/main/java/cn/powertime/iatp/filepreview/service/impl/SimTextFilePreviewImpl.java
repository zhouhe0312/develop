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
 * Content :处理文本文件
 */
@Service
public class SimTextFilePreviewImpl implements FilePreview{

    @Autowired
    FileUtils fileUtils;

    @Override
    public Map<String,Object> filePreviewHandle(String url, Map<String,Object> model){
        FileAttribute fileAttribute=fileUtils.getFileAttribute(url);
        model.put("ordinaryUrl", fileAttribute.getPath());
        model.put("code", Constants.SUCCESS_CODE);
        model.put("type", "txt");
//        return "txt";
        return model;
    }

}
