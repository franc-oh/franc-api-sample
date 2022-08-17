/** 멤버십 서비스에 필요한 기초 테이블 스크립트 **/


/* 1.회원 정보 */
CREATE TABLE ACCOUNT (
    ACCOUNT_ID BIGINT NOT NULL AUTO_INCREMENT,
    ACCOUNT_NM VARCHAR(50) NOT NULL,
    STATUS CHAR DEFAULT '1',
    EMAIL VARCHAR(40) NOT NULL,
    BIRTH VARCHAR(8) NOT NULL,
    HPHONE VARCHAR(11) NOT NULL,
    GRADE VARCHAR(10) DEFAULT 'USER',
    INSERT_DATE TIMESTAMP NOT NULL,
    INSERT_USER BIGINT NOT NULL,
    UPDATE_DATE TIMESTAMP,
    UPDATE_USER BIGINT,

    PRIMARY KEY (ACCOUNT_ID)
);

/* 2.가맹점 정보 */
CREATE TABLE FRANCHISEE (
    FRANCHISEE_ID BIGINT NOT NULL AUTO_INCREMENT,
    FRANCHISEE_NM VARCHAR(200) NOT NULL,
    STATUS CHAR DEFAULT '1',
    TEL_NO VARCHAR(20),
    ZIP_CD VARCHAR(5),
    ADDR1 VARCHAR(200),
    ADDR2 VARCHAR(200),
    FRANCHISEE_MNG_NM VARCHAR(50),
    FRANCHISEE_MNG_HPHONE VARCHAR(11),
    FRANCHISEE_MNG_EMAIL VARCHAR(40),
    BIGO VARCHAR(2000),
    INSERT_DATE TIMESTAMP NOT NULL,
    INSERT_USER BIGINT NOT NULL,
    UPDATE_DATE TIMESTAMP,
    UPDATE_USER BIGINT,

    PRIMARY KEY (FRANCHISEE_ID)
);

/* 3.멤버십 정보 */
CREATE TABLE MEMBERSHIP (
    MSP_ID BIGINT NOT NULL AUTO_INCREMENT,
    MSP_NM VARCHAR(200) NOT NULL,
    STATUS CHAR DEFAULT '1',
    MSP_INFO VARCHAR(200),
    MSP_IMG_URL VARCHAR(300),
    HOMEPAGE_URL VARCHAR(100),
    MSP_ACCUM_FG CHAR DEFAULT '2',
    MSP_ACCUM_AMT INT DEFAULT 0,
    MSP_ACCUM_RAT INT DEFAULT 10,
    MSP_ACCUM_MIN_AMT INT DEFAULT 0,
    MSP_POINT_EXPIRE_DAYS INT DEFAULT 0,
    BIGO VARCHAR(2000),
    INSERT_DATE TIMESTAMP NOT NULL,
    INSERT_USER BIGINT NOT NULL,
    UPDATE_DATE TIMESTAMP,
    UPDATE_USER BIGINT,

    PRIMARY KEY (MSP_ID)
);

/* 4.멤버십별 가입가능한 회원등급 */
CREATE TABLE MEMBERSHIP_GRADE (
    MSP_ID BIGINT NOT NULL,
    ACCOUNT_GRADE VARCHAR(10) NOT NULL,
    STATUS CHAR DEFAULT '1',
    INSERT_DATE TIMESTAMP NOT NULL,
    INSERT_USER BIGINT NOT NULL,
    UPDATE_DATE TIMESTAMP,
    UPDATE_USER BIGINT
);
ALTER TABLE MEMBERSHIP_GRADE ADD PRIMARY KEY(MSP_ID, ACCOUNT_GRADE);

/* 5.멤버십별 가맹점 */
CREATE TABLE MEMBERSHIP_FRANCHISEE (
    MSP_ID BIGINT NOT NULL,
    FRANCHISEE_ID BIGINT NOT NULL,
    STATUS CHAR DEFAULT '1',
    INSERT_DATE TIMESTAMP NOT NULL,
    INSERT_USER BIGINT NOT NULL,
    UPDATE_DATE TIMESTAMP,
    UPDATE_USER BIGINT
);
ALTER TABLE MEMBERSHIP_FRANCHISEE ADD PRIMARY KEY(MSP_ID, FRANCHISEE_ID);

/* 6.회원별 멤버십 가입정보 */
CREATE TABLE MY_MEMBERSHIP (
    ACCOUNT_ID BIGINT NOT NULL,
    MSP_ID BIGINT NOT NULL,
    STATUS CHAR DEFAULT '1',
    TOTAL_AMT INT NOT NULL,
    USABLE_POINT INT NOT NULL,
    JOIN_DATE TIMESTAMP NOT NULL,
    WITHDRAWAL_DATE TIMESTAMP,
    INSERT_DATE TIMESTAMP NOT NULL,
    INSERT_USER BIGINT NOT NULL,
    UPDATE_DATE TIMESTAMP,
    UPDATE_USER BIGINT
);
ALTER TABLE MY_MEMBERSHIP ADD PRIMARY KEY(ACCOUNT_ID, MSP_ID);

/* 7.나의 멤버십_적립내역 */
CREATE TABLE MY_MEMBERSHIP_ACCUM (
    POINT_ACCUM_SEQ VARCHAR(20) NOT NULL,
    MSP_ID BIGINT NOT NULL,
    ACCOUNT_ID BIGINT NOT NULL,
    ACCUM_FG CHAR DEFAULT 'A',
    TRADE_POINT INT NOT NULL,
    EXPIRE_YMD VARCHAR(8) NOT NULL
);
ALTER TABLE MY_MEMBERSHIP_ACCUM ADD PRIMARY KEY(POINT_ACCUM_SEQ, MSP_ID, ACCOUNT_ID);

/* 8.나의 멤버십_적립 바코드 관리 */
CREATE TABLE MY_MEMBERSHIP_BARCODE (
    BAR_CD VARCHAR(50) NOT NULL,
    CREATE_DATE TIMESTAMP NOT NULL,
    ACCOUNT_ID BIGINT NOT NULL,
    MSP_ID BIGINT NOT NULL,
    FRANCHISEE_ID BIGINT NOT NULL,
    ACCUM_FG CHAR DEFAULT 'A',
    TRADE_AMT INT NOT NULL,
    USE_POINT INT NOT NULL,
    EXPIRE_DATE TIMESTAMP NOT NULL,
    USE_DATE TIMESTAMP NOT NULL
);
ALTER TABLE MY_MEMBERSHIP_BARCODE ADD PRIMARY KEY(BAR_CD, CREATE_DATE);