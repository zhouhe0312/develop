package cn.powertime.iatp.filepreview.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PdfUtils {

    private final Logger LOGGER = LoggerFactory.getLogger(PdfUtils.class);

    @Autowired
    FileUtils fileUtils;

    public List<String> pdf2jpg(String filePath,String outPath, String fileId) {
        List<String> imageUrls = new ArrayList<>();
        Integer imageCount = fileUtils.getConvertedPdfImage(fileId);
        String imageFileSuffix = ".jpg";
        String urlPrefix = "file/" + fileId;
        if (imageCount != null && imageCount > 0) {
            for (int i = 0; i < imageCount ; i++) {
                String base64String = Base64.encodeBase64String((urlPrefix + "/" + i + imageFileSuffix).getBytes());
                imageUrls.add(base64String);
            }
            return imageUrls;
        }
        try {
            File pdfFile = new File(filePath);
            PDDocument doc = PDDocument.load(pdfFile);
            int pageCount = doc.getNumberOfPages();
            PDFRenderer pdfRenderer = new PDFRenderer(doc);

            File path = new File(outPath);
            if (!path.exists()) {
                path.mkdirs();
            }
            String imageFilePath;
            for (int pageIndex = 0; pageIndex < pageCount; pageIndex++) {
                imageFilePath = outPath + File.separator + pageIndex + imageFileSuffix;
                BufferedImage image = pdfRenderer.renderImageWithDPI(pageIndex, 105, ImageType.RGB);
                ImageIOUtil.writeImage(image, imageFilePath, 105);
                String base64String = Base64.encodeBase64String((urlPrefix + "/" + pageIndex + imageFileSuffix).getBytes());
                imageUrls.add(base64String);
            }
            doc.close();
            fileUtils.addConvertedPdfImage(fileId, pageCount);
        } catch (IOException e) {
            LOGGER.error("Convert pdf to jpg exception", e);
        }
        return imageUrls;
    }
}
