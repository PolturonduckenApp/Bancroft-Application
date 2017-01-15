package com.polturonducken.bancroft;

import java.util.Date;

public class Block {
	private String name;
	private Date startTime;
	private Date endTime;
	
	public Block(String name, Date startTime, Date endTime) {
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public Block(String name) {
		this(name, null, null);
	}
	
	public Block() {
		this("", null, null);
	}
	
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	public Date startTime() {return startTime;}
	public void setStartTime(Date startTime) {this.startTime = startTime;}
	
	public Date endTime() {return endTime;}
	public void setEndTime(Date endTime) {this.endTime = endTime;}
	
	public long duration() {
		return endTime.getTime() - startTime.getTime();
	}
}
