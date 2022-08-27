package com.example.commons;

/**
 * desc:
 *
 * @author : Hongyi Zheng
 * @date : 2022/8/26
 */
public class Result {
    /**
     * 返回结果是否成功
     */
    private boolean success;
    /**
     * 返回结果描述
     */
    private String message;
    /**
     * 返回结果数据
     */
    private Object data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static Result success() {
        return success(null);
    }

    public static Result success(Object data) {
        Result result = new Result();
        result.setSuccess(true);
        result.setMessage("success");
        result.setData(data);
        return result;
    }

    public static Result fail() {
        return failWithMessage("failed");
    }

    public static Result error() {
        return failWithMessage("error");
    }

    private static Result failWithMessage(final String message) {
        Result result = new Result();
        result.setMessage(message);
        return result;
    }

    public Result() {
    }

    public Result(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
