package com.easy.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Result<T>{

    private String code;

    private String msg;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private T data;

    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(String code, String msg, T result) {
        this.code = code;
        this.msg = msg;
        this.data = result;
    }

    public Result<T> success() {
        return new Result<>("0", "success");
    }

    public Result<T> success(String msg, T result) {
        return new Result<>("0", msg, result);
    }

    public Result<T> success(String code, String msg, T result) {
        return new Result<>(code, msg, result);
    }

    public Result<T> success(T result) {
        return new Result<>("0", "success", result);
    }

    public Result<T> error(String code, String msg) {
        return new Result<>(code, msg);
    }

    public Result<T> error(String code, String msg, T result) {
        return new Result<>(code, msg, result);
    }

}