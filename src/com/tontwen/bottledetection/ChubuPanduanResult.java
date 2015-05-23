package com.tontwen.bottledetection;

public class ChubuPanduanResult {
	private String bottleNumber;
	private int bottleType;
	private String carNumber;
	private String preDetectResult;
	
	public String getBottleNumber() {
		return bottleNumber;
	}
	public void setBottleNumber(String bottleNumber) {
		this.bottleNumber = bottleNumber;
	}
	public int getBottleType() {
		return bottleType;
	}
	public void setBottleType(int bottleType) {
		this.bottleType = bottleType;
	}
	public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	public String getPreDetectResult() {
		return preDetectResult;
	}
	public void setPreDetectResult(String preDetectResult) {
		this.preDetectResult = preDetectResult;
	}
}