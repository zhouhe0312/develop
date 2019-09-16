package cn.powertime.iatp.fileserver.controller;

import cn.powertime.iatp.authres.security.SecurityUtils;
import cn.powertime.iatp.commons.Constants;
import cn.powertime.iatp.commons.DateFormatUtil;
import cn.powertime.iatp.config.FileServerConfig;
import cn.powertime.iatp.core.base.BaseController;
import cn.powertime.iatp.entity.SysFile;
import cn.powertime.iatp.exception.FileServerException;
import cn.powertime.iatp.fileserver.util.PdfPptMp4Length;
import cn.powertime.iatp.service.SysFileService;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * <p>
 * 文件信息表 前端控制器
 * </p>
 *
 * @author ZYW
 * @since 2018-11-18
 */
@Slf4j
@RestController
@RequestMapping("/file")
public class SysFileController extends BaseController {

    private static final Map<String, String> FILE_TYPE = ImmutableMap.of("1", "课程或实验模板", "2", "试题上传模板", "3", "知识库内容模板");

    private static final Pattern PATTERN = Pattern.compile("bytes=(\\d*)-(\\d*)");


    @Autowired
    private SysFileService sysFileService;

    @Autowired
    private FileServerConfig fileServerConfig;

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    @PostMapping(value = "/upload")
    public Object upload(@RequestParam(value = "file", required = false) MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new FileServerException("上传文件的大小为空！");
        }
        if (file.getOriginalFilename().length() >= 50) {
            throw new FileServerException("上传文件名称过长，长度在50个字符以内！");
        }
        LocalDateTime now = LocalDateTime.now();
        String uploadFolder = now.format(DateTimeFormatter.ofPattern(DateFormatUtil.YYYYMMdd));

        String path = fileServerConfig.getPath();

        if (StringUtils.endsWith(path, Constants.PATH_SEPARATOR_LINUX)
                || StringUtils.endsWith(path, Constants.PATH_SEPARATOR_WIN)) {
            path = path + uploadFolder;
        } else {
            path = path + File.separator + uploadFolder;
        }

        File pathFile = new File(path);
        if (!pathFile.exists() && !pathFile.mkdirs()) {
            throw new FileServerException("创建文件夹失败！");
        }

        String origName = file.getOriginalFilename();
        String extName = FilenameUtils.getExtension(origName);
        String newFileName = now.format(DateTimeFormatter.ofPattern(DateFormatUtil.YYYYMMddHHmmssSSS)) + "." + extName;
        long size = file.getSize();
        String pageOrSecond = "";

        try {
            String targetPath = pathFile + File.separator + newFileName;
            file.transferTo(new File(targetPath));
            if(StringUtils.equalsIgnoreCase("pdf",extName)){
                pageOrSecond = String.valueOf(PdfPptMp4Length.getPdfPage(targetPath));
            } else if(StringUtils.equalsIgnoreCase("ppt",extName) || StringUtils.equalsIgnoreCase("pptx",extName)) {
                pageOrSecond = String.valueOf(PdfPptMp4Length.getPPTPage(targetPath));
            } else if(StringUtils.equalsIgnoreCase("mp4",extName)) {
                pageOrSecond = String.valueOf(PdfPptMp4Length.getMp4Time(targetPath));
            }
        } catch (IOException e) {
            throw new FileServerException("上传文件File为空！", e);
        }

        SysFile sysFile = new SysFile();
        sysFile.setId(IdWorker.getId());
        sysFile.setExt(extName);
        sysFile.setOrigName(origName);
        sysFile.setName(newFileName);
        sysFile.setPath(path);
        sysFile.setSize(Long.valueOf(size).intValue());
        sysFile.setCreateUser(SecurityUtils.getCurrentUserUsername());
        sysFile.setUpdateUser(SecurityUtils.getCurrentUserUsername());
        boolean result = sysFileService.save(sysFile);
        if (result) {
            return success(ImmutableMap.of("id", String.valueOf(sysFile.getId()), "type", extName,"length",pageOrSecond), "上传成功！");
        }
        return error("上传失败！");
    }

    @PostMapping(value = "/uploads")
    public Object uploads(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request)
                .getFiles("files");
        if (CollectionUtils.isEmpty(files)) {
            throw new FileServerException("上传文件为空！");
        }

        LocalDateTime now = LocalDateTime.now();
        String uploadFolder = now.format(DateTimeFormatter.ofPattern(DateFormatUtil.YYYYMMdd));

        String path = fileServerConfig.getPath();

        if (StringUtils.endsWith(path, Constants.PATH_SEPARATOR_LINUX)
                || StringUtils.endsWith(path, Constants.PATH_SEPARATOR_WIN)) {
            path = path + uploadFolder;
        } else {
            path = path + File.separator + uploadFolder;
        }

        File pathFile = new File(path);
        if (!pathFile.exists() && !pathFile.mkdirs()) {
            throw new FileServerException("创建文件夹失败！");
        }

        List<SysFile> sysFileList = Lists.newArrayList();
        SysFile sysFile = null;

        for (MultipartFile file : files) {
            String origName = file.getOriginalFilename();
            String extName = FilenameUtils.getExtension(origName);
            String newFileName = IdWorker.getIdStr() + "." + extName;
            long size = file.getSize();

            try {
                file.transferTo(new File(pathFile + File.separator + newFileName));
            } catch (IOException e) {
                throw new FileServerException("上传文件File为空！", e);
            }

            sysFile = new SysFile();
            sysFile.setId(IdWorker.getId());
            sysFile.setExt(extName);
            sysFile.setOrigName(origName);
            sysFile.setName(newFileName);
            sysFile.setPath(path);
            sysFile.setSize(Long.valueOf(size).intValue());
            sysFile.setStatus(Constants.STATUS_NORMAL);
            sysFile.setCreateUser(SecurityUtils.getCurrentUserUsername());
            sysFile.setUpdateUser(SecurityUtils.getCurrentUserUsername());
            sysFileList.add(sysFile);
        }

        boolean result = sysFileService.saveBatch(sysFileList);
        if (result) {
            return success(
                    sysFileList.stream()
                            .map(item -> ImmutableMap.of("id", String.valueOf(item.getId()), "type", item.getExt()))
                            .collect(Collectors.toList()),
                    "上传成功"
            );
        }
        return error("上传失败！");
    }

    @GetMapping("/download/{id}")
    @ApiOperation(value = "文件下载", notes = "下载上传的文件")
    public void downLoadFile(@PathVariable("id") String id, HttpServletResponse response) {
        if (StringUtils.isEmpty(id)) {
            throw new FileServerException("文件ID为空！");
        }
        String fileId = id;

        if (fileId.contains(Constants.DOT)) {
            fileId = Splitter.on(Constants.DOT).splitToList(fileId).get(0);
        }

        String downloadPath = "";
        String origName = "";

        if (StringUtils.isNumeric(fileId)) {
            SysFile sysFile = sysFileService.getById(fileId);
            if (sysFile == null) {
                throw new FileServerException(id + "不能查询到文件信息！");
            }

            final String fileName = sysFile.getName();
            final String filePath = sysFile.getPath();
            if (StringUtils.isEmpty(fileName) || StringUtils.isEmpty(filePath)) {
                throw new FileServerException("文件路径或者名称为空！");
            }
            downloadPath = filePath + File.separator + fileName;
            origName = sysFile.getOrigName();
        } else {
            String filePath = new String(Base64.decodeBase64(fileId));
            downloadPath = fileServerConfig.getPath() + File.separator + filePath;
            origName = FilenameUtils.getName(filePath);
        }
        download(response, downloadPath, origName);
    }

    private void download(HttpServletResponse response, String downloadPath, String origName) {
        try {
            // 设置response参数，可以打开下载页面
            response.reset();
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            String filename = URLEncoder.encode(origName, "UTF-8");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + new String(filename.getBytes("UTF-8"), "ISO-8859-1"));
            File file = new File(downloadPath);
            if (!file.exists()) {
                throw new FileServerException(downloadPath + "文件不存在！");
            }
            Files.copy(file.toPath(), response.getOutputStream());
        } catch (IOException e) {
            throw new FileServerException("下载文件异常！", e);
        }
    }

    @GetMapping("/video/{id}")
    @ApiOperation(value = "视频播放", notes = "视频播放")
    public void videoFile(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isEmpty(id)) {
            throw new FileServerException("文件ID为空！");
        }
        String fileId = id;

        if (fileId.contains(Constants.DOT)) {
            fileId = Splitter.on(Constants.DOT).splitToList(fileId).get(0);
        }

        SysFile sysFile = sysFileService.getById(fileId);
        if (sysFile == null) {
            throw new FileServerException(id + "不能查询到文件信息！");
        }

        final String fileName = sysFile.getName();
        final String filePath = sysFile.getPath();
        if (StringUtils.isEmpty(fileName) || StringUtils.isEmpty(filePath)) {
            throw new FileServerException("文件路径或者名称为空！");
        }

        String downloadPath = filePath + File.separator + fileName;
        String origName = FilenameUtils.getName(filePath);
        String rangeHeader = request.getHeader("Range");

        if (StringUtils.isEmpty(rangeHeader)) {
            download(response, downloadPath, origName);
        } else {
            try {
                File file = new File(downloadPath);

                if (!file.exists()) {
                    throw new FileServerException(downloadPath + "文件不存在！");
                }
                long length = file.length();


                // 设置response参数，可以打开下载页面
                response.reset();
                response.setContentType("video/mp4");
                response.setHeader("Accept-Ranges", "bytes");
                response.setHeader("Content-Length", String.valueOf(length));

                Matcher matcher = PATTERN.matcher(rangeHeader);
                String start = "";
                String end = "";
                if (matcher.find()) {
                    start = matcher.group(1);
                    end = matcher.group(2);
                }

                Map<String, Long> range = ImmutableMap.of("start"
                        , (StringUtils.isEmpty(start) ? 0 : Long.valueOf(start))
                        , "end"
                        , StringUtils.isEmpty(end) ? (length - 1) : Long.valueOf(end));

                if (StringUtils.isEmpty(start) && StringUtils.isNotEmpty(end)) {
                    range.put("start", length - range.get("end"));
                    range.put("end", (length - 1));
                }

                if (range.get("start") >= length || range.get("end") >= length) {
                    response.setHeader("Content-Range", "bytes */" + length);
                    response.setStatus(416);
                    response.flushBuffer();
                    return;
                }

                byte[] bytes = new byte[Long.valueOf(range.get("end") - range.get("start") + 1).intValue()];
                RandomAccessFile raf = new RandomAccessFile(file, "r");
                raf.seek(range.get("start"));
                raf.read(bytes, 0, bytes.length);
                response.setHeader("Content-Length", String.valueOf(bytes.length));
                response.setHeader("Content-Range", "bytes " + range.get("start") + "-" + range.get("end") + "/" + length);
                response.setStatus(206);
                response.getOutputStream().write(bytes);
                response.flushBuffer();
                raf.close();
            } catch (IOException e) {
                throw new FileServerException("视频播放异常！", e);
            }
        }
    }

    @GetMapping("/template/{no}")
    @ApiOperation(value = "模板下载", notes = "输入模板编号下载对应模板(1:课程或实验模板.xlsx,2:试题上传模板.xlsx,3:知识库内容模板.xlsx)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "no", value = "模板编号", required = true)
    })
    public void templateFile(@PathVariable("no") String no, HttpServletResponse response) {
        if (StringUtils.isBlank(no)) {
            throw new FileServerException("模板编号不能为空！");
        }
        try {
            // 设置response参数，可以打开下载页面
            response.reset();
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            String fileName = URLEncoder.encode(FILE_TYPE.get(no), "UTF-8");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + ".xlsx");
            String path = fileServerConfig.getPath();
            File file = new File(path + File.separator + "template" + File.separator + no + ".xlsx");
            if (!file.exists()) {
                throw new FileServerException("文件不存在！");
            }
            Files.copy(file.toPath(), response.getOutputStream());
        } catch (IOException e) {
            throw new FileServerException("下载文件异常！", e);
        }
    }

}
