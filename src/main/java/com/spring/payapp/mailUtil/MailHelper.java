/**
 * 
 */
package com.spring.payapp.mailUtil;

import java.util.Properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @author chun
 * @package payApp
 * @time 10:01:57 PM
 */
@Component
@ConfigurationProperties(prefix = "my.mail")
@Data
public class MailHelper {

	private String host;
	private int port;
	private String username;
	private String password;
	private Properties properties;

	public void sendMail(String email, String subject, String url) {
		try {
			JavaMailSenderImpl sender = new JavaMailSenderImpl();
			sender.setHost(host);
			sender.setPort(port);
			sender.setUsername(username);
			sender.setPassword(password);
			sender.setJavaMailProperties(properties);

			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(email);
			message.setSubject(subject);
			message.setText(url);

			sender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
