package com.trainspotting.hait.application;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.trainspotting.hait.exception.ApplyDuplicatedException;
import com.trainspotting.hait.model.ApplicationDTO;
import com.trainspotting.hait.model.ApplicationEntity;
import com.trainspotting.hait.model.OwnerEntity;
import com.trainspotting.hait.util.MailUtil;

@Service
public class ApplicationService {
	
	@Autowired
	private ApplicationMapper mapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MailUtil mail;

	public int insert(ApplicationEntity p) {
		p.setRstrnt_nm(p.getRstrnt_nm().trim());
		if(mapper.findRstrntName(p) != null) throw new ApplyDuplicatedException("RESTAURANT_NAME_DUPLICATED");
		if(mapper.findOwnerEmail(p) != null) throw new ApplyDuplicatedException("OWNER_EMAIL_DUPLICATED");
		if(mapper.findOwnerContact(p) != null) throw new ApplyDuplicatedException("OWNER_CONTACT_DUPLICATED");

		return mapper.insert(p);
	}
	
	public List<ApplicationDTO> listAll(int process_status) {
		return mapper.listAll(process_status);
	}
	
	public ApplicationDTO detail(int pk) {
		return mapper.detail(pk);
	}
	
	public int update(ApplicationEntity p) throws UnsupportedEncodingException, MessagingException {
		if(p.getProcess_status() == -1) {
			mail.rejectMail(p.getOwner_email());
			
		} else if (p.getProcess_status() == 1) {
			String tempPW = UUID.randomUUID().toString().replaceAll("-", "");
			tempPW = tempPW.substring(0, 10);
			
			OwnerEntity oe = new OwnerEntity();
			oe.setNm(p.getOwner_nm());
			oe.setEmail(p.getOwner_email());
			oe.setContact(p.getOwner_contact());
			oe.setPw(passwordEncoder.encode(tempPW));
			
			mapper.insOwner(oe);
			mapper.insRstrnt(p);
			
			mail.acceptMail(p.getOwner_email(), tempPW);
		}
		
		return mapper.update(p);
	}
}
