package cn.codingstyle.spider.crawl;

import java.util.List;

public class ArticleContentModifiers {
    private List<ArticleContentModifier> articleContentModifiers;

    public ArticleContentModifiers(List<ArticleContentModifier> articleContentModifiers) {
        this.articleContentModifiers = articleContentModifiers;
    }

    public String modify(CrawlOriginalData data) {
        ArticleContentModifier modifier = getArticleContentModifier(data.getSource());
        return modifier.modify(data.getContent(), data.getImageUrls());
    }

    private ArticleContentModifier getArticleContentModifier(String source) {
        return articleContentModifiers.stream()
                .filter(modifier -> modifier.getSource().equals(source))
                .findFirst()
                .get();
    }
}
