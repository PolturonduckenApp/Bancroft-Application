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
}
