package com.rudraksh.springboot.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rudraksh.springboot.model.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {

	@Query(value="Select u.id, u.first_name , u.last_name, a.activity_name, a.activity_desc, a.deadline_date, a.user_id from user u inner join activity a on u.id=a.user_id", nativeQuery=true)
	List<Activity> listActivity();
	
	@Query(value="Select u.id, u.first_name , u.last_name, a.activity_name, a.activity_desc, a.deadline_date, a.user_id from user u inner join activity a on u.id=a.user_id where u.first_name like %:keyword% or u.last_name like %:keyword% or a.activity_name like %:keyword%", nativeQuery=true)
	List<Activity> listActivityByKeyword(@Param("keyword")String keyword);
	
	//@Query(value="Select activity.activity_name, activity.activity_desc, activity.deadline_date, activity.user_id, user.id from activity inner join user on user.id=activity.user_id", nativeQuery=true)
	//List<Activity> listMyActivity();
	
	@Query(value="Select activity.id,activity.activity_name, activity.activity_desc, activity.deadline_date, activity.user_id from activity where user_id =?", nativeQuery=true)
	List<Activity> listMyActivity(Long id);
	
//	@Query(value="Select activity.id,activity.activity_name, activity.activity_desc, activity.deadline_date, activity.user_id from activity where a.activity_name like %:keyword% and user_id =?", nativeQuery=true)
//	List<Activity> listMyActivityByKeyword(Long id, @Param("keyword")String keyword);
	
	//@Query(value="update activity set activity_name=:activityName, activity_desc=:activityDesc, deadline_date=:deadLine where id=?",nativeQuery=true)
	//void updateActivity(@Param("activityName")String activityName,@Param("activityDesc")String activityDesc,@Param("deadLine")Date deadLine,Long id);
}
