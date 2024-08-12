package com.dsa.web4.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuestBookDTO {
	Integer num;             //글번호
	String name;             //작성자
	String password;         //비밀번호
	String message;          //게시글
	LocalDateTime inputdate; //작성시간
}
