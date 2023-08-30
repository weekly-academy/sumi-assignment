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
- **ERD 다이어그램**
  
  ![ERD](https://github.com/weekly-academy/sumi-assignment/assets/81948599/80b7530c-a421-4825-b9e0-abe4e3406afb)


- **아키텍처**
  - 클린 아키텍처: `핵사고날 아키텍처`로 설계
    
    →  이때까지 계층형 아키텍처로만 설계, 소규모 개발이지만 연습을 위해 핵사고날 아키텍처를 연습하기로 결정
    
  - 아키텍처를 설계한다는 것이 어떤 의미? → 다이어그램을 그리는 것인가…
  - 핵사고날 아키텍처에 대해서 좀 더 알아본 후 코드 구현이 필요!
  - [백엔드 아키텍처 정리한 내용](https://imported-event-228.notion.site/3ee90d9a43ae4223acd9a8e4dcd4cb9a?pvs=4) 

## 3. API 명세서
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
