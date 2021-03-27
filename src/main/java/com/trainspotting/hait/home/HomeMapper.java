package com.trainspotting.hait.home;

import org.apache.ibatis.annotations.Mapper;

import com.trainspotting.hait.model.ApplicationEntity;

@Mapper
public interface HomeMapper {
	int insert(ApplicationEntity p);
}
