package com.itstep.yuliandr.socNtW.soc_network.controller;


import com.itstep.yuliandr.socNtW.soc_network.entity.Image;
import com.itstep.yuliandr.socNtW.soc_network.payload.responce.MessageResponse;
import com.itstep.yuliandr.socNtW.soc_network.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("/api/image")
public class ImageController {
    @Autowired
    private ImageService service;


    @PostMapping("/upload")
    public ResponseEntity<MessageResponse> uploadImageToUser(@RequestParam("file") MultipartFile file
            , Principal principal) throws IOException {
        service.uploadImageToUser(file, principal);
        return ResponseEntity.ok(new MessageResponse("Image Uploaded Successfully"));
    }

    @PostMapping("/{postId}/upload")
    public ResponseEntity<MessageResponse> uploadImageToPost(@PathVariable("postId") String postId
            , @RequestParam ("file") MultipartFile file, Principal principal) throws IOException {
        service.uploadImageToPost(file, principal,Long.parseLong(postId));
        return ResponseEntity.ok(new MessageResponse("Image Uploaded Successfully"));
    }
    @GetMapping("/profileImage")
    public ResponseEntity<Image> getImageForUser( Principal principal)  {
        Image userImage = service.getImageToUser(principal);
        return new ResponseEntity<>(userImage, HttpStatus.OK);
    }
    @GetMapping("/{postId}/image")
    public ResponseEntity<Image> getImageToPost(@PathVariable("postId") String postId)  {
        Image image = service.getImageToPost(Long.parseLong(postId));
        return new ResponseEntity<>(image, HttpStatus.OK);
    }


}
