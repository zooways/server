package admin.zoowayss.top.controller;

import admin.zoowayss.top.controller.domain.Result;
import admin.zoowayss.top.controller.domain.vo.PostServerVO;
import admin.zoowayss.top.entity.Server;
import admin.zoowayss.top.service.impl.ServerService;
import admin.zoowayss.top.token.TokenUser;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Description:
 * @Author: Zoowayss Shi
 * @Date: 2023/5/12 15:21
 * @Version: 1.0
 */
@RestController
@RequestMapping("/admin/server")
public class ServerController {

    @Resource
    private ServerService serverService;


    @GetMapping
    public Result listPage(TokenUser user, @RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Server> page = new Page<>();
        page.setCurrent(current);
        page.setSize(pageSize);
        return Result.ok(serverService.page(page));
    }


    @PostMapping
    public Result add(TokenUser user, @Validated @RequestBody PostServerVO postServerVO) {
        Server save = new Server();
        BeanUtils.copyProperties(postServerVO, save);
        save.setCreateTime(new Date());
        return Result.ok(serverService.save(save));
    }
}
