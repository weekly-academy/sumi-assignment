package com.mentoring.assignment.membership.domain.member.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mentoring.assignment.membership.domain.barcode.application.BarcodeReader;
import com.mentoring.assignment.membership.domain.member.application.MemberService;
import com.mentoring.assignment.membership.domain.pointhistory.application.PointHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {
    // 스프링 부트 3.X 버전 맞추기 -> 에러 발생 해결

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    @MockBean
    private BarcodeReader barcodeService;

    @MockBean
    private PointHistoryService pointHistoryService;

//    @Test
//    @DisplayName("성공_멤버가_바코드를_처음_발급했을 때")
//    void createBarcode() throws Exception {
//        // given
//        MemberDto memberDto = MemberDto.builder().userId(140786709L).build();
//
//        String[] arr = new String[2];
//        String newBarcodeNumber = "99999999";
//        given(barcodeService.createBarcode()).willReturn(newBarcodeNumber);
//        arr[0] = "바코드를 생성했습니다.";
//        arr[1] = newBarcodeNumber;
//
//        String body = objectMapper.writeValueAsString(memberDto);
//
//
//        // 내부 구현이 없는 메서드를 호출하는 것이므로
//        // 이 메서드를 호출했을 때 바코드 넘버를 반환하는 행동을 미리 지정한 것
//        given(memberService.issueBarcode(memberDto.getUserId())).willReturn(arr);
//
//        // when
//        ResultActions actions = mockMvc.perform(post("/member/barcode")
//                    .contentType(MediaType.APPLICATION_JSON) // 보내는 데이터의 타입 명시
//                    .content(body)  // HTTP Body에 데이터 담기
//                );
//
//        // then
//        actions.andExpect(status().isCreated()) // HTTP 응답값
//                // 요청을 보낸 후 응답에 대해 검증
//                .andExpect(jsonPath("$.message").value("바코드를 생성했습니다."))
//                .andExpect(jsonPath("$.data").value("99999999"))
//                // return 결과 반환
//                .andReturn();
//
//
//    }
//
//    @Test
//    @DisplayName("성공_멤버가_이미_바코드를_가지고_있을때")
//    void showBarcode() throws Exception {
//        // given
//        MemberDto memberDto = MemberDto.builder().userId(140786709L).build();
//        Barcode barcode = Barcode.builder()
//                .barcodeNumber("99999999")
//                .build();
//
//        Member member = Member.builder()
//                .id(140786709L)
//                .barcode(barcode)
//                .build();
//
//        barcode.assignMember(member);
//
//        String[] arr = new String[2];
//
//        arr[0] = "멤버가 바코드를 이미 가지고 있습니다.";
//        arr[1] = member.getBarcode().getBarcodeNumber();
//
//        String body = objectMapper.writeValueAsString(memberDto);
//
//
//        // 내부 구현이 없는 메서드를 호출하는 것이므로
//        // 이 메서드를 호출했을 때 바코드 넘버를 반환하는 행동을 미리 지정한 것
//        given(memberService.issueBarcode(memberDto.getUserId())).willReturn(arr);
//
//        // when
//        ResultActions actions = mockMvc.perform(post("/member/barcode")
//                .contentType(MediaType.APPLICATION_JSON) // 보내는 데이터의 타입 명시
//                .content(body)  // HTTP Body에 데이터 담기
//        );
//
//        // then
//        // Http Status가 200이 되어야 하나?
//        actions.andExpect(status().isCreated())
//                .andExpect(jsonPath("$.message").value("멤버가 바코드를 이미 가지고 있습니다."))
//                .andExpect(jsonPath("$.data").value("99999999"))
//                // return 결과 반환
//                .andReturn();
//
//    }
//
//    @Test
//    @DisplayName("성공_멤버가_포인트를_적립했을때")
//    void savePoint() throws Exception {
//        // given
//        PointRequest pointRequest = PointRequest.builder()
//                .partnerId(1L)
//                .barcodeNumber("99999999")
//                .amount(1000)
//                .build();
//
//        String body = objectMapper.writeValueAsString(pointRequest);
//
//        Barcode barcode = Barcode.builder()
//                .barcodeNumber("99999999")
//                .build();
//
//        Member member = Member.builder()
//                .id(140786709L)
//                .barcode(barcode)
//                .build();
//
//        barcode.assignMember(member);
//
//        PartnerCategory partnerCategory = PartnerCategory.builder()
//                .field(Field.A)
//                .build();
//
//        PartnerStore partnerStore = PartnerStore.builder()
//                .partnerName("A마트")
//                .partnerCategory(partnerCategory)
//                .build();
//
//        LocalDateTime now = LocalDateTime.now();
//        PointHistory pointHistory = PointHistory.builder()
//                .approvedAt(now)
//                .type(EARN)
//                .amount(pointRequest.getAmount())
//                .partnerCategory(partnerCategory)
//                .partnerStore(partnerStore)
//                .barcode(barcode)
//                .build();
//
//        PointResponse pointResponse = PointResponse.builder()
//                        .pointHistory(pointHistory)
//                        .build();
//
//
//        given(memberService.savePoint(pointRequest)).willReturn(pointResponse);
//
//        // when
//        ResultActions actions = mockMvc.perform(post("/member/point/saving")
//                .contentType(MediaType.APPLICATION_JSON) // 보내는 데이터의 타입 명시
//                .content(body)  // HTTP Body에 데이터 담기
//        );
//
//        // then
//
//        actions.andExpect(status().isOk())
//                .andExpect(jsonPath("$.message").value("포인트가 성공적으로 적립되었습니다."))
//                .andExpect(jsonPath("$.data.approvedAt").value(now))
//                .andExpect(jsonPath("$.data.type").value(EARN))
//                .andExpect(jsonPath("$.data.field").value(Field.A))
//                .andExpect(jsonPath("$.data.partnerName").value("A마트"))
//                .andExpect(jsonPath("$.data.amount").value(1000))
//                // return 결과 반환
//                .andReturn();
//
//
//
//    }
//
//    @Test
//    void usePoint() {
//    }
}