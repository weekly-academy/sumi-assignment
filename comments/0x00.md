> **Confirm**

- isSuccess 필드가 있는 경우가 있고, 없는 경우가 있다!

  → 맞춰줄 필요가 있다!

  `isSuccess 왜 필요한가?`

    - 성공했을 경우, HTTP Code만으로는 성공했을 때 어떤 정보들이 생성되었는지 등을 알 수 없음
    - 실패했을 경우, HTTP Code외에 추가 오류 정보를 제공하면 클라이언트 단에서 오류를 더 적절하게 취할 수 있음
    - 성공 시 응답, 실패 시 응답을 다르게 표현해보자



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
		isSucess: boolean;
		message: string;
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
		message: string;
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
		message: string;
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
		isSucess: boolean;
		message: string;
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



