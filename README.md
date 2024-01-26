

## #1. 요구사항 분석 및 설계 📄

<span style="font-size:60%">

> 유저와 바코드 [1:1]

- 사용자당 하나의 바코드를 가지고 있으므로 1:1 관계
- 바코드로 사용자를 찾을 수 있고, 사용자로 바코드를 찾을 수 있게 하기 위해 양방향으로 설계
    
> 가맹점과 업종 [N:1]
- 하나의 가맹점은 하나의 업종을 가진다
- 하나의 업종은 여러개의 가맹점을 가진다
    
> 업종과 포인트 [1:N]
- 하나의 업종은 여러개의 포인트 정보를 가진다
- 하나의 포인트는 하나의 업종을 가진다

    → 같은 업종의 가맹점들은 적립된 포인트를 공유하여 사용할 수 있기 때문에 업종과 포인트 연관관계 매핑! 

> 바코드와 포인트 [1:N]
- 하나의 바코드는 여러개(3개)의 포인트를 가진다
- 하나의 포인트는 하나의 바코드만 가진다

</span>

---

## #2. Flow Chart ☁️
<span style="font-size:60%">

> **멤버십 바코드 발급 API**

- 사용자 정보 조회 → `사용자 ID (9자리 숫자)` 
- 바코드 생성: 중복 X, 10자리 숫자형 문자열

    → 사용자의 바코드가 있는 경우 기존 바코드를 반환

    → 사용자의 바코드가 없는 경우 바코드 테이블에 저장 
    

> **포인트 적립 API**

- 사용자 정보 조회 → `사용자 바코드` → 사용자의 바코드가 존재하지않으면 등록되지 않은 바코드 에러
  
- `적립금액` 입력
  
- `가맹점 ID` 입력 → 가맹점이 존재하지 않으면 등록되지 않은 가맹점 에러
  
- 가맹점 ID로 가맹점의 업종 찾기
  
- 히스토리 테이블에 모두 저장하기

> **포인트 사용 API**

- 사용자 정보 조회 → `사용자 바코드` → 사용자의 바코드가 존재하지않으면 등록되지 않은 바코드 에러
  
- `사용금액` 입력
  
- `가맹점 ID` 입력 → 가맹점이 존재하지 않으면 등록되지 않은 가맹점 에러
  
- 가맹점 ID로 가맹점의 업종 찾기
  
- 사용자 - 포인트 테이블의 업종별 최종 포인트에서 사용금액만큼 빼기 → 최종 포인트보다 사용금액이 많을 경우 포인트 부족 에러
  
- 히스토리 테이블에 모두 저장
  

> **내역 조회 API**

- 사용자 정보 조회 → `사용자 바코드` → 사용자의 바코드가 존재하지않으면 등록되지 않은 바코드 에러
- 시작긴간과 종료기간을 입력하면, 그 기간에 맞는 포인트 내역 처리

</span>

## #3. API 명세서 ✏️



## #4. Development Design Comment 💡
> **ERD Diagram**과 **API 명세서**을 설계하면서 코멘트받은 내용을 바탕으로 정리하였습니다.

### 초기 ERD Diagram ✔️
![First-ERD-Diagram](https://github.com/weekly-academy/sumi-assignment/assets/81948599/80b7530c-a421-4825-b9e0-abe4e3406afb)

<span style="font-size:60%">

| 코멘트 내용                                                      | 답변 내용                                              |
|-------------------------------------------------------------|----------------------------------------------------|
| 0x00. 설계한 포인트 테이블은 로그성 테이블인가? 업데이트되는 테이블인가?                 | [0x00 : Comment Resolved](#0x00--comment-resolved) |
| 0x01. 유저와 바코드 테이블 사이에 양방향 매핑이 필요할까?                         | [0x01 : Comment Resolved](#0x01--comment-resolved) |
| 0x02. 바코드 테이블의 PK는 Bigint로 변경하자                             | [0x02 : Comment Resolved](#0x02--comment-resolved) |
| 0x03. 지금 설계한 대로라면, 내역을 일일히 다 계산해서 현재 포인트를 계산해야하는데, 문제가 없을까? | [0x03 : Comment Resolved](#0x03--comment-resolved) |

### #0x00 : Comment Resolved

- 업데이트되는 테이블일 경우, 3개의 업종에 따라서 approved_at 칼럼이 매 사용/적립마다 **가장 최신 시점**으로 업데이트
  
    → **문제점 발생**
```
  1. 업데이트된 사용시기만 저장하면, 이전 사용시기 정보를 잃어버림
  2. 사용시기를 업데이트하면, type도 업데이트하고, amount도 업데이트? → 매번 업데이트시 성능 문제
  3. 사용자나 친구, 가족이 동시에 바코드를 사용하는 상황이 있다면?
     - `발급된 멤버쉽 바코드는 가족이나 친구끼리 공유가 가능하다` 라는 요구사항 존재
     - 다른 값으로 업데이트 될 우려가 있음 MySQL Isolation level (팬텀 리드)
```

- `로그성 테이블` 일 경우, 해당 point 테이블이 매 사용/적립마다 새롭게 **쌓임**

  - 내가 설계한 의도는 로그성 테이블

### #0x01 : Comment Resolved

- 양방향 매핑일 필요가 없다고 판단
- **ERD 상에서는 양방향 매핑이 존재해서는 안된다!**

### #0x02 : Comment Resolved
- 이유가 무엇일까? 

    → MySQL 인덱스 원리 (정렬) : Barcode 값이 들어올 때마다 MySQL에서 정렬을 해야함

### #0x03 : Comment Resolved
* 문제가 있다! 유저의 최종 포인트 (현재 포인트)를 계산하기 위해서 두가지의 방법이 제시될 수 있다.
```
2. user에 포인트 컬럼 추가
2. user에 따로 포인트 테이블
```
→ **업종별로 포인트를 관리**하도록 해야하므로 나는 포인트 테이블을 따로 만들고, 포인트 테이블에 업종별 최종 포인트 저장하도록 구성했다.

</span>

### 최종 ERD Diagram ✔️
![final-Erd Diagram](https://github.com/weekly-academy/sumi-assignment/assets/81948599/794e811e-3cef-472c-a0f3-2ba6f08b8755)

<span style="font-size:60%">


<br>


| 코멘트 내용                                                     | 답변 내용                                              |
|------------------------------------------------------------|----------------------------------------------------|
| 0x04. isSuccess 필드가 있는 경우가 있고, 없는 경우가 있는데, 맞춰줄 필요가 있지 않을까? | [0x04 : Comment Resolved](#0x04--comment-resolved) |


### #0x04 : Comment Resolved

`isSuccess 왜 필요한가?`

- 성공했을 경우, HTTP Code만으로는 성공했을 때 어떤 정보들이 생성되었는지 등을 알 수 없음
- 실패했을 경우, HTTP Code외에 추가 오류 정보를 제공하면 클라이언트 단에서 오류를 더 적절하게 취할 수 있음
- 성공 시 응답, 실패 시 응답을 다르게 표현해보자

`RFC 7807 Error Code`
- Error에 대한 표준 존재

`Error Code가 필요한 이유`    
- 클라이언트 단에서는 **어떤 예외인지에 따라서 다르게 처리하는 로직 필요**             
→ **다른 예외들이 동일한 StatusCode를 제공하는 경우**가 많으므로 클라이언트 단이 StatusCode와 더불어 **추가로 Error Message를 참조해서 처리**해야 하는 상황이 생긴 것!          

- 예시           
```
{           
    "type": "/errors/incorrect-user-pass",           
    "title": "Incorrect username or password.",            
    "status": 401,
    "detail": "Authentication failed due to incorrect username or password.",       
    "instance": "/login/log/abc123"        
}      
```         
- **type:** 오류를 분류하는 URI 식별자      
- **title:** 오류에 대한 간략하고 사람이 읽을 수 있는 메시지       
- **status:** HTTP 응답 코드 (Optional)      
- **detail:** 사람이 읽을 수 있는 오류 설명       
- **instance:** 오류의 특정 발생을 식별하는 URI        


- 현업에서는 회사마다 다르게 사용 but, 학생일 때 해보는 것이 좋다.
- [Spring - REST API에서 직접 정의한 Error code를 사용하는 이유!](https://jaehoney.tistory.com/240)
- 나같은 경우, Error Code를 다음과 같이 작성했다.
  - `@Valid` Error가 났을 경우의 예시이다.
```
{
    "code": "INVALID_PARAMETER",
    "message": "Invalid parameter included",
    "errors": [
        {
            "field": "partnerId",
            "message": "널이어서는 안됩니다"
        }
    ]
}
```
</span>


---
## #5. Code Comment 📜

> **코드**를 작성하면서 코멘트 받은 내용을 바탕으로 정리하였습니다.

<span style="font-size:60%">


| 코멘트 내용                                                                                         | 답변 내용                       |
|------------------------------------------------------------------------------------------------|-----------------------------|
| 0x05. Member와 MemberPoint가 Cascade로 처리되어야 한다고 생각하는 이유                                          | [0x00 : Comment Need]()     |
| 0x06. Assert가 어느 시점에 사용되는지, 사용한 이유                                                             | [0x01 : Comment Need]()     |
| 0x07. 테이블명/컬럼명 컨벤션                                                                             | [0x02 : Comment Resolved]() |
| 0x08. 서비스에서 사용자에게 응답될 Http Response 객체를 직접 만드는게 맞을까?                                           | [0x03 : Comment Resolved]() |
| 0x09. Entity 클래스에서 builder를 사용한 이유                                                             | [0x04 : Comment Need]()     |
| 0x10. Service와 ServiceImpl가 같은 패키지에 있으면?                                                       | [0x05 : Comment Need]()     |
| 0x11. PartnerStoreCategory에서 컬럼 field 타입을 char보다 enum을 쓰자                                      | [0x06 : Comment Resolved]() |
| 0x12. @RequiredArgsConstructor, @NoArgsConstructor등 습관적으로 어노테이션을 쓰지말고, 왜 필요한지 생각해보자            | [0x07 : Comment Resolved]() | 
| 0x13. LocalDateTime말고 Instant, OffsetDateTime, ZonedDateTime을 사용해보자                            | [0x08 : Comment Need]()     |                                                                               
| 0x14. 메소드 명이나 변수명에 ~List같이 자료형을 포함시키지 말자                                                       | [0x09 : Comment Resolved]() |
| 0x15. 작성한 코드에서 PartnerStore와 PointHistory의 관계를 봤을 때, 상호명이 변경될 경우 PointHistory도 영향을 받을 수 있지않을까? | [0x10 : Comment Need]()     |                                                                       
| 0x16. 메소드 파라미터와 반환 값으로 Primitive Type이 아니라 Wrapper Type을 사용한 이유                                | [0x11 : Comment Resolved]() |                                                                            
| 0x17. build.gradle.kts로 변경해보자                                                                  | [0x12 : Comment Need]()     |
| 0x18. build.gradle의 버전을 외부에 지정하는 방법을 적용해보자                                                     | [0x13 : Comment Need]()     |
</span>

---

## #6. Learning ✨

> 간단한 과제이지만, 굉장히 배운 내용이 많습니다. 이에 관해 정리한 내용입니다. 


---

## #7. Questions ❓

> 기술적인 부분이나 개발 태도에 대해서 질문한 부분입니다. 

<span style="font-size:60%">

### 1. 
```일단 코드를 대충이라도 짜서 돌아가게 만든 다음 리팩토링하면서 정리하기 vs 코드를 짜면서 시간이 걸리더라도 모르는 내용이 있으면 다 찾아보고 정리 후에 다시 코드짜기```

   저는 보통 후자쪽으로 많이 코딩을 하는데, 그래서 시간이 많이 걸리는 것 같습니다. 어떤 방식으로 짜는게 좋을까요?

### 2. 
제 코드를 보시면, `builder`를 굉장히 많이 사용했습니다. 이렇게 코드를 작성하는 게 좋은 코드인지 잘 모르겠습니다.
예를 들어, `BarcodeRepositoryTest`를 보시면, 다음과 같이 되어있습니다.
```    
Barcode barcode = Barcode.builder()
.barcodeNumber("123456789")
.build();


Member member = Member.builder()
        .barcode(barcode)
        .build();

barcode.assignMember(member);

memberRepository.save(member);
```
이렇게 양방향 설정이 되어있는 경우에 `builder`를 이용할 경우, 바코드 먼저 만들고, 멤버 만들고, 다시 바코드에 멤버를 할당하고, 그 멤버를 저장하는 작업을 합니다. 이런식의 코드는 어떻게 생각하시나요?


### 3.

이 코드에서는 다른 도메인 패키지에 있는 `repository`를 이용하게되면, 하나의 서비스에 다른 도메인의 `repository`도 섞이게 된다고 생각해서 다른 도메인에 있는 `service`를 이용했는데 이렇게 사용하는 것이 적절한지 궁금합니다.

```
 Barcode barcode = barcodeService.validateBarcode(pointRequest.getBarcodeNumber());
        Member member = barcode.getMember();
        //가맹점 검증
        PartnerStore partnerstore = partnerService.validatePartner(pointRequest.getPartnerId());
        PartnerCategory partnerCategory = partnerstore.getPartnerCategory();

        // 업종과 멤버로 멤버포인트 찾기
        MemberPoint memberPoint = memberPointRepository.findByMemberAndPartnerCategory(member, partnerCategory)
                .orElseThrow(() -> new Exception("회원 포인트를 찾을 수 없습니다."));

        memberPoint.updatePoint(memberPoint.getCurrentPoint() + pointRequest.getAmount());

        // 멤버 포인트 저장
        memberPointRepository.save(memberPoint);

        // 포인트 히스토리에 저장
        LocalDateTime localDateTime = LocalDateTime.now();
        PointHistory pointHistory = pointHistoryService.savePointHistory(localDateTime, Type.EARN, memberPoint.getCurrentPoint(), partnerCategory, partnerstore, barcode);


        return new PointResponse(pointHistory);
```

### 4.

`DTO를 어느 계층까지 사용할 것인가?`에 대한 주제로 comment를 정리했는데, 이와 관해서 멘토님의 의견도 궁금합니다!
</span>
