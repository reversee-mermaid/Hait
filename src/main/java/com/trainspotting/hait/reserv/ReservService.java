package com.trainspotting.hait.reserv;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainspotting.hait.exception.ReservDuplicatedException;
import com.trainspotting.hait.model.ReservDTO;
import com.trainspotting.hait.model.ReservEntity;
import com.trainspotting.hait.model.RstrntDTO;
import com.trainspotting.hait.util.SMSUtil;

@Service
public class ReservService {
	
	@Autowired
	private ReservMapper mapper;
	
	@Autowired
	private SMSUtil smsUtil;
	
	//owner
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
	
	//customer
	public RstrntDTO selRstrnt(int pk) {
		RstrntDTO data = mapper.selRstrnt(pk);
		
		int realtimeTotal = countRealtimeTotal(pk);
		if(realtimeTotal != 0) {
			data.setRealtime_total(realtimeTotal);
		}
		
		return data;
	}
	
	public void insReserv(ReservEntity param) {
		ReservEntity duplicated = findReservByContact(param.getContact());
		
		if(duplicated != null) {
			throw new ReservDuplicatedException();
		};
		
		mapper.insReserv(param);
	}
	
	public ReservDTO selReserv(int pk) {
		ReservDTO result = mapper.selReserv(pk);
		
		int rstrnt_pk = result.getRstrnt_pk();
		int realtime_total = countRealtimeTotal(rstrnt_pk);
		
		String contact = result.getContact();
		result.setContact(contactMarking(contact));
		
		result.setRstrnt(selRstrnt(rstrnt_pk));
		result.getRstrnt().setRealtime_total(realtime_total);
		
		return result;
	}
	
	private ReservEntity findReservByContact(String contact) {
		return mapper.findReservByContact(contact);
	}
	
	private int countRealtimeTotal(int pk) {
		return mapper.countRealtimeTotal(pk);
	}
	
	private String contactMarking(String current) {
		StringBuffer contact = new StringBuffer(current);
		contact.replace(3, 7, "****");
		return contact.toString();
	}
}
