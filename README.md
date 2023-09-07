# sumi-assignment
> 시작: 2023.08.27(일)

## Default Branch Rule

- default branch: develop
- 어떤 작업을 하려고 하면, develop 브랜치에서 새로운 브랜치를 생성해서 작업 수행(브랜치 이름은 자유롭게 해도 됨)
  - 'branch 전략' 검색 시 다양한 네이밍 룰이 나옴. 이런 것들 참고해도 좋음.
- 각 작업은 새롭게 생성한 브랜치에서 수행하고, develop 브랜치로 PR 올려주시면 됨.
- 참고하면 좋은 책: https://www.aladin.co.kr/shop/wproduct.aspx?ItemId=316493887

## Step 1.

> 2023.08.30(수) 저녁 리뷰
- 설계: ERD, 아키텍처, 요구사항 분석 등등
- README.md에 작성한 후 PR올리기

# Sumi's Section
## 1. 요구사항 분석 및 설계

- 유저와 바코드 [1:1]
    - 사용자당 하나의 바코드를 가지고 있으므로 1:1 관계
    - 바코드로 사용자를 찾을 수 있고, 사용자로 바코드를 찾을 수 있게 하기 위해 양방향으로 설계
    
- 가맹점과 업종 [N:1]
    - 하나의 가맹점은 하나의 업종을 가진다
    - 하나의 업종은 여러개의 가맹점을 가진다
    
- 업종과 포인트 [1:N]
    - 하나의 업종은 여러개의 포인트 정보를 가진다
    - 하나의 포인트는 하나의 업종을 가진다
    
    → 같은 업종의 가맹점들은 적립된 포인트를 공유하여 사용할 수 있기 때문에 업종과 포인트 연관관계 매핑! 
    
- 바코드와 포인트 [1:N]
    - 하나의 바코드는 여러개(3개)의 포인트를 가진다
    - 하나의 포인트는 하나의 바코드만 가진다

## 2. ERD 및 아키텍처 설계

### **ERD 다이어그램**
  
  ![ERD](https://github.com/weekly-academy/sumi-assignment/assets/81948599/80b7530c-a421-4825-b9e0-abe4e3406afb)

- 설계한 Point Table은 로그성 테이블인가? 업데이트되는 테이블인가?
    - 업데이트되는 테이블일 경우, 3개의 업종에 따라서 approved_at 칼럼이 매 사용/적립마다 **가장 최신 시점**으로 업데이트
        
        → **문제점 발생** 
        
        1. 업데이트된 사용시기만 저장하면, **이전 사용시기 정보를 잃어버림**
        2. 사용시기를 업데이트하면 , 구분도 업데이트하고, 금액도 업데이트? → 매번 업데이트시 성능 문제
        3. `‘발급된 멤버쉽 바코드는 가족이나 친구끼리 공유가 가능하다’`
            - 처음에는 왜 이런 요구사항이 있는지 파악하지 못함
                
                → Point Table에서 사용자나 친구, 가족이 **동시에 바코드를 사용**하는 상황이 있지 않을까?
                
                - 다른 값으로 업데이트 될 우려가 있음
    - `로그성 테이블` 일 경우, 해당 point 테이블이 매 사용/적립마다 새롭게 **쌓임**
        - 내가 설계한 의도는 로그성 테이블
        
        → **문제점 발생** 
        
        `유저의 최종 포인트 (현재 포인트)를 계산하기 위해서 어떻게 해야할까?` 
        
        - 지금 설계한 대로라면, 내역을 일일히 다 계산해서 현재 포인트를 구해야함
        - 해결 방법
            1. **user에 포인트 컬럼 추가**
            2. **user에 따로 포인트 테이블**
<br>

> **User - Barcode Relationship**
> 
- 양방향 매핑일 필요가 없다고 판단
- **ERD 상에서는 양방향 매핑이 존재해서는 안된다!**

<br>

> **Barcode Table**
> 
- Barcode Table의 `PK는 bigint로 변경`하자
    
    → MySQL 인덱스 원리 (정렬)
    
    : Barcode 값이 들어올 때마다 MySQL에서 정렬을 해야함 
    

<br>

### 수정된 ERD 다이어그램

![수정된 ERD](https://github.com/weekly-academy/sumi-assignment/assets/81948599/6c3641a0-791c-4e9a-bcb6-9fb24108a973)
<br>
<br>
# 아키텍처

### 아키텍처에 대한 고찰

- 계층형(레이어드) 아키텍처 → `데이터베이스 주도 설계 유도`
  - 의존성의 방향이 아래 계층을 향하고, 최하단에는 데이터베이스 관련 레이어가 존재하기 때문에 데이터베이스의 구조를 먼저 생각하고, 이를 토대로 도메인 로직을 구현
        
    → **도메인 로직을 구성하고, 로직을 제대로 이해했는지 먼저 확인하지 않고**, 영속성 계층과 웹 계층을 만들기 때문에 비즈니스 관점에서는 옳지 않은 방법!
        
  - 영속성 계층과 도메인 계층 사이에 **강한 결합**을 생기게 하여 변경에 용이하지 않게 된다.

- 클린 아키텍처 → `의존성 역전` 을 하는 이유?
    - 레이어드 아키텍처의 경우, 영속성 계층(저수준 모듈)에 대한 도메인 계층(고수준 모듈)의 의존성 때문에 영속성 계층을 **변경**할 때마다 잠재적으로 도메인 계층도 **변경**해야한다.
    - **인터페이스를 통해 의존성 역전**을 하면 저수준 모듈이 변경되더라도 고수준 모듈은 변경되지 않도록 함으로써 유지보수를 조금 더 편하게 할 수 있다.
<br>

### 아키텍처 설계 단계

[2장: 의존성 역전하기](https://www.weekly.ac/4b15abfe-1373-4658-aa02-72d606911c42)

[Why DDD, Clean Architecture and Hexagonal ?](https://dataportal.kr/74)

- Domain Layer: Entity와 UserCases로 세분화
- Infrastructure Layer: 모든 I/O components(Database, Web APIs)등이 존재하는 영역

(많이 찾아봤는데 아직 어려움 .. )

- 추후에 `핵사고날 아키텍처`로 리팩토링

[백엔드 아키텍처](https://www.notion.so/3ee90d9a43ae4223acd9a8e4dcd4cb9a?pvs=21)

## 3. API 명세서
> **Confirm**

- isSuccess 필드가 있는 경우가 있고, 없는 경우가 있다!
    
    → 맞춰줄 필요가 있다! 
    
    `isSuccess 왜 필요한가?`
    
    - 성공했을 경우, HTTP Code만으로는 성공했을 때 어떤 정보들이 생성되었는지 등을 알 수 없음
    - 실패했을 경우, HTTP Code외에 추가 오류 정보를 제공하면 클라이언트 단에서 오류를 더 적절하게 취할 수 있음
    - 성공 시 응답, 실패 시 응답을 다르게 표현해보자

### RFC 7807 Error Code

- Error에 대한 표준 존재
- `Error Code가 필요한 이유`
    - 클라이언트 단에서는 **어떤 예외인지에 따라서 다르게 처리하는 로직 필요**
        
        → **다른 예외들이 동일한 StatusCode를 제공하는 경우**가 많으므로 클라이언트 단이 StatusCode와 더불어 **추가로 Error Message를 참조해서 처리**해야 하는 상황이 생긴 것! 
        
    - **예시**
        
        ```json
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

[Spring - REST API에서 직접 정의한 Error code를 사용하는 이유!](https://jaehoney.tistory.com/240)

## /user/barcode
  - `POST`
  - 유저 바코드 발급
  ### 요청 예시
  
  - 주소: /user/barcode
  - header
      
      ```
        해당사항 없음.
      ```
      
  - body
      
     ```
      {
         userId: long;	
      }
     ```
      
  
  ### 응답 예시
  
  ```
    // 성공 200
    
    {
        barcode: string;
    }
                      
    // 실패 400
```



## /point/saving
  - `POST`
  - 포인트 적립
  ### 요청 예시
  
  - 주소: /point/saving
  - header
      
      ```
        해당사항 없음.
      ```
      
  - body
      
     ```
      {
         pointRequest: PointRequest;
      }
     ```
      
  
  ### 응답 예시
  
  ```
    // 성공 200
    
    {
        isSucess: boolean;
        data: PointResponse;
    }
                      
    // 실패 400
  ```


## /point/using
  - `POST`
  - 포인트 사용
  ### 요청 예시
  
  - 주소: /point/using
  - header
      
      ```
        해당사항 없음.
      ```
      
  - body
      
     ```
      {
         pointRequest: PointRequest;
      }
     ```
      
  
  ### 응답 예시
  
  ```
    // 성공 200
    
    {
        isSucess: boolean;
        data: PointResponse;
    }
                      
    // 실패 400
  ```

## /point/history?startTime=localDate&endTime=localDate&barcode=string
  - `GET`
  - 포인트 내역 조회
  ### 요청 예시
  
  - 주소: /point/history?startTime=2023-08-23 10:00:00&endTime=2023-08-23 23:00:00&barcode="9837214566"
  - header
      
      ```
        {
          해당사항 없음.
        }
      ```
      
  - body
      
     ```
      {
         해당사항 없음.
      }
     ```
      
  
  ### 응답 예시
  
  ```
    // 성공 200
    
    {
        history : 
	      [
             PointResponse
	      ]
    }
                      
    // 실패 400
  ```

## DTO, Enum 목록
### Type
  - 포인트 유형 Enum
    ```
    {
        USE,
        EARN
    }
    ```

### PointResponse
  - 포인트 적립, 사용, 조회 response에 대한 DTO
     ```
     {
         approvedAt: localDateTime;
         type: Type;
         category: char;
         partnerName: string;
         amount: int;
     }
     ```
     
      
### PointRequest
  - 포인트 적립, 사용, 조회 request에 대한 DTO
      ```
      {
          partnerId: long;
          barcodeId: long;
          amount: int;
      }
      ```

## 4. 질문 및 찾아봐야할 사항 

- 식별관계, 비식별관계의 차이 
    
    → 의미적으로 차이는 알겠는데 비식별관계랑 비교했을 때 식별관계는 어떤 경우에 쓰는 것이 더 좋은지? 
    
- string vs String , long vs Long … 차이 

- API 명세서, ERD 컨펌받기
