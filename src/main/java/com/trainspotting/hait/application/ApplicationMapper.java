package com.trainspotting.hait.application;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.trainspotting.hait.model.ApplicationDTO;
import com.trainspotting.hait.model.ApplicationEntity;
import com.trainspotting.hait.model.OwnerEntity;
import com.trainspotting.hait.model.RstrntEntity;

@Mapper
public interface ApplicationMapper {
	ApplicationEntity findOwnerEmail(ApplicationEntity p);
	ApplicationEntity findOwnerContact(ApplicationEntity p);
	ApplicationEntity findRstrntName(ApplicationEntity p);
	int insert(ApplicationEntity p);
	List<ApplicationDTO> listAll();
	List<ApplicationDTO> listStatus(int p);
	ApplicationDTO detail(ApplicationEntity p);
	int update(ApplicationEntity p);
	int insOwner(OwnerEntity p);
	int insRstrnt(ApplicationEntity p);
}
