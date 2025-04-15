package com.easy.vo;

public final class ItemBean {
    @SuppressWarnings("unused")
	private static final long serialVersionUID = -684979444665720L;
    private int itemId;
	private String lotteryType;
	private String itemCode;
	private String itemName;
	private String game;

	public ItemBean(int itemId, String lotteryType, String itemCode, String itemName){
		this.itemId=itemId;
		this.lotteryType=lotteryType;
		this.itemCode=itemCode;
		this.itemName=itemName;
	}

	/**
	 * @return the itemId
	 */
	public int getItemId() {
		return itemId;
	}


	/**
	 * @return the lotteryType
	 */
	public String getLotteryType() {
		return lotteryType;
	}


	/**
	 * @return the itemCode
	 */
	public String getItemCode() {
		return itemCode;
	}


	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}


	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}

	@Override
	public String toString(){
		return "ItemBean[itemId:"+itemId+";lotteryType:"+lotteryType+";itemCode:"+itemCode+"; itemName:"+itemName+"]";
	}
}
