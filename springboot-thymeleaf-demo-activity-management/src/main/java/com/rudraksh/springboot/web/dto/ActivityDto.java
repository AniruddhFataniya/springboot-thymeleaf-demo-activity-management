package com.rudraksh.springboot.web.dto;

import java.util.Date;

import javax.persistence.Column;

import org.springframework.format.annotation.DateTimeFormat;

public class ActivityDto {
	
	
	private String activityName;
	private String activityDesc;
	
	
	public ActivityDto(){
		
	}

	

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityDesc() {
		return activityDesc;
	}

	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
	}

	/*public Date getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(Date deadLine) {
		this.deadLine = deadLine;
	}*/

	public ActivityDto(String activityName, String activityDesc) {
		this.activityName = activityName;
		this.activityDesc = activityDesc;
		
	}
	
	

}
