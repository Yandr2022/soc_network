package com.yuliandr.learn.socNtW.repository;

import com.yuliandr.learn.socNtW.entity.Comment;
import com.yuliandr.learn.socNtW.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {


    List<Comment> findAllByPost(Post post);

    Comment findByIdAndUserId(Long commentId, Long userId);



}
