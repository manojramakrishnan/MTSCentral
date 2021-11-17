package com.multiplicandin.mts.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.multiplicandin.mts.service.EmailService;

@Service("emailService")
public class EmailServiceImpl implements EmailService {

	@Autowired
    private JavaMailSender mailSender;
	
	@Override
	public void sendSimpleEmail(String deliveryEmailId, String message, String subject) {
		// TODO Auto-generated method stub
		SimpleMailMessage message1 = new SimpleMailMessage();

		
		message1.setFrom("manoj.rgv@gmail.com");
		message1.setTo(deliveryEmailId);
		message1.setText(message);
		message1.setSubject(subject);

		mailSender.send(message1);

	}

}
