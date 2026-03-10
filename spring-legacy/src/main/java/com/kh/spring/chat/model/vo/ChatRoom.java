package com.kh.spring.chat.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatRoom {
	
	// DB의 그대로 저장할거
	private int chatRoomNo;
	private String title;
	private String status;
	private int userNo;

	// 사용자의 수를 저장할 거
	private String userName;
	private int cnt;
	
	

}
