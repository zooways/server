package admin.zoowayss.top.service;


import admin.zoowayss.top.entity.AdminUserEntity;
import admin.zoowayss.top.token.TokenVerifier;

public interface AdminUserService extends TokenVerifier {
    AdminUserEntity findAdminUser(String username);

    boolean isPasswordMatched(String password, String password1);

    String generateToken(AdminUserEntity adminUserEntity);

    AdminUserEntity findAdminUserById(String userId);
}
