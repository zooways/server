package admin.zoowayss.top.controller.admin;

import admin.zoowayss.top.config.Constants;
import admin.zoowayss.top.controller.admin.domain.AdminUserCreateRequest;
import admin.zoowayss.top.controller.domain.Result;
import admin.zoowayss.top.entity.AdminUserEntity;
import admin.zoowayss.top.service.AdminUserService;
import admin.zoowayss.top.token.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/admin/user")
public class AdminUserLoginController {


    @Autowired
    private AdminUserService adminUserService;


    @PostMapping("/login")
    public Result login(@RequestBody AdminUserCreateRequest request, HttpServletResponse httpResponse) {
        Assert.hasText(request.getUsername(), "Username must not be empty");
        Assert.hasText(request.getPassword(), "Password must not be empty");

        AdminUserEntity adminUserEntity = adminUserService.findAdminUser(request.getUsername());

        Assert.notNull(adminUserEntity, "username or password is invalid");
        if (adminUserEntity.getStatus() != AdminUserEntity.STATUS_NORMAL) {
            return Result.error("user is not normal");
        }

        boolean passwordMatched = adminUserService.isPasswordMatched(request.getPassword(), adminUserEntity.getPassword());

        Assert.isTrue(passwordMatched, "username or password is invalid");

        String token = adminUserService.generateToken(adminUserEntity);

        Cookie cookie = new Cookie(Constants.ADMIN_TOKEN_HEADER, token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) (Constants.ADMIN_TOKEN_EXPIRE_TIME / 1000));
        httpResponse.addCookie(cookie);

        Map map = getInfoMap(adminUserEntity);
        map.put("token", token);
        return Result.ok(map);
    }

    @PostMapping("/logout")
    public Result logout(HttpServletResponse httpResponse) {
        Cookie cookie = new Cookie(Constants.ADMIN_TOKEN_HEADER, null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        httpResponse.addCookie(cookie);
        return Result.ok();
    }

    @GetMapping("/info")
    public Result info(TokenUser tokenUser) {
        Assert.notNull(tokenUser.getUserId(), "Your must login first");
        AdminUserEntity adminUserEntity = adminUserService.findAdminUserById(tokenUser.getUserId());
        Assert.notNull(adminUserEntity, "Your must login first");
        Map map = getInfoMap(adminUserEntity);
        return Result.ok(map);
    }

    private Map getInfoMap(AdminUserEntity adminUserEntity) {
        Map map = new HashMap(1);
        map.put("userId", adminUserEntity.getId());
        map.put("username", adminUserEntity.getUsername());
        map.put("roles", AdminUserEntity.asRoles(adminUserEntity));
        return map;
    }


}
