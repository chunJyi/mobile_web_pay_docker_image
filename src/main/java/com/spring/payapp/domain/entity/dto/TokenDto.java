/**
 * 
 */
package com.spring.payapp.domain.entity.dto;

import java.time.LocalTime;

import com.spring.payapp.domain.entity.Account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chun
 * @package payApp
 * @time 2:13:31 PM
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {
	
	private long id;
	private Account account;
	private String token;
	private LocalTime expireTime;


}
