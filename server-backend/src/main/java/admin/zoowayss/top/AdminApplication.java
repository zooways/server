package admin.zoowayss.top;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description:
 * @Author: Zoowayss Shi
 * @Date: 2023/5/12 15:12
 * @Version: 1.0
 */
@SpringBootApplication
@MapperScan(basePackages = "admin.zoowayss.top.mapper")
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
