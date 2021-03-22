package com.trainspotting.hait.mail;

import java.io.UnsupportedEncodingException;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class MailUtil {
    
	@Autowired
    private JavaMailSender mailSender;
    private MimeMessage message;
    private MimeMessageHelper messageHelper;
    
    @PostConstruct
    public void init() throws MessagingException {
    	message = this.mailSender.createMimeMessage();
    	messageHelper = new MimeMessageHelper(message, true, "UTF-8");
    }
    
    public void send(String subject, String text, String toEmail)
    		throws MessagingException, UnsupportedEncodingException {
    	
    	messageHelper.setSubject(subject);
        messageHelper.setText(text, true);
        messageHelper.setFrom("dbwjd0321@gmail.com", "H.ait");
        messageHelper.setTo(toEmail);
        mailSender.send(message);
    }
    
    public void rejectMail(String email) throws MessagingException, UnsupportedEncodingException {
		String subject = "[H.ait] 서비스 신청이 거부 되었습니다.";
		String text = "<h1>자세한 사항은 회사로 문의해주세요.</h1>";
		send(subject, text, email);
	}

	public void acceptMail(String email, String tempPW) throws MessagingException, UnsupportedEncodingException {
		String subject = "[H.ait] 회원이 되신 것을 환영합니다.";
		String text = "<h1>아래 발급된 비밀번호로 로그인 후 변경해주세요.</h1><br>"
				+ "<p>임시 비밀번호 : "
				+ tempPW + "</p>";
		send(subject, text, email);
	}
}
