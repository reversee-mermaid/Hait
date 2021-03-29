package com.trainspotting.hait.admin;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.trainspotting.hait.model.AdminEntity;

@Mapper
public interface AdminMapper {
	@Select("Select * From t_admin Where id = #{id}")
	AdminEntity findUserById(String id);
}
