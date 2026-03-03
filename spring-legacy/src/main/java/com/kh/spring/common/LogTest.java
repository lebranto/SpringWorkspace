package com.kh.spring.common;

import lombok.extern.slf4j.Slf4j;


// 스프링에서 로그를 사용하기 위한 어노테이션
@Slf4j
public class LogTest {
	
	// 로깅과 관련된 필드가 자동 생성

	public static void main(String[] args) {
		
		
		/*
		 * Logging level
		 * 
		 * 1. fatal
		 *  - 치명적인 에러를 의미
		 * 2. error
		 *  - 요청 처리중 발생하는 오류에서 사용하는 메서드
		 *  - try - catch의 catch에서 사용한다. 
		 * 3. warn
		 *  - 경고성 메세지 작성이 사용하는 메서드.
		 * 4. info
		 *  - 요청 처리중 발생하는 정보성 메세지 출력시 사용하는 메서드
		 * 5. debug
		 *  - 개발중에 필요한 정보성 메세지 출력시 사용
		 * 6. trace 
		 *  - 가장 상세한 로깅 레벨로 디버그보다 많은 내부정보를 출력하고자 할 때 사용
		 * 
		 * */
		
		log.error("error - {}" , "에러메세지");
		//{} 동적으로 바인딩하고자 할 때 사용, 다음 매개변수에 값을 적는다. 최대 2개까지
		log.warn("warn - {}" , "경고성메세지");
		log.info("info - {}" , "정보성메세지"); 
		//log4j의 logger 에서 info로 설정되어 있으면 여기까지 나온다.
		log.debug("debug - {}" , "디버그메세지");
		log.trace("trace - {}" , "트레이스");
		
		
		
	}

}
