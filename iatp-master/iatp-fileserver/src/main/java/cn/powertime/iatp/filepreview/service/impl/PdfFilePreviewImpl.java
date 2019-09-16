package cn.powertime.iatp.filepreview.service.impl;

import cn.powertime.iatp.filepreview.config.ConfigConstants;
import cn.powertime.iatp.filepreview.model.FileAttribute;
import cn.powertime.iatp.filepreview.service.FilePreview;
import cn.powertime.iatp.filepreview.utils.Constants;
import cn.powertime.iatp.filepreview.utils.FileUtils;
import cn.powertime.iatp.filepreview.utils.PdfUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by kl on 2018/1/17.
 * Content :处理pdf文件
 */
@Service
public class PdfFilePreviewImpl implements FilePreview{


    @Autowired
    FileUtils fileUtils;

    @Autowired
    PdfUtils pdfUtils;

    @Override
    public Map<String,Object> filePreviewHandle(String uri, Map<String,Object> model) {
        FileAttribute fileAttribute = fileUtils.getFileAttribute(uri);
        Object type = model.get("officePreviewType");
        String officePreviewType = type == null ? ConfigConstants.getOfficePreviewType() : String.valueOf(type);
        model.put("pdfUrl", uri);
        String outPath = ConfigConstants.getFileDir() + fileAttribute.getFileId();
        if (StringUtils.equals(Constants.OFFICE_PREVIEW_TYPE_IMAGE,officePreviewType)
                        || StringUtils.equals(Constants.OFFICE_PREVIEW_TYPE_ALLIMAGES,officePreviewType)) {
            List<String> imageUrls = pdfUtils.pdf2jpg(fileAttribute.getPath(),outPath, fileAttribute.getFileId());
            if (imageUrls == null || imageUrls.size() < 1) {
                model.put("message", "pdf转图片异常，请联系管理员");
                model.put("fileType",fileAttribute.getSuffix());
                model.put("code", Constants.FILE_NOT_SUPPORTED_CODE);
                return model;
            }
            model.put("imgUrls", imageUrls);
            model.put("currentUrl", imageUrls.get(0));
            model.put("code",Constants.SUCCESS_CODE);
            if (StringUtils.equals(Constants.OFFICE_PREVIEW_TYPE_IMAGE,officePreviewType)) {
//                return "officePicture";
                model.put("type","officePicture");
                return model;
            } else {
//                return "picture";
                model.put("type","picture");
                return model;
            }
        }
//        return "pdf";
        model.put("type","pdf");
        return model;
    }
}
