package com.personal.doctor.CapstoneDesign.community.domain;

import com.personal.doctor.CapstoneDesign.user.domain.Users;
import com.personal.doctor.CapstoneDesign.user.domain.UsersRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PostsRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PostsRepository postsRepository;

    @AfterEach
    public void deleteAll() {
        postsRepository.deleteAll();
        usersRepository.deleteAll();
    }

    @Test
    @Rollback
    public void 게시물_작성하기() {
        Users users = Users.builder()
                .userID("user")
                .userPassword("password")
                .build();
        usersRepository.save(users);

        Posts posts = Posts.builder()
                .title("title")
                .category("category")
                .question("question")
                .build();
        users.addPosts(posts);
        posts.setUser(users);
        postsRepository.save(posts);

        assertEquals(posts.getUsers().getId(), users.getId());
    }

}