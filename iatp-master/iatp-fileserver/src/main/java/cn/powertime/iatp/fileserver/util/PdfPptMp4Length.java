package cn.powertime.iatp.fileserver.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaInfo;
import ws.schild.jave.MultimediaObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author ZYW
 */
@Slf4j
public class PdfPptMp4Length {

    public static int getPdfPage(String filePath){
        try {
            File pdfFile = new File(filePath);
            PDDocument doc = PDDocument.load(pdfFile);
            return doc.getNumberOfPages();
        } catch (IOException e) {
            log.error("获得PDF页数异常，",e);
        }
        return 0;
    }

    public static int getPdfPage(InputStream input){
        try {
            PDDocument doc = PDDocument.load(input);
            return doc.getNumberOfPages();
        } catch (IOException e) {
            log.error("获得PDF页数异常，",e);
        }
        return 0;
    }

    /**
     * 计算PPT格式文档的页数
     * @param filePath 文件路径
     * @return pages 页数
     */
    public static int getPPTPage(String filePath) {
        try {
            String extension = FilenameUtils.getExtension(filePath);
            FileInputStream fis = new FileInputStream(filePath);
            if(StringUtils.equalsIgnoreCase("ppt",extension)) {
                SlideShow slideShow = new SlideShow(fis);
                return slideShow.getSlides().length;
            }

            XMLSlideShow pptxFile = new XMLSlideShow(fis);
            return pptxFile.getSlides().length;
        } catch (IOException e) {
            log.error("获得PPT页数异常，",e);
        }
        return 0;
    }


    public static long getMp4Time(String filePath) {
        try {
            File source = new File(filePath);
            MultimediaObject instance = new MultimediaObject(source);
            MultimediaInfo result = instance.getInfo();
            return result.getDuration() / 1000;
        } catch (EncoderException e) {
            log.error("获得视频秒数异常，",e);
        }
        return 0;
    }
}
