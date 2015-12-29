package com.guard.other.apiVO.ExpressVO;

/**
 * 调用接口查询后的状态结果VO
 * @author guard
 *
 */
public class Express {
	private String resultcode;// 返回标识码
	private String reason;
	private String error_code;
	private ExpressResult result;

	public String getResultcode() {
		return resultcode;
	}

	public void setResultcode(String resultcode) {
		this.resultcode = resultcode;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getError_code() {
		return error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	public ExpressResult getResult() {
		return result;
	}

	public void setResult(ExpressResult result) {
		this.result = result;
	}

}
