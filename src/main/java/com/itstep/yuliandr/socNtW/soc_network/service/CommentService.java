package com.itstep.yuliandr.socNtW.soc_network.service;

import com.itstep.yuliandr.socNtW.soc_network.dto.CommentDTO;
import com.itstep.yuliandr.socNtW.soc_network.entity.Comment;
import com.itstep.yuliandr.socNtW.soc_network.entity.Post;
import com.itstep.yuliandr.socNtW.soc_network.entity.User;
import com.itstep.yuliandr.socNtW.soc_network.exceptions.PostNotFoundException;
import com.itstep.yuliandr.socNtW.soc_network.repository.CommentRepository;
import com.itstep.yuliandr.socNtW.soc_network.repository.PostRepository;
import com.itstep.yuliandr.socNtW.soc_network.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    public static final Logger LOGGER = LoggerFactory.getLogger(CommentService.class);

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public Comment saveComment(Long postId, CommentDTO commentDTO, Principal principal) {
        User user = getUserByPrincipal(principal);
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException
                ("Post cannot be found for username " + user.getEmail()));
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUserId(user.getId());
        comment.setUsername(user.getUsername());
        comment.setMessage(commentDTO.getMessage());
        LOGGER.info("Saving comment for Post: {}", post.getId());
        return commentRepository.save(comment);

    }

    public List<Comment> getAllCommentsForPost(Long postId) {
        Post post= postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException
                ("Post cannot be found" ));
        List<Comment> comments = commentRepository.findAllByPost(post);
        return comments;
    }

    public void deleteComment(Long commentId){
        Optional<Comment> comment = commentRepository.findById(commentId);
        comment.ifPresent(commentRepository::delete);
    }

    public User getUserByPrincipal(Principal principal){
        String username = principal.getName();
        return userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException
                ("User with username " + username + " not found "));
    }
}
