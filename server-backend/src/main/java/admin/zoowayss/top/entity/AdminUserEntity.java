package admin.zoowayss.top.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Data
@TableName("s_admin_user")
public class AdminUserEntity {

    public static final int STATUS_NORMAL = 1;
    public static final int STATUS_DISABLED = 2;

    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String username;
    private String password;
    private Integer status;
    private String role;
    private Date createTime;

    public static List<String> asRoles(AdminUserEntity adminUserEntity) {
        String role = adminUserEntity.getRole();
        if (StringUtils.hasText(role)) {
            String[] split = role.split(",");
            return Arrays.asList(split);
        }
        return Collections.EMPTY_LIST;
    }
}
