package com.itstep.yuliandr.socNtW.soc_network.facade;

import com.itstep.yuliandr.socNtW.soc_network.dto.PostDTO;

import com.itstep.yuliandr.socNtW.soc_network.entity.Post;

import org.springframework.stereotype.Component;

@Component
public class PostFacade {
    public PostDTO postToPostDTO(Post post){
        //представление пользователя для клиентской стороны
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setUsername(post.getUser().getUsername());
        postDTO.setCaption(post.getCaption());
        postDTO.setLikes(post.getLikes());
        postDTO.setUsersLiked(post.getLikedUsers());
        postDTO.setLocation(post.getLocation());
        postDTO.setTitle(post.getTitle());
        return postDTO;
    }
}
