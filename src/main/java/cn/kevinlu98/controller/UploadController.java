package cn.kevinlu98.controller;

import cn.kevinlu98.common.Result;
import cn.kevinlu98.enums.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;


/**
 * Author: Mr丶冷文
 * Date: 2022/10/8 11:24
 * Email: kevinlu98@qq.com
 * Description:
 */
@Slf4j
@RestController
@RequestMapping("/admin/")
public class UploadController {

    @Value("${upload.base-dir}")
    private String baseDir;

    private static final DateFormat DATA_FORMAT = new SimpleDateFormat("yyyyMMdd/");

    /**
     * editor.md的规定的返回格式
     */
    @Data
    @AllArgsConstructor
    static class MDResult {
        private Integer success;
        private String message;
        private String url;
    }

    @PostMapping("/md/upload")
    public MDResult mdUpload(@RequestParam(value = "editormd-image-file") MultipartFile file) {
        try {
            String url = uploadFile(file);
            return new MDResult(1, "上传成功", url);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return new MDResult(0, ResultEnum.RESULT_UPLOAD_FAIL.getMessage(), null);
        }
    }

    @PostMapping("/upload")
    public Result<String> upload(@RequestParam MultipartFile file) {
        try {
            String url = uploadFile(file);
            return Result.success(url);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return Result.error(ResultEnum.RESULT_UPLOAD_FAIL);
        }
    }

    private String uploadFile(MultipartFile file) throws IOException {
        if (!baseDir.endsWith("/")) {
            baseDir += "/";
        }
        // 生成时间分区的目录
        String dateName = DATA_FORMAT.format(new Date());
        String folderPath = baseDir + dateName;
        File folder = new File(folderPath);
        if (!folder.isDirectory()) {
            folder.mkdirs();
        }
        //取上传目录的绝对路径
        String absPath = folder.getAbsolutePath().replace("./", "") + "/";
        //取文件后缀
        String suffix = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."), file.getOriginalFilename().length());
        //生成文件名
        String filename = UUID.randomUUID().toString().replace("-", "") + suffix;
        //上传到服务器
        file.transferTo(new File(absPath + "/" + filename));
        return "/upload/" + dateName + "/" + filename;
    }
}
