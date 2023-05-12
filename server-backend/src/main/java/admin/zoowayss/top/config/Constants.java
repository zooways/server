package admin.zoowayss.top.config;

import java.util.regex.Pattern;

public class Constants {

    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
    public static Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static final String USER_TOKEN_HEADER = "Authorization";

    public static final String ADMIN_TOKEN_HEADER = "Admin-Token";

    public static final long TOKEN_EXPIRE_TIME = 1000l * 60l * 60l * 24l * 7l;

    public static final long ADMIN_TOKEN_EXPIRE_TIME = TOKEN_EXPIRE_TIME;

    public static final String ADMIN_ROLE_SUPER = "super";

    public static final String EMAIL_TITLE_KEY = "email.title";

    public static final String EMAIL_CONTENT_KEY = "email.content";

    public static final String ARTICLE_TOKEN_TOP_URL = "/token/top/up";
}
