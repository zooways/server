package admin.zoowayss.top.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class CoreProperties {

    @Value("${core.token.anonymousPathPatterns:}")
    private List<String> anonymousPathPatterns;

}
