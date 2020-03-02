package com.xiaor.archetype.common.constants;

public enum ResultEnum {
	
	SUCCESS(0, "Success"),
	
	SYSTEM_ERROR(9, "System Error"),
	
	CHARGE_FAIL(8, "Charge Fail"),
	
	CHARGE_SUCCESS(0, "Charge Success"),
	
	CHARGE_SHIELD(5, "Charge Shield"), //扣费屏蔽
	;

	private int result;
	
	private String msg;
	
	private String desc;
	
	private ResultEnum(int result, String msg) {
		this.result = result;
		this.msg = msg;
	}
	
	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
