package cn.kevinlu98.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Author: Mr丶冷文
 * Date: 2022/10/9 21:15
 * Email: kevinlu98@qq.com
 * Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageHelper<T> {
    // 数据列表
    private List<T> rows;
    // 总记录数
    private Long total;

    //当前页
    private Integer current;

    //总页数
    private Integer totalPage;
}
