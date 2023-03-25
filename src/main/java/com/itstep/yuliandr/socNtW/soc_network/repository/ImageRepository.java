package com.itstep.yuliandr.socNtW.soc_network.repository;

import com.itstep.yuliandr.socNtW.soc_network.entity.Image;

import com.itstep.yuliandr.socNtW.soc_network.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findByUserId(Long userId);
    Optional<Image> findByPostId(Long postId);



}
