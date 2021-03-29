package com.trainspotting.hait.reserv;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.trainspotting.hait.model.ReservDTO;
import com.trainspotting.hait.model.ReservEntity;
import com.trainspotting.hait.model.RstrntDTO;

@Mapper
public interface ReservMapper {
	//owner
	List<ReservEntity> selReservAll(int pk);
	void updReservStatus(ReservEntity p);
	
	
	//customer
	RstrntDTO selRstrnt(int pk);
	
	int countRealtimeTotal(int rstrnt_pk);
	
	void insReserv(ReservEntity param);
	ReservDTO selReserv(int pk);
	ReservEntity findReservByContact(String contact);
	
}
