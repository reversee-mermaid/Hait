package com.trainspotting.hait.application;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.trainspotting.hait.mail.MailUtil;
import com.trainspotting.hait.model.ApplicationEntity;
import com.trainspotting.hait.model.OwnerEntity;

@Service
public class ApplicationService {

	@Autowired
	private ApplicationMapper mapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private MailUtil mail;
	
	public int update(ApplicationEntity p) throws UnsupportedEncodingException, MessagingException {
		//TODO 받아온 값 확인!!
		
		if(p.getProcess_status() == -1) {
			mail.rejectMail(p.getOwner_email());
			
		} else if (p.getProcess_status() == 1) {
			String tempPW = UUID.randomUUID().toString().replaceAll("-", "");
			tempPW = tempPW.substring(0, 10);
			
			OwnerEntity oe = new OwnerEntity();
			oe.setEmail(p.getOwner_email());
			oe.setPw(passwordEncoder.encode(tempPW));
			mapper.insOwner(oe);
			mapper.insRstrnt(p);
			
			mail.acceptMail(p.getOwner_email(), tempPW);
		}
		
		return mapper.update(p);
	}
}