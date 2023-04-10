package com.itstep.yuliandr.socNtW.soc_network.controller;

import com.itstep.yuliandr.socNtW.soc_network.dto.CommentDTO;
import com.itstep.yuliandr.socNtW.soc_network.entity.Comment;
import com.itstep.yuliandr.socNtW.soc_network.facade.CommentFacade;
import com.itstep.yuliandr.socNtW.soc_network.payload.responce.MessageResponse;
import com.itstep.yuliandr.socNtW.soc_network.service.CommentService;
import com.itstep.yuliandr.socNtW.soc_network.validations.ResponseErrorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private CommentService service;
    @Autowired
    private CommentFacade facade;
    @Autowired
    private ResponseErrorValidation errorValidation;

    @PostMapping("/{postId}/create")
    public ResponseEntity<Object> createComment(@Valid @RequestBody CommentDTO commentDTO
            , @PathVariable("postId") String postId,  BindingResult result
            , Principal principal) {
        ResponseEntity<Object> errors = errorValidation.mapValidationService(result);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Comment comment = service.saveComment(Long.parseLong(postId), commentDTO, principal);
        CommentDTO createdComment = facade.commentToCommentDTO(comment);

        return new ResponseEntity<>(createdComment, HttpStatus.OK);
    }

    @GetMapping("/{postId}/all")
    public ResponseEntity<List<CommentDTO>> getAllCommentsToPost(@PathVariable("postId") String postId) {
        List<CommentDTO> commentDTOList = service.getAllCommentsForPost(Long.parseLong(postId))
                .stream().map(facade::commentToCommentDTO ).collect(Collectors.toList());

        return new ResponseEntity<>(commentDTOList, HttpStatus.OK);

    }


    @PostMapping("/{commentId}/delete")
    public ResponseEntity<MessageResponse> deleteComment(@PathVariable("commentId") String commentId){
        service.deleteComment(Long.parseLong(commentId));
        return new ResponseEntity<>(new MessageResponse("Comment was deleted"), HttpStatus.OK);
    }


}
