package com.itstep.yuliandr.socNtW.soc_network.facade;

import com.itstep.yuliandr.socNtW.soc_network.dto.CommentDTO;
import com.itstep.yuliandr.socNtW.soc_network.dto.PostDTO;
import com.itstep.yuliandr.socNtW.soc_network.entity.Comment;
import com.itstep.yuliandr.socNtW.soc_network.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class CommentFacade {
    public CommentDTO commentToCommentDTO(Comment comment){
        //представление пользователя для клиентской стороны
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setUsername(comment.getUsername());
        commentDTO.setMessage(comment.getMessage());

        return commentDTO;
    }
}
