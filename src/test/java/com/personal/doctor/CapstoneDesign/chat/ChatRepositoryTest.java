package com.personal.doctor.CapstoneDesign.chat;

import com.personal.doctor.CapstoneDesign.chat.domain.Chat;
import com.personal.doctor.CapstoneDesign.chat.domain.ChatRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class ChatRepositoryTest {

    @Autowired
    private ChatRepository chatRepository;

    @AfterEach
    public void clean() {
        chatRepository.deleteAll();
    }

    @Test
    public void 채팅_저장() {
        Chat chat = Chat.builder()
                .requestText("40세 남자인데 무릎이 너무 아파. 어떻게 해야할까?")
                .responseText("Bard 대답")
                .build();
        chatRepository.save(chat);

        assertEquals(1, chatRepository.count());
    }

}
