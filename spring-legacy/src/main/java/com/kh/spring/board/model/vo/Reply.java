package com.kh.spring.board.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor  //매개변수 없는 생성자
public class Reply {
	
	private int replyNo;
	private String replyContent;
	private String refBno;
	private String replyWriter;
	private String createDate;
	private String status;

}
