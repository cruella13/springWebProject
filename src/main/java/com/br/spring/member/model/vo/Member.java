package com.br.spring.member.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * * Lombok(롬복)
 *   필드만 작성하면 알아서 생성자, get/setter, toString 등등의 메소드 만들어주는
 *   
 *   1) 라이브러리 다운 후 적용 (Maven pom.xml)
 *   2) 다운로드된 jar 찾아서 설치
 *   3) IDE 재실행
 *   
 */
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 매개변수생성자
@Setter	//setter메소드
@Getter //getter메소드
@ToString // toString메소드
public class Member {
	
	private String userId;
	private String userPwd;
	private String userName;
	private String email;
	private String gender;
	private String age;
	private String phone;
	private String address;
	private String profileImg;
	private Date enrollDate;
	private Date modifyDate;
	private String status;
	
	// private String uName; => userName
	// 단, 롬복을 쓸 때는 필드명 작성 시 적어도 소문자 두 글자 이상으로 시작할 것

}
