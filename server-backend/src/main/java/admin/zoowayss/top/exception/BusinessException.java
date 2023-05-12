package admin.zoowayss.top.exception;


public class BusinessException extends RuntimeException {

    private String code;

    public BusinessException(String message) {
        this("400", message);
    }

    public BusinessException(String code, String message) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
