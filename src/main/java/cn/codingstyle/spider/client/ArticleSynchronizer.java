package cn.codingstyle.spider.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ArticleSynchronizer {

    @Value("${forum.clientId}")
    private String clientId;
    @Value("${forum.clientPassword}")
    private String clientPassword;
    @Value("${forum.username}")
    private String username;
    @Value("${forum.password}")
    private String password;

    private final ForumClient forumClient;

    public ArticleSynchronizer(ForumClient forumClient) {
        this.forumClient = forumClient;
    }

    public void sync(String subject, String content) {
        String token = getToken();
        syncArticle(subject, content, token);
    }

    private String getToken() {
        AccessToken token = forumClient.getToken("password", username, this.password, clientId, clientPassword);
        return token.getAccess_token();
    }

    private void syncArticle(String subject, String content, String token) {
        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put("access_token", token);
        queryParam.put("title", subject);
        queryParam.put("node_id", 16);
        queryParam.put("body", content);
        forumClient.syncAticle(queryParam);
    }
}
