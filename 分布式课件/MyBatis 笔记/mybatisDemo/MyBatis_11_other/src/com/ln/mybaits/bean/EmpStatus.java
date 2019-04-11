package com.ln.mybaits.bean;

/**
 * 员工状态的枚举类型
 * 希望数库保存的是100,200这些状态码，而不是0,1枚举索引
 * @author LiNian
 *
 */
public enum EmpStatus {

	LOGIN(100,"用户登录"),LOGOUT(200,"用户登出"),REMOVE(300,"用户不存在");
	
	private Integer code;
	private String msg;
	private EmpStatus(Integer code,String msg){
		this.code=code;
		this.msg=msg;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	// 按照状态码返回枚举的状态码
	public static EmpStatus getEmpStatusByCode(Integer code){
		switch (code) {
		case 100:
			return LOGIN;
		case 200:
			return LOGOUT;
		case 300:
			return REMOVE;
		default:
			return LOGIN;
		}
	}
}
