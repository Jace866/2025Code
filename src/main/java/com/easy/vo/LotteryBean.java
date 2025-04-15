package com.easy.vo;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class LotteryBean {
    @SuppressWarnings("unused")
	private static final long serialVersionUID = -684979444667710L;
    private int id;          //游戏Id
    private String code;     //游戏代码
    private String name;     //游戏名称
	private String type;     //游戏类别(时时彩、 11选5、PK10、快三、快乐十分等等)
	private String tab;      //对应数据库表名
	private String maxIssue; //每天最大期号
	private String fmt1;     //格式化期号
	private String fmt2;     //格式化期号
	private int issueLen;    //期号长度
	private int issueType;   //期号类别: 1-yyyymmddnnn, 2-yyyynnn, 3-nnnnnnnn
	private int issues;
	private int showOrder;   //显示顺序
	private int sendFlag;    //发送数据标志
	private int status;      //状态
	private String sendAccount; //发送帐号
	private String memberLayer; //会员层次,会员分类
	
	public LotteryBean(int id, String code, String name, String type, String tab, String maxIssue, int issueType){
		this.id = id;
		this.code = code;
		this.name = name;
		this.type = type;
		this.tab = tab;
		this.maxIssue = maxIssue;
		this.issueType = issueType;
	}

	public LotteryBean(int id, String code, String name, String type, String tab, String maxIssue, int issueType, int showOrder){
		this.id = id;
		this.code = code;
		this.name = name;
		this.type = type;
		this.tab = tab;
		this.maxIssue = maxIssue;
		this.issueType = issueType;
		this.showOrder = showOrder;
	}

	
	public String toString() {
		return "LotteryBean[id="+id
		        +";code="+code
		        +";name="+name
		        +";type="+type
		        +";tab="+tab
		        +";fmt1="+fmt1
		        +";fmt2="+fmt2
		        +";maxIssue="+maxIssue
		        +";issueLen="+issueLen
		        +";issueType="+issueType
		        +";showOrder="+showOrder
		        +";status="+status
		        +"]";
	}

	public String getCode() {
		return code;
	}
	public String getFmt1() {
		return "yyMMdd"; //fmt1;
	}
	public String getFmt2() {
		if( issues>999 ) return "0000";
		else if( id==10 ) return "00";
		else return "000"; //fmt2;
	}
	public int getId() {
		return id;
	}
	public int getIssueLen() {
		return issueLen;
	}
	public String getMaxIssue() {
		return maxIssue;
	}
	public String getName() {
		return name;
	}
	public String getType() {
		return type;
	}
	public String getTab() {
		return tab;
	}
	
	public int getIssueType() {
		return issueType;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public int getSendFlag() {
		return sendFlag;
	}
	
	public void setSendFlag(int sendFlag) {
		this.sendFlag = sendFlag;
	}

	public int getIssues() {
		return issues;
	}

	public void setIssues(int issues) {
		this.issues = issues;
	}

	public String getMemberLayer() {
		return memberLayer;
	}

	public void setMemberLayer(String memberLayer) {
		this.memberLayer = memberLayer;
	}

	public int getShowOrder() {
		return showOrder;
	}

	public String getSendAccount() {
		return sendAccount;
	}

	public void setSendAccount(String sendAccount) {
		this.sendAccount = sendAccount;
	}

}
