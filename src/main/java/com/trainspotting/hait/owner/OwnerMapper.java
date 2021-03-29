package com.trainspotting.hait.owner;

import org.apache.ibatis.annotations.Mapper;

import com.trainspotting.hait.model.OwnerDTO;
import com.trainspotting.hait.model.OwnerEntity;

@Mapper
public interface OwnerMapper {

	// owner
	OwnerEntity selOwnerByRstrntPk(int r_pk);
	OwnerDTO findUserByEmail(String email);
}