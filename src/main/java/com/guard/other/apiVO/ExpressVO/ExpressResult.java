package com.guard.other.apiVO.ExpressVO;

import java.util.List;
/**
 * 快递信息VO
 * @author guard
 *
 */
public class ExpressResult {
	private String company;
	private String com;
	private String no;
	private String status;// 0或1（1表示签收或退回）
	private List<LogisticsDetail> list;
	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getCom() {
		return com;
	}
	public void setCom(String com) {
		this.com = com;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<LogisticsDetail> getList() {
		return list;
	}
	public void setList(List<LogisticsDetail> list) {
		this.list = list;
	}

}
