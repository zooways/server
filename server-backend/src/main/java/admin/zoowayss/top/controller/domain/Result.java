package admin.zoowayss.top.controller.domain;

import lombok.Data;

@Data
public class Result<T> {

    private String code;

    private String msg;

    private T data;

    private Boolean success;


    public Result(String code, String msg) {
        this(code, msg, null);
    }

    public Result(String code, String msg, T data) {
        this.code = code;
        this.data = data;
        this.success = code.equals("ok");
    }

    public static Result ok() {
        return new Result("ok", "success");
    }

    public static <T> Result<T> ok(T data) {
        return new Result("ok", "success", data);
    }

    public static Result error(String msg) {
        return new Result("error", msg);
    }

    public static <T> Result<T> error(String msg, T data) {
        return new Result("error", msg, data);
    }


}
