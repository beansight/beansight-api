package com.beansight.android.models;

public class Insight {
	
	private String uniqueId;
	private String content;
	private String creator;
	private long agreeCount;
	private long disagreeCount;
	
	
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public long getAgreeCount() {
		return agreeCount;
	}
	public void setAgreeCount(long agreeCount) {
		this.agreeCount = agreeCount;
	}
	public long getDisagreeCount() {
		return disagreeCount;
	}
	public void setDisagreeCount(long disagreeCount) {
		this.disagreeCount = disagreeCount;
	}
	
}
