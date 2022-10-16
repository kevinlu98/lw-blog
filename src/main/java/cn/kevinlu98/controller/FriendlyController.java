package cn.kevinlu98.controller;

import cn.kevinlu98.common.Result;
import cn.kevinlu98.enums.ResultEnum;
import cn.kevinlu98.pojo.Friendly;
import cn.kevinlu98.service.FriendlyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Objects;

/**
 * Author: Mr丶冷文
 * Date: 2022/10/6 22:32
 * Email: kevinlu98@qq.com
 * Description:
 */
@Slf4j
@RestController
@RequestMapping("/admin/friendly")
public class FriendlyController {

    private final FriendlyService service;

    public FriendlyController(FriendlyService service) {
        this.service = service;
    }

    @GetMapping("/")
    public Result<List<Friendly>> list() {
        return Result.success(service.list());
    }

    @GetMapping("/{id}")
    public Result<Friendly> detail(@PathVariable Integer id) {
        Friendly friendly = service.detail(id);
        if (Objects.isNull(friendly)) {
            return Result.error(ResultEnum.RESULT_DATA_NOT_FIND);
        } else {
            return Result.success(friendly);
        }
    }


    @PostMapping("/")
    public Result<String> save(Friendly friendly) {
        service.save(friendly);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Integer id) {
        service.delete(id);
        return Result.success();
    }
}
