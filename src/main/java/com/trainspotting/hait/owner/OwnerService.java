package com.trainspotting.hait.owner;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.trainspotting.hait.exception.LoginFailedException;
import com.trainspotting.hait.jwt.JwtProvider;
import com.trainspotting.hait.model.OwnerDTO;
import com.trainspotting.hait.model.OwnerEntity;
import com.trainspotting.hait.model.ReservEntity;
import com.trainspotting.hait.model.RstrntDTO;
import com.trainspotting.hait.model.RstrntEntity;
import com.trainspotting.hait.util.FileUtil;
import com.trainspotting.hait.util.SMSUtil;

import io.jsonwebtoken.Claims;

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
	private FileUtil fileUtil;

	public OwnerEntity selOwnerByRstrntPk(int r_pk) {
		OwnerEntity owner = mapper.selOwnerByRstrntPk(r_pk);
		owner.setPw(null);
		return owner;
	}

	public String login(OwnerEntity p) {
		OwnerDTO user = mapper.findUserByEmail(p.getEmail());

		if (user == null) {
			throw new LoginFailedException("USER_NOT_FOUND");
		}
		if (!passwordEncoder.matches(p.getPw(), user.getPw())) {
			throw new LoginFailedException("PASSWORD_MISMATCH");
		}
		String role = user.getRstrnt_enabled() == 1 ? "OWNER" : null;
		return jwtProvider.provideToken(p.getEmail(), role, user.getRstrnt_pk()).getToken();
	}

	public String initialSetting(MultipartFile file, RstrntDTO dto, String token) throws Exception {
		dto.setLocation(decoding(dto.getLocation()));
		if (dto.getMore_info() != null) {
			dto.setMore_info(decoding(dto.getMore_info()));
		}

		String filename = getFilenameAfterSave(file, dto.getPk());

		dto.setProfile_img(filename);
		dto.setReset_pw(passwordEncoder.encode(dto.getReset_pw()));

		mapper.resetPw(dto);
		mapper.updRstrnt(dto);

		Claims currentToken = jwtProvider.provideToken(token).getData();
		String email = currentToken.getSubject();
		int r_pk = (int) currentToken.get("r_pk");
		return jwtProvider.provideToken(email, "OWNER", r_pk).getToken();
	}

	public RstrntEntity selRstrnt(int pk) {
		return mapper.selRstrnt(pk);
	}

	public RstrntEntity updRstrnt(int r_pk, MultipartFile file, String more_info) throws Exception {
		RstrntDTO dto = new RstrntDTO();
		dto.setPk(r_pk);

		if (file != null) {
			dto.setProfile_img(getFilenameAfterSave(file, r_pk));
		}
		if (more_info != null) {
			dto.setMore_info(decoding(more_info));
		}
		mapper.updRstrnt(dto);
		return dto;
	}

	public void updRstrntState(RstrntEntity param) {
		int state = param.getState();
		if (state == -1) {
			List<ReservEntity> list = selReservAll(param.getPk());
			for (ReservEntity reserv : list) {
				reserv.setProcess_status(-2);
				updReservStatus(reserv);
			}
		}
		mapper.updRstrntState(param);
	}

	public List<ReservEntity> selReservAll(int pk) {
		return mapper.selReservAll(pk);
	}

	public void updReservStatus(ReservEntity param) {
		String message = null;
		switch (param.getProcess_status()) {
		case 2:
			message = "착석 확인 되었습니다. 맛있는 식사 하세요!";
			break;
		case 1:
			message = "고객님, 자리가 준비 되었습니다. 매장으로 방문해주세요.";
			break;
		case -1:
			message = "고객님의 사정으로 인해 예약이 취소 되었습니다.";
			break;
		case -2:
			message = "가게의 사정으로 인해 예약이 취소 되었습니다.";
			break;
		case -3:
			message = "재료 소진으로 인해 예약이 취소 되었습니다.";
			break;
		default:
			break;
		}

		smsUtil.send(param.getContact(), message);
		mapper.updReservStatus(param);
	}

	private String getFilenameAfterSave(MultipartFile file, int pk) throws Exception {
		return fileUtil.save(file, pk);
	}

	private String decoding(String data) {
		try {
			return new String(data.getBytes("8859_1"), "utf-8");
		} catch (UnsupportedEncodingException e) {}

		return null;
	}
}
