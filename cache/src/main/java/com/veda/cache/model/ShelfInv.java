package com.veda.cache.model;

import java.sql.Date;

public class ShelfInv {
	private String shelfDetId;
	private String rfId;
	private String itemStatus;
	private String itemAddedBy;
	private Date itemAddDate;
	private String lastUser;
	private Date lastUpdttm;
	private String sentStatus;
	public String getShelfDetId() {
		return shelfDetId;
	}
	public void setShelfDetId(String shelfDetId) {
		this.shelfDetId = shelfDetId;
	}
	public String getRfId() {
		return rfId;
	}
	public void setRfId(String rfId) {
		this.rfId = rfId;
	}
	public String getItemStatus() {
		return itemStatus;
	}
	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}
	public String getItemAddedBy() {
		return itemAddedBy;
	}
	public void setItemAddedBy(String itemAddedBy) {
		this.itemAddedBy = itemAddedBy;
	}
	public Date getItemAddDate() {
		return itemAddDate;
	}
	public void setItemAddDate(Date itemAddDate) {
		this.itemAddDate = itemAddDate;
	}
	public String getLastUser() {
		return lastUser;
	}
	public void setLastUser(String lastUser) {
		this.lastUser = lastUser;
	}
	public Date getLastUpdttm() {
		return lastUpdttm;
	}
	public void setLastUpdttm(Date lastUpdttm) {
		this.lastUpdttm = lastUpdttm;
	}
	public String getSentStatus() {
		return sentStatus;
	}
	public void setSentStatus(String sentStatus) {
		this.sentStatus = sentStatus;
	}
	@Override
	public String toString() {
		return "ShelfInv [shelfDetId=" + shelfDetId + ", rfId=" + rfId + ", itemStatus=" + itemStatus + ", itemAddedBy="
				+ itemAddedBy + ", itemAddDate=" + itemAddDate + ", lastUser=" + lastUser + ", lastUpdttm=" + lastUpdttm
				+ ", sentStatus=" + sentStatus + "]";
	}
	
	
	
}
