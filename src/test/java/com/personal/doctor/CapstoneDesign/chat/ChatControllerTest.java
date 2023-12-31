package com.personal.doctor.CapstoneDesign.chat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.doctor.CapstoneDesign.chat.controller.dto.ChatRequestDto;
import com.personal.doctor.CapstoneDesign.chat.controller.dto.ChatSaveRequestDto;
import com.personal.doctor.CapstoneDesign.chat.domain.Chat;
import com.personal.doctor.CapstoneDesign.chat.domain.ChatRepository;
import com.personal.doctor.CapstoneDesign.chat.service.ChatService;
import com.personal.doctor.CapstoneDesign.user.controller.dto.UserJoinRequestDto;
import com.personal.doctor.CapstoneDesign.user.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserService userService;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatService chatService;

    private static Long userId;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        UserJoinRequestDto userJoinRequestDto = UserJoinRequestDto.builder()
                .userID("ID")
                .userPassword("PW")
                .build();
        userId = userService.join(userJoinRequestDto);
    }

    @AfterEach
    public void clean() {
        chatService.deleteAll();
        userService.deleteAll();
    }

    @Test
    public void 채팅_저장() throws Exception {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("requestText", "채팅 저장 api 테스트");
        requestMap.put("responseText", "채팅 저장 api 테스트");
        String content = new ObjectMapper().writeValueAsString(requestMap);

        mockMvc.perform(
                        post("/chat/" + userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                )
                .andDo(print())
                .andExpect(status().isOk());

        List<Chat> chats = chatRepository.findChatByUsersId(userId);

        assertEquals("채팅 저장 api 테스트", chats.get(0).getRequestText());
        assertEquals("채팅 저장 api 테스트", chats.get(0).getResponseText());
    }

    @Test
    public void 채팅_반환() throws Exception {

        ChatSaveRequestDto requestDto1 = ChatSaveRequestDto.builder()
                .requestText("40세 남자인데 무릎이 너무 아파. 어떻게 해야할까?")
                .responseText("Bard 대답")
                .build();
        chatService.save(userId, requestDto1);
        ChatSaveRequestDto requestDto2 = ChatSaveRequestDto.builder()
                .requestText("72 여자인데 배가 너무 아파. 어떻게 해야할까?")
                .responseText("Bard 대답")
                .build();
        chatService.save(userId, requestDto2);

        MvcResult mvcResult = mockMvc.perform(
                        get("/userChats/" + userId)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<ChatRequestDto> chats = new ObjectMapper().readValue(jsonResponse, new TypeReference<List<ChatRequestDto>>() {
        });

        assertEquals(2, chats.size());
        assertEquals("40세 남자인데 무릎이 너무 아파. 어떻게 해야할까?", chats.get(0).getRequestText());
        assertEquals("Bard 대답", chats.get(0).getResponseText());
        assertEquals("72 여자인데 배가 너무 아파. 어떻게 해야할까?", chats.get(1).getRequestText());
        assertEquals("Bard 대답", chats.get(1).getResponseText());
    }

}
