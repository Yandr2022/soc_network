package com.itstep.yuliandr.socNtW.soc_network.controller;

import com.itstep.yuliandr.socNtW.soc_network.dto.PostDTO;
import com.itstep.yuliandr.socNtW.soc_network.entity.Post;
import com.itstep.yuliandr.socNtW.soc_network.facade.PostFacade;
import com.itstep.yuliandr.socNtW.soc_network.payload.responce.MessageResponse;
import com.itstep.yuliandr.socNtW.soc_network.service.PostService;
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
@CrossOrigin//разрешает все источники и HTTP-методы аннотации @RequestMapping
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostService service;
    @Autowired
    private PostFacade facade;
    @Autowired
    private ResponseErrorValidation errorValidation;

    @PostMapping("/create")
    public ResponseEntity<Object> createPost(@Valid @RequestBody PostDTO postDTO, BindingResult result
            , Principal principal) {
        ResponseEntity<Object> errors = errorValidation.mapValidationService(result);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        Post post = service.createPost(postDTO, principal);
        PostDTO createdPost = facade.postToPostDTO(post);

        return new ResponseEntity<>(createdPost, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> postDTOList = service.getAllPosts().stream().map(facade::postToPostDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(postDTOList, HttpStatus.OK);

    }
    @GetMapping("/user/posts")
    public ResponseEntity<List<PostDTO>> getAllPostsForUser(Principal principal) {
        List<PostDTO> postDTOList = service.getAllPostsForUser(principal).stream().map(facade::postToPostDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(postDTOList, HttpStatus.OK);
    }

    @PostMapping("/{postId}/{username}/like")
    public ResponseEntity<PostDTO> likePost(@PathVariable("postId") String postId
            , @PathVariable("username") String username) {
        Post post = service.likePost(Long.parseLong(postId), username);
        PostDTO postDTO = facade.postToPostDTO(post);

        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }
    @PostMapping("/{postId}/delete")
    public ResponseEntity<MessageResponse> deletePost(@PathVariable("postId") String postId, Principal principal){
        service.deletePost(Long.parseLong(postId), principal);
        return new ResponseEntity<>(new MessageResponse("Post was deleted"), HttpStatus.OK);

    }


}
