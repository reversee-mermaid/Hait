package com.trainspotting.hait.admin;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.trainspotting.hait.exception.LoginFailedException;
import com.trainspotting.hait.jwt.JwtProvider;
import com.trainspotting.hait.model.AdminEntity;
import com.trainspotting.hait.model.ApplicationDTO;
import com.trainspotting.hait.model.ApplicationEntity;
import com.trainspotting.hait.model.OwnerEntity;
import com.trainspotting.hait.util.MailUtil;

@Service
public class AdminService {
	
	@Autowired
	private AdminMapper mapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private MailUtil mail;
	
	public String login(AdminEntity param) {
		
		AdminEntity data = mapper.findUserById(param.getId());

		if(data == null) {
			throw new LoginFailedException("ACCOUNT_NOT_FOUND");
		}
		
		if(!passwordEncoder.matches(param.getPw(), data.getPw())) {
			throw new LoginFailedException("PASSWORD_MISMATCH");
		}

		return jwtProvider.provideToken(data.getId(), "ADMIN").getToken();
	}
	
	public List<ApplicationDTO> listAll() {
		return mapper.listAll();
	}
	
	public List<ApplicationDTO> listStatus(int p) {
		return mapper.listStatus(p);
	}
	
	public ApplicationDTO detail(ApplicationEntity p) {
		return mapper.detail(p);
	}
	
	public int update(ApplicationEntity p) throws UnsupportedEncodingException, MessagingException {
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
