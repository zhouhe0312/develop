package cn.powertime.iatp.filepreview.service.impl;

import cn.powertime.iatp.filepreview.model.FileAttribute;
import cn.powertime.iatp.filepreview.service.FilePreview;
import cn.powertime.iatp.filepreview.utils.Constants;
import cn.powertime.iatp.filepreview.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author : kl
 * @authorboke : kailing.pub
 * @create : 2018-03-25 上午11:58
 * @description:
 **/
@Service
public class MediaFilePreviewImpl implements FilePreview {

    @Autowired
    FileUtils fileUtils;

    @Override
    public Map<String,Object> filePreviewHandle(String url, Map<String,Object> model) {
        model.put("mediaUrl", url);
        FileAttribute fileAttribute=fileUtils.getFileAttribute(url);
        model.put("code", Constants.SUCCESS_CODE);
        String suffix=fileAttribute.getSuffix();
        if ("flv".equalsIgnoreCase(suffix)) {
            model.put("type","flv");
            return model;
        }
        model.put("type","other");
        return model;
    }


}
