package com.easy.vo;

public class InterfaceBean {
    private Integer id;             //商户Id
    private String pid;             //商户号
    private Integer uid;            //会员Id
    private Integer currencyId;      //币种Id
    private String currencyType;    //币种
    private String loginname;       //会员账号
    private String password;        //会员密码
    private Double amount;          //金额
    private String billno;          //注单号
    private String transactionId;   //业务订单号
    private Integer transferType;    //转账类型;下注BET,派彩WIN,退款REFUND, 提前结算CASHOUT,订单取消补扣 CANCEL_DEDUCT,订单取消返 CANCEL_RETURN,结算回滚返还SETTLEMENT_ROLLBACK_RETURN,结算回滚补扣SETTLEMENT_ROLLBACK_DEDUCT

    public InterfaceBean(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getBillno() {
        return billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getTransferType() {
        return transferType;
    }

    public void setTransferType(Integer transferType) {
        this.transferType = transferType;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }
}
