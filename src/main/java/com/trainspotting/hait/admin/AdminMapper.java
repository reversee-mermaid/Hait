package com.trainspotting.hait.admin;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.trainspotting.hait.model.AdminEntity;
import com.trainspotting.hait.model.ApplicationDTO;
import com.trainspotting.hait.model.ApplicationEntity;
import com.trainspotting.hait.model.OwnerEntity;

@Mapper
public interface AdminMapper {
	@Select("Select * From t_admin Where id = #{id}")
	AdminEntity findUserById(String id);
	List<ApplicationDTO> listAll();
	List<ApplicationDTO> listStatus(int p);
	ApplicationDTO detail(ApplicationEntity p);
	int update(ApplicationEntity p);
	int insOwner(OwnerEntity p);
	int insRstrnt(ApplicationEntity p);
}
