package com.multiplicandin.mts.service.impl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import com.multiplicandin.mts.model.Sms;
import com.multiplicandin.mts.service.SmsService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


@Component
public class SmsServiceImpl implements SmsService {

	    
	    private final String ACCOUNT_SID ="AC922699b2a709f201918aa752a5a4c42e";

	    private final String AUTH_TOKEN = "8e68f3d07b7d6ff342ce96fbdb42b934";

	    private final String FROM_NUMBER = "+18126903863";

	    public void send(Sms sms) {
	    	Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

	        Message message = Message.creator(new PhoneNumber(sms.getTo()), new PhoneNumber(FROM_NUMBER), sms.getMessage())
	                .create();
	        System.out.println("here is my id:"+message.getSid());// Unique resource ID created to manage this transaction

	    }

	    public void receive(MultiValueMap<String, String> smscallback) {
	    }
}
