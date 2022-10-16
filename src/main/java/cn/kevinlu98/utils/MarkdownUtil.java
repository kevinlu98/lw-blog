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
        return renderer.render(document);
    }
}
