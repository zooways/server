package admin.zoowayss.top.service.impl;

import admin.zoowayss.top.config.Constants;
import admin.zoowayss.top.entity.AdminUserEntity;
import admin.zoowayss.top.mapper.AdminUserMapper;
import admin.zoowayss.top.service.AdminUserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Description:
 * @Author: Zoowayss Shi
 * @Date: 2023/5/12 15:03
 * @Version: 1.0
 */
@Service
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUserEntity> implements AdminUserService, InitializingBean {
    @Value("${core.user.jwtSecret:secret}")
    private String secret;
    @Autowired
    private BCryptPasswordEncoder encoder;

    private Algorithm algorithm;
    private JWTVerifier verifier;
    @Override
    public String verifyAndGetUserId(String token) {
        return "req";
    }

    @Override
    public AdminUserEntity findAdminUser(String username) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        return getOne(queryWrapper);
    }

    @Override
    public boolean isPasswordMatched(String password, String encodedPassword) {
        return encoder.matches(password, encodedPassword);
    }

    @Override
    public String generateToken(AdminUserEntity adminUserEntity) {
        return JWT.create()
                .withClaim("username", adminUserEntity.getUsername())
                .withClaim("adminId", adminUserEntity.getId())
                .withClaim("roles", AdminUserEntity.asRoles(adminUserEntity))
                .withExpiresAt(new Date(System.currentTimeMillis() + Constants.ADMIN_TOKEN_EXPIRE_TIME))
                .sign(algorithm);
    }

    @Override
    public AdminUserEntity findAdminUserById(String userId) {
        return getById(userId);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        algorithm = Algorithm.HMAC256("admin " + secret);
        verifier = JWT.require(algorithm).build();
    }
}
