package com.trainspotting.hait.city;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.trainspotting.hait.model.CityEntity;

@Mapper
public interface CityMapper {
	@Select("select pk, nm from t_city")
	public List<CityEntity> selectAll();
}
