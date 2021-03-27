package com.trainspotting.hait.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainspotting.hait.exception.ReservDuplicatedException;
import com.trainspotting.hait.model.ReservDTO;
import com.trainspotting.hait.model.ReservEntity;
import com.trainspotting.hait.model.RstrntDTO;
import com.trainspotting.hait.model.RstrntEntity;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerMapper mapper;

	public List<RstrntEntity> selRstrntAll(String nm, String city_pk) {
		RstrntEntity param = new RstrntEntity();
		
		if(nm != null && nm.trim() != "") {
			param.setNm(nm.trim());
		}
		if(city_pk != null && city_pk != "") {
			param.setCity_pk(Integer.parseInt(city_pk));
		}
		
		return mapper.selRstrntAll(param);
	}

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
		// TODO contact marking
		// String contact = result.getContact();

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
