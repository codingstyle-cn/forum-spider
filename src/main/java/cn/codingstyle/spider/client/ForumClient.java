package cn.codingstyle.spider.client;


import feign.Logger;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "forumClient", url = "${forum.host}", configuration = ForumClient.FormSupportConfig.class)
public interface ForumClient {
    @RequestMapping(method = RequestMethod.POST, value = "/oauth/token")
    AccessToken getToken(@RequestParam(defaultValue = "password") String grant_type,
                         @RequestParam String username,
                         @RequestParam String password,
                         @RequestParam String client_id,
                         @RequestParam String client_secret);


    @PostMapping(value = "/api/v3/topics",
        consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
        produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}
    )
    void syncAticle(Map<String, ?> queryParam);

    class FormSupportConfig {
        @Autowired
        private ObjectFactory<HttpMessageConverters> messageConverters;

        // new一个form编码器，实现支持form表单提交
        @Bean
        public Encoder feignFormEncoder() {
            return new SpringFormEncoder(new SpringEncoder(messageConverters));
        }

        // 开启Feign的日志
        @Bean
        public Logger.Level logger() {
            return Logger.Level.FULL;
        }
    }
}
