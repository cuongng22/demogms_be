package com.example.demo.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Resp {
	Object data;
	int statusCode;// 0: succ <>1: fail
	String msg;
	Object included;

    public Resp() {
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