package com.example.controller;

import com.example.commons.Result;

/**
 * desc:
 * author: Hongyi Zheng
 * date: 2022/8/26
 */
public abstract class BaseController {

    protected Result success(Object data){
        return Result.success(data);
    }

    protected Result success() {
        return success(null);
    }

    protected Result failed() {
        return Result.fail();
    }

    protected Result error() {
        return Result.error();
    }
}
