package com.scloudyy.springbackend.dto;

public class Result<T> {
    private T data;
    private boolean success;
    private String errMsg;
    private int errCode;

    public Result(boolean success, T data) {
        this.setSuccess(success);
        this.setData(data);
    }

    public Result(boolean success, int errCode, String errMsg) {
        this.setSuccess(success);
        this.setErrCode(errCode);
        this.setErrMsg(errMsg);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }
}
