package com.example.demo.response;

import lombok.Data;
import lombok.NoArgsConstructor;

public class Resp {
	Object data;
	int statusCode;// 0: succ <>1: fail
	String msg;
	Object included;

    public Resp() {
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getIncluded() {
        return included;
    }

    public void setIncluded(Object included) {
        this.included = included;
    }

    public Resp(int statusCode, Object data) {
		super();
		this.statusCode = statusCode;
		this.data = data;
	}

	public Resp(int statusCode, Object data, Object included) {
		super();
		this.statusCode = statusCode;
		this.data = data;
		this.included = included;
	}

	public Resp(int statusCode, String msg, Object data, Object included) {
		super();
		this.statusCode = statusCode;
		this.data = data;
		this.included = included;
		this.msg = msg;
	}

}