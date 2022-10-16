package cn.kevinlu98.common;

import cn.kevinlu98.enums.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Author: Mr丶冷文
 * Date: 2022/10/6 22:26
 * Email: kevinlu98@qq.com
 * Description:
 */
@Data
@AllArgsConstructor
public class Result<T> {
    private Integer code;
    private String message;

    private T data;

    private Result(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
    }

    private Result(ResultEnum resultEnum, T data) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
        this.data = data;
    }

    public static <T> Result<T> success() {
        return new Result<>(ResultEnum.RESULT_SUCCESS);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(ResultEnum.RESULT_SUCCESS, data);
    }

    public static <T> Result<T> error() {
        return new Result<>(ResultEnum.RESULT_ERROR);
    }

    public static <T> Result<T> error(ResultEnum resultEnum) {
        return new Result<>(resultEnum);
    }

    public static <T> Result<T> error(int code, String message) {
        return new Result<>(code, message, null);
    }
}
