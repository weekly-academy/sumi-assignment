package com.mentoring.assignment.membershippoint.domain.member.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mentoring.assignment.membershippoint.domain.member.application.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Mockito와 관련된 테스트 환경 설정
@ExtendWith(MockitoExtension.class)
class MemberControllerTest {

    // 클래스의 인스턴스 주입받음 -> 테스트할 컨트롤러 객체
    @InjectMocks
    MemberController memberController;

    // 클래스의 Mock 객체를 생성하고 주입
    // 컨트롤러가 의존하는 서비스를 Mocking하여 테스트 중에 서비스 동작 제어할 수 있게 해줌
    @Mock
    MemberService memberService;

    // Spring MVC를 사용하여 REST API 요청을 시뮬레이션하고, 테스트하기 위한 MockMVc 객체 생성
    MockMvc mockMvc;

    // Json data 처리
    private Gson gson;

    // 각 테스트 메서드가 실행되기 전에 호출되는 메서드
    @BeforeEach
    public void beforeEach() {
        // MockMvc 초기화, 설정
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
    }

    @Test
    @DisplayName("바코드 생성 컨트롤러 로직 확인")
    void createBarcode() throws Exception {
        // given
        Long userId = 1L;

        // when
        mockMvc.perform(
                // HTTP POST 요청
                // "/member/barcode" 엔드포인트로 보내짐
                post("/member/barcode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(userId)))
                .andExpect(status().isCreated());

        // then


        // memberService의 issueBarcode와 함께 호출되었는지 확인
        verify(memberService).issueBarcode(userId);


    }

    @Test
    void savePoint() {
    }

    @Test
    void usePoint() {
    }
}