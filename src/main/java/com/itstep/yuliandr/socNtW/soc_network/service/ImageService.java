package com.itstep.yuliandr.socNtW.soc_network.service;

import com.itstep.yuliandr.socNtW.soc_network.entity.Image;
import com.itstep.yuliandr.socNtW.soc_network.entity.Post;
import com.itstep.yuliandr.socNtW.soc_network.entity.User;
import com.itstep.yuliandr.socNtW.soc_network.exceptions.ImageNotFoundException;
import com.itstep.yuliandr.socNtW.soc_network.repository.ImageRepository;
import com.itstep.yuliandr.socNtW.soc_network.repository.PostRepository;
import com.itstep.yuliandr.socNtW.soc_network.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class ImageService {
    public static final Logger LOGGER = LoggerFactory.getLogger(CommentService.class);
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository, UserRepository userRepository, PostRepository postRepository) {
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }


    public Image uploadImageToUser(MultipartFile file, Principal principal) throws IOException {
        User user = getUserByPrincipal(principal);
        LOGGER.info("Uploading image profile to User {}", user.getUsername());
        Image userProfileImage = imageRepository.findByUserId(user.getId()).orElse(null);
        //проверка наличия фото профайла + удаление, если есть
        if (!ObjectUtils.isEmpty(userProfileImage)) {
            imageRepository.delete(userProfileImage);
        }
        //создание нового
        Image image = new Image();
        image.setUserId(user.getId());
        image.setImageBytes(compressBytes(file.getBytes()));
        image.setName(file.getOriginalFilename());
        return imageRepository.save(image);
    }

    public Image uploadImageToPost(MultipartFile file, Principal principal, Long postId) throws IOException {
        User user = getUserByPrincipal(principal);
        Post post = user.getPosts().stream().filter(p -> p.getId().equals(postId)).collect(toSinglePostCollectors());
        Image image = new Image();
        image.setPostId(post.getId());
        image.setImageBytes(file.getBytes());
        image.setImageBytes(compressBytes(file.getBytes()));
        image.setName(file.getOriginalFilename());
        LOGGER.info("Uploading image to Post {}", post.getId());
        return imageRepository.save(image);
    }

    public Image getImageToUser(Principal principal) {
        User user = getUserByPrincipal(principal);
        Image image = imageRepository.findByUserId(user.getId()).orElse(null);
        if (!ObjectUtils.isEmpty(image)) {
            image.setImageBytes(decompressBytes(image.getImageBytes()));
        }
        return image;
    }

    public Image getImageToPost(Long postId) {
        Image image = imageRepository.findByPostId(postId)
                .orElseThrow(() -> new ImageNotFoundException("Cannot find image to Post " + postId));
        if (!ObjectUtils.isEmpty(image)) {
            image.setImageBytes(decompressBytes(image.getImageBytes()));
        }
        return image;
    }

    //метод для сжатия файлов перед размещением
    private byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        //устанавливает входные данные для сжатия
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            LOGGER.error("Cannot compress Bytes");
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();

    }

    private byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();

        } catch (IOException | DataFormatException e) {
            LOGGER.error("Cannot compress Bytes");
        }
        return outputStream.toByteArray();
    }

    private <T> Collector<T, ?, T> toSinglePostCollectors() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.size() != 1) {
                        throw new IllegalStateException();
                    }
                    return list.get(0);
                }
        );
    }

    public User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException
                ("User with username " + username + " not found "));
    }
}
