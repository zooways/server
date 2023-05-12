package admin.zoowayss.top.token;

import java.util.Collections;
import java.util.Set;

public interface TokenVerifier {

    String verifyAndGetUserId(String token);

    default Set<String> getRoles(String token) {
        return Collections.EMPTY_SET;
    }
}
