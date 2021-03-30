package com.trainspotting.hait.rstrnt;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.trainspotting.hait.model.ReservEntity;
import com.trainspotting.hait.model.RstrntDTO;
import com.trainspotting.hait.model.RstrntEntity;

@Mapper
public interface RstrntMapper {
	//owner
	RstrntDTO selRstrnt(int pk);
	int resetPw(RstrntDTO dto);
	int updRstrnt(RstrntEntity p);
	void updRstrntState(RstrntEntity p);
	List<ReservEntity> selReservAll(int pk);
	void updReservStatus(ReservEntity p);
	
	//customer
	List<RstrntEntity> selRstrntAll(RstrntEntity param);
	
	int countRealtimeTotal(int rstrnt_pk);	
}
