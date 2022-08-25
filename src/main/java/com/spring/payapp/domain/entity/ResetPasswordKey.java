/**
 * 
 */
package com.spring.payapp.domain.entity;

import java.io.Serializable;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chun
 * @package payApp
 * @time 4:38:56 PM
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordKey implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@OneToOne
	private Account account;
	private String token;
	private LocalTime expireTime;
	
	public boolean isExpireTime() {
		int startTime = expireTime.toSecondOfDay();
		int endTime = LocalTime.now().minusMinutes(1).toSecondOfDay();
		return endTime-startTime >=0;
	}
	

}
