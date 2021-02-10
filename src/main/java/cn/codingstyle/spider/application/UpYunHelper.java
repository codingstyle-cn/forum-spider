package cn.codingstyle.spider.application;

import cn.codingstyle.spider.FileUtil;
import com.UpYun;
import com.alibaba.fastjson.JSON;
import com.upyun.UpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
@Slf4j
public class UpYunHelper {
    private final FileUtil fileUtil;
    private final UpYunConfig upYunConfig;

    public UpYunHelper(FileUtil fileUtil, UpYunConfig upYunConfig) {
        this.fileUtil = fileUtil;
        this.upYunConfig = upYunConfig;
    }

    public void uploadFile2(String currentYear, String url, String fileName) {
        try {
            boolean result = upload2(url, fileName, "/article/photo/" + currentYear + "/" + fileName);
            log.info("上传又拍云结果:{}", result);
        } catch (Exception e) {
            log.error("上传又拍云失败:error:{}", e.getMessage(), e);
        }
    }

    private boolean upload2(String currentYear, String url, String fileName) throws Exception {
        File file = fileUtil.downloadFile(url,fileName);
        boolean result = upload(file, "/article/photo/" + currentYear + "/" + fileName);
        file.delete();
        return result;
    }

    public void uploadFile(String currentYear, String url, String fileName) {
        try {
            boolean result = upload("/article/photo/" + currentYear + "/" + fileName, "https:" + url);
            log.info("上传又拍云结果:{}", result);
        } catch (Exception e) {
            log.error("上传又拍云失败:error:{}", e.getMessage(), e);
        }
    }
    public void uploadFile(String url, String path) {
        try {
            boolean result = upload(url, path);
            log.info("上传又拍云结果:{}", result);
        } catch (Exception e) {
            log.error("上传又拍云失败:error:{}", e.getMessage(), e);
        }
    }

//    private boolean processFile(String url, String path) throws Exception {
//        File file = fileUtil.downloadFile("https:" + url);
//
//        return false;
//    }

    private boolean upload(String filePath, String url) throws Exception {
        File file = fileUtil.downloadFile(url);
        boolean result = upload(file, filePath);
        file.delete();
        return result;
    }

    private boolean upload(File file, String filePath) throws IOException, UpException {
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
        return upyun.writeFile(filePath, file);
    }
}
