package cn.codingstyle.spider.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "up-yun", ignoreUnknownFields = false)
@Component
public class UpYunConfig {
    private String bucketName;
    private String username;
    private String password;

}
