package admin.zoowayss.top.controller.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Description:
 * @Author: Zoowayss Shi
 * @Date: 2023/5/12 15:25
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostServerVO {
    String domain;

    @NotEmpty(message = "ipv4 is required")
    String ip4;

    String ip6;

    @NotEmpty(message = "password is required")
    String password;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @NotNull(message = "expireTime is required")
    Date expireTime;


}
