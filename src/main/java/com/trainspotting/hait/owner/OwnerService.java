package com.trainspotting.hait.owner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.trainspotting.hait.exception.LoginFailedException;
import com.trainspotting.hait.jwt.JwtProvider;
import com.trainspotting.hait.model.OwnerDTO;
import com.trainspotting.hait.model.OwnerEntity;

@Service
public class OwnerService {

	@Autowired
	private OwnerMapper mapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtProvider jwtProvider;

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
}
