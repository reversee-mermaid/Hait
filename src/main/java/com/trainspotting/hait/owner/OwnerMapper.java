package com.trainspotting.hait.owner;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.trainspotting.hait.model.OwnerDTO;
import com.trainspotting.hait.model.OwnerEntity;
import com.trainspotting.hait.model.ReservEntity;
import com.trainspotting.hait.model.RstrntDTO;
import com.trainspotting.hait.model.RstrntEntity;

@Mapper
public interface OwnerMapper {

	// owner
	OwnerEntity selOwnerByRstrntPk(int r_pk);
	OwnerDTO findUserByEmail(String email);
	int resetPw(RstrntDTO dto);

	// restaurant
	RstrntEntity selRstrnt(int pk);
	int updRstrnt(RstrntEntity p);
	void updRstrntState(RstrntEntity p);

	// reservation
	List<ReservEntity> selReservAll(int pk);
	void updReservStatus(ReservEntity p);
}
