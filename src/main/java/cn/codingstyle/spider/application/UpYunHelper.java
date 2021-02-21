package cn.codingstyle.spider.application;

import cn.codingstyle.spider.FileUtil;
import com.UpYun;
import com.alibaba.fastjson.JSON;
import com.upyun.UpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import static java.lang.String.format;

@Component
@Slf4j
public class UpYunHelper {
    private final FileUtil fileUtil;
    private final UpYunConfig upYunConfig;

    public UpYunHelper(FileUtil fileUtil, UpYunConfig upYunConfig) {
        this.fileUtil = fileUtil;
        this.upYunConfig = upYunConfig;
    }

    public void uploadFile(String currentYear, String url, String fileName) {
        try {
            boolean result = upload(currentYear, url, fileName);
            log.info("上传又拍云结果:{}", result);
        } catch (Exception e) {
            String errorMsg = format("上传又拍云失败: %s, url: %s", e.getMessage(), url);
            log.error(errorMsg, e);
            throw new RuntimeException(errorMsg);
        }
    }

    private boolean upload(String currentYear, String url, String fileName) throws Exception {
        File file = fileUtil.downloadFile(url, fileName);
        boolean result = upload(file, "/article/photo/" + currentYear + "/" + fileName);
        file.delete();
        return result;
    }

    private boolean upload(File file, String uploadFilePath) throws IOException, UpException {
        log.info("又拍云账号:\n{}", JSON.toJSONString(upYunConfig));
        UpYun upyun = new UpYun(
            upYunConfig.getBucketName(),
            upYunConfig.getUsername(),
            upYunConfig.getPassword());
        // 可选属性1，是否开启 debug 模式，默认不开启
        upyun.setDebug(false);
        // 可选属性2，超时时间，默认 30s
        upyun.setTimeout(30);
        //计算文件 MD5，如果文件太大或计算不便，可以不计算
        upyun.setContentMD5(UpYun.md5(file));
        // 文件上传
        return upyun.writeFile(uploadFilePath, file);
    }

    public void uploadFile2(String sourceUrl, String fileName) {
        File file = null;
        try {
            file = fileUtil.downloadFile(sourceUrl);
            String uploadFilePath = "/article/photo/" + String.valueOf(LocalDate.now().getYear()) + "/" + fileName;
            boolean result = upload(file, uploadFilePath);
            if (!result) {
                throw new RuntimeException("upload file to UpYun Failed");
            }
        } catch (Exception e) {
            String errorMsg = format("上传图片失败: source url: %s", fileName);
            log.error(errorMsg, e);
            throw new RuntimeException(errorMsg);
        } finally {
            if (file != null) {
                file.delete();
            }
        }

    }
}
