package com.campresv.dao;

import com.campresv.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserDto, Integer> {
    @Query("from com.campresv.dto.UserDto where email = :inputEmailAddress")
    List<UserDto> findByEmailAddress(@Param("inputEmailAddress") String email);
}