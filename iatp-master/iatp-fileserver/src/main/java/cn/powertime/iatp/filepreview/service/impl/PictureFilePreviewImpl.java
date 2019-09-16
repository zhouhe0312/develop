package cn.powertime.iatp.filepreview.service.impl;

import cn.powertime.iatp.filepreview.service.FilePreview;
import cn.powertime.iatp.filepreview.utils.Constants;
import cn.powertime.iatp.filepreview.utils.FileUtils;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.List;
import java.util.Map;

/**
 * Created by kl on 2018/1/17.
 * Content :图片文件处理
 */
@Service
public class PictureFilePreviewImpl implements FilePreview {

    @Autowired
    FileUtils fileUtils;

    @Override
    public Map<String,Object> filePreviewHandle(String url, Map<String,Object> model) {
        String fileKey=(String) RequestContextHolder.currentRequestAttributes().getAttribute("fileKey",0);
        List imgUrls = Lists.newArrayList(url);
        try{
            imgUrls.clear();
            imgUrls.addAll(fileUtils.getRedisImgUrls(fileKey));
        }catch (Exception e){
            imgUrls = Lists.newArrayList(url);
        }
        model.put("imgUrls", imgUrls);
        model.put("currentUrl",url);
        model.put("code", Constants.SUCCESS_CODE);
        model.put("type", "picture");
//        return "picture";
        return model;
    }
}
