package com.kh.spring.security.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.validator.MemberValidator;
import com.kh.spring.member.model.vo.Member;
import com.kh.spring.security.model.vo.MemberExt;

import lombok.extern.slf4j.Slf4j;


// <servlet-context에서 component-scan에 의해 bean 객체로 저장되어 web container에 넣어 진다. > 

@Controller
@RequestMapping("/security")
@Slf4j
public class SecurityController {
	
	
	// 필드방식 의존성 주입
	//@Autowired
	private MemberService mService;
	

	//@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	// 생성자 방식 의존성 주입
	// 어노테이션을 쓰지 않아도 가능
	// 단 생성자가 여러개면 @Autowored어노테이션 필요
	public SecurityController(MemberService mService,BCryptPasswordEncoder passwordEncoder) {
		this.mService = mService;
		this.passwordEncoder = passwordEncoder;
	}
	
	
	@RequestMapping("/accessDenied")
	public String accessDenied(Model m) {
		m.addAttribute("errorMsg","접근 불가능한 페이지입니다.");
		return "common/errorPage";
	}
	
	
	// 회원 가입 페이지 이동 
	@GetMapping("/insert")
	public String enroll(
			@ModelAttribute Member member
			/*
			 *  @ModelAttribute
			 *   - 커맨드 객체 바인딩시 사용
			 *   - model 영역에 커맨드 객체를 자동으로 저장, 원래는 model.addAttribute(넣을 객체)를 써야함
			 *   
			 * */
			) {
		//log.info("bcrypto : {}", passwordEncoder);
		return "member/memberEnrollForm";
	}
	
	
	/*
	 * InitBinder
	 *  - 현재 컨트롤러에서 Binding 작업을 수행 할 때 실행되는 객체
	 *  - @ModelAttribute로 지정한 커맨드 객체에 대해 바인딩 작업을 수행한다.
	 *  
	 *  
	 *  처리순서
	 *  1) 클라이언트의 요청 파라미터를 커맨드 객체 필드에 바인딩 시도
	 *  2) 바인딩 과정에서 webDataBinder가 필요한 경우 타입 변환 및 유효성 검사를 수행
	 *  3) 유효성 검사 결과를 저장한다.
	 * 
	 * */
	
	@InitBinder
	public void iniBinder(WebDataBinder binder) {
		
		// 유효성 검사 수행
		binder.addValidators(new MemberValidator());
		
		// 타입 변환수행
		// 문자열 행태의 날짜값을 Date로 변환하기.(birthday 컬럼을 이용)
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
		dateFormat.setLenient(false); //yyMMdd형식이 아닌 경우 에러 발생
		
		binder.registerCustomEditor(Date.class, "birthday", 
				new CustomDateEditor(dateFormat, true));
		// 날짜 형태의 값이 들어오는 경우 자동으로 date 자료형으로 변환해주는 커스텀 에디터
		
	
	}
	
	@PostMapping("/insert")
	public String register(
			@Validated @ModelAttribute Member member,
			BindingResult bindingResult,
			//BindingResult
			// - 유효성 검사결과를 저장하는 객체
			// - forward시 자동으로 jsp에게 전달되며, from태그 내부에
			//   에러 내용을 바인딩 하기 위해 사용된다.
			RedirectAttributes ra
			){
			// 유효성검사 실패시 현재 작업하고 있던 페이지로 포워딩 해준다.
			if(bindingResult.hasErrors()) {
				return "member/memberEnrollForm";
			}
			
			// 유효성 검사 성공시 비밀번호 암호화하여 회원가입 진행
			String encryptedPassword =
					passwordEncoder.encode(member.getUserPwd());
			
			member.setUserPwd(encryptedPassword);
		
			mService.insertMember(member);
			
			return "redirect:/member/login";
	}
	
	
	/*
	 * Authentication
	 *  - principal :  인증에 사용된 사용자 객체 
	 *  - Credentials :  인증에 필요한 비밀번호에 대한 정보를 가진 객체
	 * 	- Authorities :  사용자가 가진 권한을 저장하는 객체
	 * 
	 * */
	
	@GetMapping("/myPage")
	public String myPage(
			Authentication auth,
			Principal principal,
			Model model
			) {
			// 인증된 정보 가져오는 방법
			// 1. ArgumentResolver를 이용한 자동 바인딩
			log.debug("auth={}" , auth);
			log.debug("pincipal={}" , principal);
			
//			model.addAttribute("loginUser",new MemberExt());
			
			
			// 2. SecurityContextHolder를 이
			
			Authentication auth2=  SecurityContextHolder.getContext()
			                        .getAuthentication(); //사용자의 인증정보 가져오기
		
			MemberExt loginUser = (MemberExt)auth2.getPrincipal();
			//MemberExt loginUser = (MemberExt) prin;
			
			model.addAttribute("loginUser",loginUser);
			
			return "member/myPage";
	}
	
	@PostMapping("/update")
	public String update(
			@Validated @ModelAttribute MemberExt loginUser,
			BindingResult bindResult,
			Authentication auth, //로그인한 사용자 인증정보
			RedirectAttributes ra
			) {
		if(bindResult.hasErrors()) {
			return "redirect:/security/myPage";
		}
		
		//비즈니스 로직
		//1. 전달받은 member데이터를 바탕으로 DB수정요청
		int result = mService.updateMember(loginUser);
		
		//2. 내정보 수정이 성공했다면, 변경된 회원정보를 DB에서 다시 조회한 후
		//   새로운 인증정보를 생성하여 SecurityContext에 저장.
		
		if(result>0) {
			
			// (principal, credential, authorities) 을 넣어준다.
			Authentication newAuth= 
					new UsernamePasswordAuthenticationToken(
							loginUser, auth.getCredentials()
							,auth.getAuthorities());
			
			SecurityContextHolder
			          .getContext()
			          .setAuthentication(newAuth);
			ra.addFlashAttribute("alertMsg","내 정보 수정 성공");
			
			return "redirect:/security/myPage";
		}else {
			throw new RuntimeException("회원정보 수정 오류.");
		}
		
	}
	
	
	
	
}
