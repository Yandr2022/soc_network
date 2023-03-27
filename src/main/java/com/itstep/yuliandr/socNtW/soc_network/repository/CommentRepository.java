package com.itstep.yuliandr.socNtW.soc_network.repository;

import com.itstep.yuliandr.socNtW.soc_network.entity.Comment;
import com.itstep.yuliandr.socNtW.soc_network.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {


    List<Comment> findAllByPost(Post post);

    Comment findByIdAndUserId(Long commentId, Long userId);



}
