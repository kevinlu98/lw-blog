package cn.kevinlu98.pojo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * Author: Mr丶冷文
 * Date: 2022/10/7 15:45
 * Email: kevinlu98@qq.com
 * Description:
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "blog_navigation")
public class Navigation  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //主键


    private String name; //名称

    private String icon; //图标

    private Integer ordered; //顺序

    private String link; //跳转链接

    private Boolean linkMode; //跳转方式

    private Boolean enable; //是否启用


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Navigation navigation = (Navigation) o;
        return id != null && Objects.equals(id, navigation.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
