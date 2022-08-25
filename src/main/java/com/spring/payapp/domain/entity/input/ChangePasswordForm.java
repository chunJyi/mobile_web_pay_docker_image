/**
 * 
 */
package com.spring.payapp.domain.entity.input;

import lombok.Data;

/**
 * @author chun
 * @package payApp
 * @time 5:21:21 PM
 */
@Data
public class ChangePasswordForm {
	
	private String newPassword;
	private String ConfirmPassword;


}
