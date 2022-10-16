package cn.kevinlu98.utils;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Heading;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;

import org.commonmark.renderer.html.HtmlRenderer;


import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: Mr丶冷文
 * Date: 2022/10/13 14:08
 * Email: kevinlu98@qq.com
 * Description:
 */
public interface MarkdownUtil {

    List<Extension> EXTENSIONS = Collections.singletonList(TablesExtension.create());
    Parser parser = Parser.builder()
            .extensions(EXTENSIONS)
            .build();
    HtmlRenderer renderer = HtmlRenderer.builder()
            .extensions(EXTENSIONS)
            .attributeProviderFactory(attributeProviderContext -> (node, tagName, attributes) -> {
                if (node instanceof Link) {
                    attributes.put("target", "_blank");
                }
                if (node instanceof Heading) {
                    attributes.put("id", UUID.randomUUID().toString().replace("-", ""));
                    attributes.put("class", "lw-title-toc");
                }
            })
            .build();

    /**
     * 将markdown代码解析成html
     *
     * @param markdown markdown源码
     * @return html代码
     */
    static String parse(String markdown) {
        Node document = parser.parse(markdown);
        String content = renderer.render(document);
        Pattern pattern = Pattern.compile(":([\\w+-]+):");

        content = content.replaceAll("\\{fwcline *start=&quot;([\\s\\S]*?)&quot; *end=&quot;([\\s\\S]*?)&quot; *}[\\s\\S]*?\\{/fwcline *}", "<div class=\"fwcline\" style=\"background-image: linear-gradient(-45deg,transparent 0,transparent 10%,$2 0,$2 40%,transparent 0,transparent 60%,$1 0,$1 90%,transparent 0,transparent 100%);\"></div>");
        content = content.replaceAll("\\{fwbili *bvid=&quot;([\\s\\S]*?)&quot; *bvnu=&quot;([\\s\\S]*?)&quot; *}([\\s\\S]*?)\\{/fwbili *}", "<iframe class=\"fwbili\" src=\"//player.bilibili.com/player.html?bvid=$1&page=$2\"></iframe>");
        content = content.replaceAll("\\{fwmusic *source=&quot;([\\s\\S]*?)&quot; *type=&quot;([\\s\\S]*?)&quot; *id=&quot;([\\s\\S]*?)&quot; *}[\\s\\S]*?\\{/fwmusic *}", "<meting-js server=\"$1\" type=\"$2\" id=\"$3\"></meting-js>");
        content = content.replaceAll("\\{fwcode *type=&quot;([\\s\\S]*?)&quot; *}(<br>)?([\\s\\S]*?)(<br>)?\\{/fwcode *}", "<div class=\"fwcode fwcode-$1\">$3</div>");
        content = content.replaceAll("\\{fwalert *type=&quot;([\\s\\S]*?)&quot; *}(<br>)*([\\s\\S]*?)(<br>)*\\{/fwalert *}", "<div class=\"fwalert fwalert-$1\">$3</div>");
        content = content.replaceAll("\\{fwbtn *type=&quot;(.*?)&quot; *url=&quot;(.*?)&quot; *}(<br>)*\\{icon=&quot;(.*?)&quot; *}(.*?)(<br>)*\\{/fwbtn *}", "<a class=\"fwbtn fwbtn-$1\" href=\"$2\" target=\"_blank\"><i class=\"fa $4\"></i>$5</a>");
        content = content.replaceAll("\\{fwgroup *title=&quot;(.*?)&quot; *}(<br>)?([\\s\\S]*?)(<br>)?\\{/fwgroup *}", "<div class=\"fwgroup pos-rlt\"><div class=\"fwgroup-title pos-abs\">$1</div>$3</div>");
        content = content.replaceAll("\\{fwthead *target=&quot;(.*?)&quot; *}(<br>)*([\\s\\S]*?)(<br>)*\\{/fwthead *}", "<div class=\"fwthead\" data-target=\"$1\">$3</div>");
        content = content.replaceAll("\\{fwtbody *target=&quot;(.*?)&quot; *}(<br>)?([\\s\\S]*?)(<br>)?\\{/fwtbody *}", "<div class=\"fwtbody fwtbody-$1\">$3</div>");
        content = content.replaceAll("\\{fwh *}(<br>)*([\\s\\S]*?)(<br>)*\\{/fwh *}", "<div class=\"fwh\">$2</div>");
        content = content.replaceAll("\\{fwb *}(<br>)*([\\s\\S]*?)(<br>)*\\{/fwb *}", "<div class=\"fwb\">$2</div>");
        content = content.replaceAll("\\{fwtab *}(<br>)*([\\s\\S]*?)(<br>)*\\{/fwtab *}", "<div class=\"fwtab\">$2</div>");
        content = content.replaceAll(":(fa-[\\w+-]+):", "<i class=\"fa $1\"></i>");
        content = content.replaceAll(":tw-([\\w+-]+):", "<img src=\"http://twemoji.maxcdn.com/36x36/$1.png\" width=\"24\" title=\"twemoji-1f004\" alt=\"twemoji-1f004\" class=\"emoji twemoji\">");
        content = content.replaceAll(":([\\w+-]+):", "<img src=\"/static/plugin/editormd/images/emoji/$1.png\" width=\"24\" class=\"emoji\" title=\":frowning:\" alt=\":frowning:\">");
        return content;
    }
}
