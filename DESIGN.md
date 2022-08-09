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
### 2-1. [공통] 멤버십 정보 (MEMBERSHIP)
msp_id*
msp_nm
status (1:사용 9:사용금지)
msp_info
msp_img_url
homepage_url
msp_accum_fg (1:정액 2:정률)
msp_accum_amt
msp_accum_rat
msp_point_expire_fg (1:년 2:월 3:일 0:무제한)
msp_point_expire_val
bigo
insert_date
insert_user
update_date
update_user

### 2-1. [공통] 제휴 멤버십 가입등급 (MEMBERSHIP_GRADE)
msp_id*
account_grade*
status (1:사용 9:사용금지)
insert_date
insert_user
update_date
update_user

### 2-2. [공통] 가맹점 (FRANCHISEE)
franchisee_id*
franchisee_nm
status (1:사용 9:사용금지)
tel_no
zip_cd
addr1
addr2
franchisee_mng_nm
franchisee_mng_tel_no
franchisee_mng_email
bigo
insert_date
insert_user
update_date
update_user

### 2-2. [공통] 멤버십 가맹점 (MEMBERSHIP_FRANCHISEE)
msp_id*
franchisee_id*
status (1:사용 9:사용금지)
insert_date
insert_user
update_date
update_user

### 2-3. [회원] 회원정보 (ACCOUNT)
### 2-4. [회원] 회원 멤버십 (ACCOUNT_MEMBERSHIP)
### 2-5. [회원] 바코드 & QR 발급 관리 (ACCOUNT_BARCODE_QR)
