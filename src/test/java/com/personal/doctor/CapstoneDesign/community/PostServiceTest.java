package com.personal.doctor.CapstoneDesign.community;

import com.personal.doctor.CapstoneDesign.community.controller.dto.PostAnsweredResponseDto;
import com.personal.doctor.CapstoneDesign.community.controller.dto.PostSaveRequestDto;
import com.personal.doctor.CapstoneDesign.community.controller.dto.PostUpdateRequestDto;
import com.personal.doctor.CapstoneDesign.community.service.PostService;
import com.personal.doctor.CapstoneDesign.user.controller.dto.UserJoinRequestDto;
import com.personal.doctor.CapstoneDesign.community.domain.Posts;
import com.personal.doctor.CapstoneDesign.community.domain.PostsRepository;
import com.personal.doctor.CapstoneDesign.user.domain.Users;
import com.personal.doctor.CapstoneDesign.user.domain.UsersRepository;
import com.personal.doctor.CapstoneDesign.util.exceptions.PostNOTExistException;
import com.personal.doctor.CapstoneDesign.user.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class PostServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PostsRepository postsRepository;

    @AfterEach
    public void clean() {
        postsRepository.deleteAll();
        postService.deleteAll();
        usersRepository.deleteAll();
        userService.deleteAll();
    }

    @Test
    @Rollback
    public void 게시물_저장() {
        UserJoinRequestDto userRequestDto = UserJoinRequestDto.builder()
                .userID("ID")
                .userPassword("PW")
                .build();
        Long userID = userService.join(userRequestDto);

        PostSaveRequestDto postRequestDto = PostSaveRequestDto.builder()
                .title("title")
                .category("category")
                .question("question")
                .build();
        Long postID = postService.save(userID, postRequestDto);

        Posts posts = postsRepository.findById(postID)
                .orElseThrow(() -> new PostNOTExistException("게시물이 존재하지 않습니다."));

        Assertions.assertEquals("title", posts.getTitle());
    }

    @Test
    @Rollback
    public void 게시물_수정() {
        UserJoinRequestDto userRequestDto = UserJoinRequestDto.builder()
                .userID("ID")
                .userPassword("PW")
                .build();
        Long userID = userService.join(userRequestDto);

        PostSaveRequestDto postRequestDto = PostSaveRequestDto.builder()
                .title("title")
                .category("category")
                .question("question1")
                .build();
        Long postID = postService.save(userID, postRequestDto);

        PostUpdateRequestDto updateRequestDto = PostUpdateRequestDto.builder()
                .title("title")
                .question("question2")
                .build();
        postService.update(postID, updateRequestDto);

        Posts posts = postsRepository.findById(postID)
                .orElseThrow(() -> new PostNOTExistException("게시물이 존재하지 않습니다."));

        Assertions.assertEquals("question2", posts.getQuestion());
    }

    @Test
    @Rollback
    public void 게시물_답변() {
        UserJoinRequestDto userRequestDto = UserJoinRequestDto.builder()
                .userID("ID_usr")
                .userPassword("PW_usr")
                .build();
        Long userID = userService.join(userRequestDto);

        UserJoinRequestDto doctorRequestDto = UserJoinRequestDto.builder()
                .userID("ID_doc")
                .userPassword("PW_doc")
                .build();
        Long doctorID = userService.join(doctorRequestDto);

        Users doctor = usersRepository.findById(doctorID).get();
        userService.updateRole(doctorID);

        PostSaveRequestDto postRequestDto = PostSaveRequestDto.builder()
                .title("title")
                .category("category")
                .question("question")
                .build();
        Long postID = postService.save(userID, postRequestDto);

        PostAnsweredResponseDto answeredResponseDto = PostAnsweredResponseDto.builder()
                .answer("answer")
                .docName("docName")
                .build();
        postService.answered(doctorID, postID, answeredResponseDto);

        Posts posts = postsRepository.findById(postID)
                .orElseThrow(() -> new PostNOTExistException("게시물이 존재하지 않습니다."));

        Assertions.assertEquals("docName", posts.getDocName());
    }

}