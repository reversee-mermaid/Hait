package com.trainspotting.hait.owner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.jsonwebtoken.Claims;

import com.trainspotting.hait.Utils.FileUtils;
import com.trainspotting.hait.Utils.SMSUtil;
import com.trainspotting.hait.exception.LoginFailedException;
import com.trainspotting.hait.jwt.JwtProvider;
import com.trainspotting.hait.model.OwnerDTO;
import com.trainspotting.hait.model.OwnerEntity;
import com.trainspotting.hait.model.ReservEntity;
import com.trainspotting.hait.model.RstrntDTO;
import com.trainspotting.hait.model.RstrntEntity;

@Service
public class OwnerService {

	@Autowired
	private OwnerMapper mapper;

	@Autowired
	private SMSUtil smsUtil;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private FileUtils fUtils;

	public String login(OwnerEntity p) {
		OwnerDTO user = mapper.findUserByEmail(p.getEmail());
		
		if(user == null) {
			throw new LoginFailedException("USER_NOT_FOUND");
		}
		if(!passwordEncoder.matches(p.getPw(), user.getPw())) {
			throw new LoginFailedException("PASSWORD_MISMATCH");
		}
		String role = user.getRstrnt_enabled() == 1 ? "OWNER" : null;
		return jwtProvider.provideToken(p.getEmail(), role, user.getRstrnt_pk()).getToken();
	}
	
	public String initialSetting(MultipartFile file, RstrntDTO dto, String token) throws Exception {
		String filename = getFilenameAfterSave(file, dto.getPk());
		
		dto.setProfile_img(filename);
		dto.setReset_pw(passwordEncoder.encode(dto.getReset_pw()));
		
		mapper.resetPw(dto);
		mapper.updRstrntInfo(dto);
		
		Claims currentToken = jwtProvider.provideToken(token).getData();
		String email = currentToken.getSubject();
		int r_pk = (int) currentToken.get("r_pk");
		return jwtProvider.provideToken(email, "OWNER", r_pk).getToken();
	}
	
	public RstrntEntity selRstrnt(int pk) {
		return mapper.selRstrnt(pk);
	}

	// 고객정보 리스트
	public List<ReservEntity> resvInfoList() {
		return mapper.resvList();
	}

	// 고객정보 디테일(1개)
	public int resvDetail(ReservEntity p){
		return mapper.resvDetail(p);
	}

	// 레스토랑 영업 상태(-1,0,1)
	public int updRstState(RstrntEntity p) {
		return mapper.updRstState(p);
	}

	// 고객예약 상태(-3,-2,-1,0,1,2)
	public int updResStatus(ReservEntity p) {

		String soldOut = "재료 소진으로 영업을 종료하였습니다."; // -3
		String rstSitu = "가게 사정으로 영업을 종료하였습니다."; // -2
		String cstomSitu = "고객님의 사정으로 예약을 취소하였습니다."; // -1
		String successReserv = "예약에 성공하셨습니다.";// 0
		String call = "고객님 자리가 준비되었습니다.식당으로 와주세요";// 1

		String to = null;
		
		switch (p.getProcess_status()) {
		case -3:
			smsUtil.send(to, soldOut);
			break;
		case -2:
			smsUtil.send(to, rstSitu);
			break;
		case -1:
			smsUtil.send(to, cstomSitu);
			break;
		case 0:
			smsUtil.send(to, successReserv);
			break;
		case 1:
			smsUtil.send(to, call);
			break;
		}
		return mapper.updResStatus(p);
	}

	// 초기 상태 입력
	public int insRstrnt(RstrntEntity p) {
		return mapper.insRstrnt(p);
	}

	private String getFilenameAfterSave(MultipartFile file, int pk) throws Exception {
		return fUtils.save(file, pk);
	}
}
