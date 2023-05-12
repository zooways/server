package admin.zoowayss.top.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

@TableName(value = "t_server")
public class Server {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @TableField(value = "`domain`")
    private String domain;

    @TableField(value = "ip4")
    private String ip4;

    @TableField(value = "ip6")
    private String ip6;

    @TableField(value = "`password`")
    private String password;

    @TableField(value = "frp_token")
    private String frpToken;

    @TableField(value = "expire_time")
    private Date expireTime;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "update_time")
    private Date updateTime;

    public static final String COL_ID = "id";

    public static final String COL_DOMAIN = "domain";

    public static final String COL_IP4 = "ip4";

    public static final String COL_IP6 = "ip6";

    public static final String COL_PASSWORD = "password";

    public static final String COL_FRP_TOKEN = "frp_token";

    public static final String COL_EXPIRE_TIME = "expire_time";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return domain
     */
    public String getDomain() {
        return domain;
    }

    /**
     * @param domain
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * @return ip4
     */
    public String getIp4() {
        return ip4;
    }

    /**
     * @param ip4
     */
    public void setIp4(String ip4) {
        this.ip4 = ip4;
    }

    /**
     * @return ip6
     */
    public String getIp6() {
        return ip6;
    }

    /**
     * @param ip6
     */
    public void setIp6(String ip6) {
        this.ip6 = ip6;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return frp_token
     */
    public String getFrpToken() {
        return frpToken;
    }

    /**
     * @param frpToken
     */
    public void setFrpToken(String frpToken) {
        this.frpToken = frpToken;
    }

    /**
     * @return expire_time
     */
    public Date getExpireTime() {
        return expireTime;
    }

    /**
     * @param expireTime
     */
    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}