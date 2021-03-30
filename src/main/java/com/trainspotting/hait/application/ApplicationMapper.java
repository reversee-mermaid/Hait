package com.trainspotting.hait.application;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.trainspotting.hait.model.ApplicationDTO;
import com.trainspotting.hait.model.ApplicationEntity;
import com.trainspotting.hait.model.OwnerEntity;

@Mapper
public interface ApplicationMapper {
	ApplicationEntity findOwnerEmail(ApplicationEntity p);
	ApplicationEntity findOwnerContact(ApplicationEntity p);
	ApplicationEntity findRstrntName(ApplicationEntity p);
	int insert(ApplicationEntity p);
	List<ApplicationDTO> listAll(int process_status);
	ApplicationDTO detail(int pk);
	int update(ApplicationEntity p);
	int insOwner(OwnerEntity p);
	int insRstrnt(ApplicationEntity p);
}
