package com.xiaor.archetype.common.model;

import com.xiaor.archetype.common.constants.ResultEnum;
import lombok.Data;

@Data
public class ResultBean {

	/**
	 * @Field @result : 结果：0.成功 其他：失败
	 */
	private int result;
	
	/**
	 * @Field @desc : 成功/失败描述
	 */
	private String desc;
	
	private String msg;
	
	private Object data;
	
	public void setResult(ResultEnum result) {
		this.result = result.getResult();
		this.desc = result.getDesc();
		this.msg = result.getMsg();
	}

	public static void main(String[] args) {
		ResultBean resultBean = new ResultBean();
        System.out.println(resultBean);
	}
}
