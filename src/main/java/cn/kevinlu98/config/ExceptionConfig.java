package cn.kevinlu98.config;

import cn.kevinlu98.common.Result;
import cn.kevinlu98.exception.LWNotFindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author: Mr丶冷文
 * Date: 2022/10/10 13:33
 * Email: kevinlu98@qq.com
 * Description: 只在Controller层生效
 */
@Slf4j
@ControllerAdvice
public class ExceptionConfig {

    @ExceptionHandler(LWNotFindException.class)
    public String notFindException(LWNotFindException e) {
        e.printStackTrace();
        log.error(e.getMessage(), e);
        return "forward:/error/404.html";
    }

    @ExceptionHandler(Exception.class)
    public void defaultException(Exception e, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        e.printStackTrace();
        log.error(e.getMessage(), e);
        if (request.getRequestURI().endsWith(".html")) {
            request.getRequestDispatcher("/error/500.html").forward(request, response);
        } else {
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(new ObjectMapper().writeValueAsString(Result.error()));
        }
    }
}
