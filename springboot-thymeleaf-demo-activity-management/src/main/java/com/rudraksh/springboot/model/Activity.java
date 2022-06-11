package com.rudraksh.springboot.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="activity")
public class Activity{
	
	/**
	 * 
	 */
//	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	@Column(name="activity_name")
	private String activityName;
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	@Column(name="activity_desc")
	private String activityDesc;
	
	@ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private CustomUser user;
	
	@NotNull(message = "is required")
	@Column(name="deadline_date")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date deadLine;
	
	
	public Activity(String activityName, String activityDesc) {
		this.activityName = activityName;
		this.activityDesc = activityDesc;
		
		
	}
	
	

	public Date getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(Date deadLine) {
		this.deadLine = deadLine;
	}

	/*@ManyToOne
	@JoinColumn(name="user_id")
	private User user;*/
	
	public CustomUser getUser() {
		return user;
	}



	public void setUser(CustomUser user) {
		this.user = user;
	}



	public Activity() {
		
	}

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}
	
	
	
	
	

}
