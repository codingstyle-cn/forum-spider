package cn.codingstyle.spider.crawl.weixinmp;

import org.junit.jupiter.api.Test;
import us.codecraft.webmagic.selector.Html;

import static org.assertj.core.api.Assertions.assertThat;

public class ArticleAuthorExtractTest {
    @Test
    void should_extract_author_success() {
        //不同html的作者信息提取
        assertFirstAuthorHtmlFormat();
        assertSecondAuthorHtmlFormat();
        assertThirdAuthorHtmlFormat();
    }

    private void assertFirstAuthorHtmlFormat() {
        String content = "<span class=\"rich_media_meta rich_media_meta_text\">\n" +
                "                                                                    SSgeek\n" +
                "                                                            </span>";
        assertThat(extractAuthor(content)).isEqualTo("SSgeek");
    }

    private void assertSecondAuthorHtmlFormat() {
        String content = "<span id=\"js_author_name\" class=\"\" datarewardsn=\"\" datatimestamp=\"\" datacanreward=\"0\">熊节</span>";
        assertThat(extractAuthor(content)).isEqualTo("熊节");
    }

    private void assertThirdAuthorHtmlFormat() {
        String content = "<span class=\"rich_media_meta rich_media_meta_nickname\" id=\"profileBt\">\n" +
                "                      <a href=\"javascript:void(0);\" id=\"js_name\">\n" +
                "                        CSDN                      </a>\n" +
                "                      <div id=\"js_profile_qrcode\" class=\"profile_container\" style=\"display:none;\">\n" +
                "                          <div class=\"profile_inner\">\n" +
                "                              <strong class=\"profile_nickname\">CSDN</strong>\n" +
                "                              <img class=\"profile_avatar\" id=\"js_profile_qrcode_img\" src=\"\" alt=\"\">\n" +
                "\n" +
                "                              <p class=\"profile_meta\">\n" +
                "                              <label class=\"profile_meta_label\">微信号</label>\n" +
                "                              <span class=\"profile_meta_value\">CSDNnews</span>\n" +
                "                              </p>\n" +
                "\n" +
                "                              <p class=\"profile_meta\">\n" +
                "                              <label class=\"profile_meta_label\">功能介绍</label>\n" +
                "                              <span class=\"profile_meta_value\">专业的中文 IT 技术社区，与千万技术人共成长。</span>\n" +
                "                              </p>\n" +
                "                              \n" +
                "                          </div>\n" +
                "                          <span class=\"profile_arrow_wrp\" id=\"js_profile_arrow_wrp\">\n" +
                "                              <i class=\"profile_arrow arrow_out\"></i>\n" +
                "                              <i class=\"profile_arrow arrow_in\"></i>\n" +
                "                          </span>\n" +
                "                      </div>\n" +
                "                    </span>";
        assertThat(extractAuthor(content)).isEqualTo("CSDN");
    }

    private String extractAuthor(String htmlContent) {
        return new WeixinMpProcessor().parseAuthor(new Html(htmlContent, "url"));
    }

}
