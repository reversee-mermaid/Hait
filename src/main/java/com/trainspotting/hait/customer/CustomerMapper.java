package com.trainspotting.hait.customer;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.trainspotting.hait.model.ReservDTO;
import com.trainspotting.hait.model.ReservEntity;
import com.trainspotting.hait.model.RstrntDTO;
import com.trainspotting.hait.model.RstrntEntity;

@Mapper
public interface CustomerMapper {
	List<RstrntEntity> selRstrntAll(RstrntEntity param);
	RstrntDTO selRstrnt(int pk);
	
	int countRealtimeTotal(int rstrnt_pk);
	
	void insReserv(ReservEntity param);
	ReservDTO selReserv(int pk);
	ReservEntity findReservByContact(String contact);
	
}
