package cn.powertime.iatp.filepreview.service.impl;

import cn.powertime.iatp.filepreview.config.ConfigConstants;
import cn.powertime.iatp.filepreview.model.FileAttribute;
import cn.powertime.iatp.filepreview.service.FilePreview;
import cn.powertime.iatp.filepreview.utils.Constants;
import cn.powertime.iatp.filepreview.utils.FileUtils;
import cn.powertime.iatp.filepreview.utils.OfficeToPdf;
import cn.powertime.iatp.filepreview.utils.PdfUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by kl on 2018/1/17.
 * Content :处理office文件
 */
@Service
public class OfficeFilePreviewImpl implements FilePreview {

    @Autowired
    FileUtils fileUtils;

    @Autowired
    PdfUtils pdfUtils;

    @Autowired
    private OfficeToPdf officeToPdf;

    @Override
    public Map<String,Object> filePreviewHandle(String uri, Map<String,Object> model) {
        // 预览Type，参数传了就取参数的，没传取系统默认
        Object type = model.get("officePreviewType");
        String officePreviewType = type == null ? ConfigConstants.getOfficePreviewType() : String.valueOf(type);
        FileAttribute fileAttribute = fileUtils.getFileAttribute(uri);
        String suffix = fileAttribute.getSuffix();
        boolean isHtml = suffix.equalsIgnoreCase("xls") || suffix.equalsIgnoreCase("xlsx");
        String pdfName = fileAttribute.getFileId() + "." + (isHtml ? "html" : "pdf");
        String pdfOutFilePath = ConfigConstants.getFileDir() + pdfName;
        // 判断之前是否已转换过，如果转换过，直接返回，否则执行转换
        if (!fileUtils.listConvertedFiles().containsKey(fileAttribute.getFileId())) {
            String filePath = fileAttribute.getPath();
            if (StringUtils.isNotBlank(pdfOutFilePath)) {
                officeToPdf.openOfficeToPDF(filePath, pdfOutFilePath);
                if (isHtml) {
                    // 对转换后的文件进行操作(改变编码方式)
                    fileUtils.doActionConvertedFile(pdfOutFilePath);
                }
                // 加入缓存
                fileUtils.addConvertedFile(fileAttribute.getFileId(), fileUtils.getRelativePath(pdfOutFilePath));
            }
        }
        if (!isHtml
                && StringUtils.equals(Constants.OFFICE_PREVIEW_TYPE_IMAGE,officePreviewType)
                || StringUtils.equals(Constants.OFFICE_PREVIEW_TYPE_ALLIMAGES,officePreviewType)) {

            String outPath = ConfigConstants.getFileDir() + fileAttribute.getFileId();
            List<String> imageUrls = pdfUtils.pdf2jpg(pdfOutFilePath,outPath, fileAttribute.getFileId());
            if (imageUrls == null || imageUrls.size() < 1) {
                model.put("message", "office转图片异常，请联系管理员");
                model.put("fileType", fileAttribute.getSuffix());
                model.put("code", Constants.FILE_NOT_SUPPORTED_CODE);
                return model;
            }

            model.put("imgUrls", imageUrls);
            model.put("currentUrl", imageUrls.get(0));
            model.put("code", Constants.SUCCESS_CODE);

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
        model.put("pdfUrl", pdfName);
//        return isHtml ? "html" : "pdf";
        model.put("type",(isHtml ? "html" : "pdf"));
        return model;
    }
}
