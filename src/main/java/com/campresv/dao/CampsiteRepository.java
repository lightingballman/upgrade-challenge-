package com.campresv.dao;

import com.campresv.dto.CampsiteDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampsiteRepository extends JpaRepository<CampsiteDto, Integer> {

}