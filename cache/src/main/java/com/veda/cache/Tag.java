package com.veda.cache;

import java.util.Date;

public class Tag {
	
	private String tagId;
	private long scanDate;
	private boolean isValid;
	
	public String getTagId() {
		return tagId;
	}
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	
	public long getScanDate() {
		return scanDate;
	}
	public void setScanDate(long scanDate) {
		this.scanDate = scanDate;
	}
	
	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	@Override
	public String toString() {
		return "Tag [tagId=" + tagId + ", scanDate=" + scanDate + ", isValid="
				+ isValid + "]";
	}
	

}
