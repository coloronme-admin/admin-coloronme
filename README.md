# 🎨 admin-coloronme
### 퍼스널 컬러 진단자용 서비스 
진단자가 진단한 퍼스널 컬러 진단 내역 및 고객들을 관리하고, 진단 시 필요한 퍼스널 컬러 타입과 컬러들을 직접 커스터 마이징할 수 있는 서비스를 제공합니다.

<br>

## 주 기능
- 진단 회원 관리
- 퍼스널 컬러 짇단 내역 관리
- 퍼스널 컬러 커스터 마이징
- 진단 내역 기반 통계 자료 제공
- 로그인 및 마이페이지
- QR 코드로 진단 회원 추가

<br>


## 프로젝트 환경
- JDK 17
- SpringBoot 3.1.2
- JPA
- PostgreSQL
- Jar
- Gradle

<br>

## 🚚 현재는 아직 개발 단계라 클라우드 타입 서비스로 배포중에 있습니다.
[컬러온미 관리자 서비스 접속하기](https://coloronme-coloronme-admin.vercel.app/)

```text
테스트 계정
ID : peter@naver.com
PWD : 1234
```

모바일 이용을 기반으로 개발하여 모바일로 접속하는 것을 권장드립니다.

<br>

## API 명세
### [Swagger](https://port-0-admin-coloronme-staging-am952nlsmt6rh8.sel5.cloudtype.app/swagger-ui/index.html)
```
GET  /swagger-ui/index.html
```
### Health Check
```
GET /actuator/health
```

<br>

## 화면 구상 일부
- 로그린 페이지
<img width="203" alt="image" src="https://github.com/user-attachments/assets/feee3eb5-3e79-45f0-8571-ca143e8099aa">


- 고객 진단 정보 페이지

<img width="203" alt="image" src="https://github.com/user-attachments/assets/9020314d-2871-4910-88d9-0eac862e4c72">


<br>
<br>

- 퍼스널 컬러 타입 커스터 마이징 페이지

<img width="406" alt="image" src="https://github.com/user-attachments/assets/bd4fe3a2-64d8-47de-9943-fd29c5de559b">

<br>
<br>

- 차트 페이지

<img width="406" alt="image" src="https://github.com/user-attachments/assets/2c649170-2cd2-4585-ad14-e42f3fa2fab1">


