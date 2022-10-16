package cn.kevinlu98.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Author: Mr丶冷文
 * Date: 2022/10/15 19:40
 * Email: kevinlu98@qq.com
 * Description:
 */
@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {
    ERROR_404(404,"对不起，你请求的页面暂时未找到.<br>它或许已经被迁移至其它页面啦."),
    ERROR_500(500,"您好，服务器暂时出发问题，请稍候访问.<br>或您也可以尝试联系站长."),
    ;
    private final int code;
    private final String msg;
}
