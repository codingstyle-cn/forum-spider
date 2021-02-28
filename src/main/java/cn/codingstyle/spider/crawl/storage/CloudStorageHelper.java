package cn.codingstyle.spider.crawl.storage;

import cn.codingstyle.spider.FileUtil;
import com.UpYun;
import com.alibaba.fastjson.JSON;
import com.upyun.UpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

import static java.lang.String.format;

@Component
@Slf4j
public class CloudStorageHelper {
    private final FileUtil fileUtil;
    private final UpYunConfig upYunConfig;

    public CloudStorageHelper(FileUtil fileUtil, UpYunConfig upYunConfig) {
        this.fileUtil = fileUtil;
        this.upYunConfig = upYunConfig;
    }

    public void uploadFile(String sourceUrl, String uploadFilePath) {
        File file = null;
        try {
            file = fileUtil.downloadFile(addProtocolPrefix(sourceUrl));
            boolean result = upload(file, uploadFilePath);
            if (!result) {
                throw new RuntimeException("upload file to UpYun Failed");
            }
        } catch (Exception e) {
            String errorMsg = format("上传图片失败: source url: %s,uploadFilePath:%s", sourceUrl, uploadFilePath);
            log.error(errorMsg, e);
            throw new RuntimeException(errorMsg);
        } finally {
            if (file != null) {
                file.delete();
            }
        }
    }

    private String addProtocolPrefix(String url) {
        return url.startsWith("http") ? url : String.format("https:%s", url);
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
}
