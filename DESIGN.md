<div align="center">
    <h1>멤버십 서비스 설계</h1>
</div>

## 1.기능 명세
### 1-1. 멤버십 목록 조회
`'멤버십' 메뉴화면에 접근했을 때 멤버십들이 노출되는 상황을 가정`
- 서비스에서 제휴하고 있는 멤버십이 전부 조회된다.
- 멤버십에 대한 모든 정보를 클라이언트로 넘겨준다.
- 뿐만 아니라 가입여부에 따라 화면에 다르게 나타내야 하는 항목이 있기 때문에, 멤버십에 대한 가입정보도 같이 넘겨줘야 한다.  
  - `ex) 가입=현재포인트 및 가입일 노출, 미가입='미가입'문구노출, 가입불가=흑백으로 비활성처리`
- 상황에 따라 가입한 멤버십, 미가입 멤버십, 가입가능 멤버십만 필터링해서 노출할 수 있어야 한다.

### 1-2. 멤버십 상세 조회
`멤버십 목록에서 특정 멤버십을 클릭했을 때를 가정`
- 특정 멤버십에 대한 상세정보를 조회한다.

### 1-3. 멤버십 가입
- 회원이 특정 멤버십에 가입한다.
- 일반 멤버십에는 모두 가입이 가능하지만, 회원등급에 따라 제한되는 멤버십도 있다.
- 중복가입X, 탈퇴당일 재가입X

### 1-4. 멤버십 적립 바코드 생성
`'멤버십 적립' 화면 접근 시 적립 바코드가 생성되는 상황을 가정`
- 멤버십 가입여부 체크, 가입된 멤버십일 경우 클라이언트로 바코드 정보를 넘겨준다. 
- 바코드에 유효시간 정보 포함

### 1-5. 멤버십 적립
`회원은 적립 바코드를 제시, 가맹점에서 바코드를 스캔 했을때`
- 바코드 번호는 Header로 넘어온다.
- 바코드 번호를 통해 해당 회원의 멤버십에 포인트를 적립
- 결제가 이루어진 가맹점과 바코드의 가맹점이 일치하는 지 체크
- 바코드의 유효기간이 지났으면 바코드를 재생성해야 함
- 적립과 관련된 정보는 제휴 멤버십 정보에 별도로 관리된다.

### 1-6. 멤버십 탈퇴
- 회원이 특정 멤버십을 탈퇴한다.
- 특정 flag 값으로 탈퇴를 구분한다.


## 2. 테이블 설계
### 2-1. 회원 정보 (ACCOUNT)
````
ACCOUNT_ID [*]                    - 회원ID
ACCOUNT_NM                        - 회원이름
STATUS                            - 상태 (1:사용 9:사용금지 0:탈퇴)
EMAIL                             - 이메일
BIRTH                             - 생년월일 (yyyymmdd)
HPHONE                            - 휴대폰번호
GRADE                             - 회원등급 (VIP/USER/ADMIN)
INSERT_DATE                       - 등록일자 (yyyymmddhhmmss)
INSERT_USER                       - 등록자
UPDATE_DATE                       - 수정일자 (yyyymmddhhmmss)
UPDATE_USER                       - 수정자
````

### 2-2. 멤버십 정보 (MEMBERSHIP)
````
MSP_ID [*]                        - 멤버십ID
MSP_NM                            - 멤버십이름
STATUS                            - 상태 (1:사용 9:사용금지)
MSP_INFO                          - 멤버십 안내문구
MSP_IMG_URL                       - 멤버십 이미지 경로
HOMEPAGE_URL                      - 제휴사 홈페이지
MSP_ACCUM_FG                      - 멤버십 적립구분 (1:정액 2:정률)    
MSP_ACCUM_AMT                     - 멤버십 적립액
MSP_ACCUM_RAT                     - 멤버십 적립률
MSP_ACCUM_MIN_AMT                 - 적립가능한 최소금액 (0:제한없음)
MSP_POINT_EXPIRE_DAYS             - 적립포인트 유효기간 (일단위, 0:무제한)
BIGO                              - 비고
INSERT_DATE                       - 등록일자 (yyyymmddhhmmss)
INSERT_USER                       - 등록자
UPDATE_DATE                       - 수정일자 (yyyymmddhhmmss)
UPDATE_USER                       - 수정자
````

### 2-3. 가맹점 정보 (FRANCHISEE)
````
FRANCHISEE_ID [*]                 - 가맹점ID
FRANCHISEE_NM                     - 가맹점명
STATUS                            - 상태 (1:사용 9:사용금지)
TEL_NO                            - 대표전화번호
ZIP_CD                            - 우편번호
ADDR1                             - 주소
ADDR2                             - 상세주소
FRANCHISEE_MNG_NM                 - 가맹점주_이름
FRANCHISEE_MNG_HPHONE             - 가맹점주_휴대폰번호
FRANCHISEE_MNG_EMAIL              - 가맹점주_이메일주소
BIGO                              - 비고
INSERT_DATE                       - 등록일자 (yyyymmddhhmmss)
INSERT_USER                       - 등록자
UPDATE_DATE                       - 수정일자 (yyyymmddhhmmss)
UPDATE_USER                       - 수정자
````

### 2-4. 멤버십별 가입가능한 회원등급 (MEMBERSHIP_GRADE)
- 멤버십별로 가입가능한 회원등급에 대한 로우 데이터를 관리
- 등급별로 멤버십 가입을 제한하기 위함 (ex. VIP등급 타겟 멤버십)
````
MSP_ID [*]                        - 멤버십ID
ACCOUNT_GRADE [*]                 - 회원등급
STATUS                            - 상태 (1:사용 9:사용금지)
INSERT_DATE                       - 등록일자 (yyyymmddhhmmss)
INSERT_USER                       - 등록자
UPDATE_DATE                       - 수정일자 (yyyymmddhhmmss)
UPDATE_USER                       - 수정자
````



### 2-5. 멤버십별 가맹점 (MEMBERSHIP_FRANCHISEE)
- 멤버십별로 계약된 가맹점 데이터를 관리
- ex. 적립 요청 시 해당 멤버십에 가입된 가맹점인지 체크 
````
MSP_ID [*]                        - 멤버십ID
FRANCHISEE_ID [*]                 - 가맹점ID
STATUS                            - 상태 (1:사용 9:사용금지)
INSERT_DATE                       - 등록일자 (yyyymmddhhmmss)
INSERT_USER                       - 등록자
UPDATE_DATE                       - 수정일자 (yyyymmddhhmmss)
UPDATE_USER                       - 수정자
````



### 2-6. 회원별 멤버십 가입정보 (MY_MEMBERSHIP)
- 회원별로 어떤 멤버십에 가입됐는지에 대한 데이터를 관리
````
ACCOUNT_ID [*]                    - 회원ID
MSP_ID [*]                        - 멤버십ID
STATUS                            - 상태 (1:사용 9:사용금지 0:탈퇴)
TOTAL_AMT                         - 총 결제금액
USABLE_POINT                      - 사용가능한 포인트
JOIN_DATE                         - 가입일자 (yyyymmddhhmmss)
WITHDRAWAL_DATE                   - 탈퇴일자 (yyyymmddhhmmss)
INSERT_DATE                       - 등록일자 (yyyymmddhhmmss)
INSERT_USER                       - 등록자
UPDATE_DATE                       - 수정일자 (yyyymmddhhmmss)
UPDATE_USER                       - 수정자
````

### 2-7. 회원별 멤버십 적립내역 (MY_MEMBERSHIP_ACCUM)
- 회원별로 어떤 멤버십으로 얼마가 적립되고, 사용됐는지에 대한 이력을 관리
````
POINT_ACCUM_SEQ [*]               - SEQ
MSP_ID                            - 멤버십ID
ACCOUNT_ID                        - 회원ID
ACCUM_FG                          - 적립구분 (A:적립 U:사용)
TRADE_POINT                       - 거래포인트
EXPIRE_YMD                        - 만료일 (yyyymmdd)
````

### 2-8. 멤버십 적립 바코드 관리 (MY_MEMBERSHIP_BARCODE)
- 멤버십 적립요청(사용요청) 시 바코드 데이터가 만들어진다.
- 바코드를 가맹점에서 스캔 시 가맹점정보 및 거래금액 등에 대한 정보가 갱신된다.
````
BAR_CD [*]                        - 바코드
CREATE_DATE [*]                   - 생성일자 (yyyymmddhhmmss)
ACCOUNT_ID                        - 회원ID
MSP_ID                            - 멤버십ID
FRANCHISEE_ID                     - 가맹점ID
ACCUM_FG                          - 적립구분 (A:적립 U:사용)
TRADE_AMT                         - 거래금액
USE_POINT                         - 사용포인트
EXPIRE_DATE                       - 바코드 만료일자 (yyyymmddhhmmss)
USE_DATE                          - 바코드 사용일자 (yyyymmddhhmmss)
````
