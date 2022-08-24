package com.br.spring.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.br.spring.member.model.service.MemberService;
import com.br.spring.member.model.vo.Member;

//@Component
@Controller // 해당 Controller클래스를 빈으로 등록하는 어노테이션 (빈 스캐닝에 의해서 등록)
public class MemberController {

	@Autowired // DI(Dependency Injection 의존성주입)
	private MemberService mService;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	/*
	@RequestMapping(value="login.me") // HandlerMapping을 대체해주는 어노테이션
	public void loginMember() {
		System.out.println("MemberController클래스의 loginMember메소드 호출");
	}
	*/
	
	
	/*
	 * 파라미터(요청 시 전달값)
	 * 
	 * 1) HttpServletRequest를 이용해서 전달받기 (기존 jsp/servlet때의 방식)
	 * 	  매개변수에 HttpServletRequest를 작성해두면
	 *    스프링컨테이너가 해당 메소드 호출 시 (실행 시) 자동으로 해당 객체를 생성해서 주입
	 */
	
	/*
	@RequestMapping("login.me")
	public String loginMember(HttpServletRequest request) {
		
		String userId = request.getParameter("id");
		String userPwd = request.getParameter("pwd");
		
		System.out.println("userId" + userId);
		System.out.println("userPwd" + userPwd);
		
		return "main";
	}
	
	*/
	
	/*
	 * 2) @RequestParam 어노테이션을 이용하는 방법
	 * 	  request.getParameter("키") : 밸류 의 역할을 대신해주는 어노테이션
	 * 
	 * 	  dafaultValue 속성으로 기본값 설정 가능
	 */
	
	/*
	@RequestMapping("login.me")
	public String loginMember( @RequestParam(value="id", defaultValue="aaa") String userId,
							   @RequestParam("pwd")String userPwd) {
		
		System.out.println("userId : "  +  userId);
		System.out.println("userPwd : " + userPwd);
		return "main";
	}
	*/
	
	/*
	 * 3) @RequestParam 어노테이션을 생략하는 방법
	 *    => 해당 키값으로 넘어온 값을 곧바로 변수에 담고자 할 때
	 *    
	 *   ** 단, 매개변수명을 name값(요청 시 전달값의 키값)과 동일하게 세팅해둬야 자동으로 값이 주입
	 * 
	 */
	
	/*
	@RequestMapping("login.me")
	public String loginMember(String id, String pwd) {
		
		//System.out.println("userId : " + id);
		//System.out.println("userPwd : " + pwd);
		
		Member m = new Member();
		m.setUserId(id);
		m.setUserPwd(pwd);
		
		return "main";
	}
	*/
	
	/*
	 * 4) 커맨드 객체 방식
	 *    다수로 넘어온 값을 곧바로 vo객체의 필드에 담고자 할 때의 방식
	 *    
	 *    매개변수로 요청 시 전달값을 담고자 하는 vo클래스 타입을 세팅 후
	 *    전달값의 키값(name)을 vo클래스에 담고자하는 필드명으로 작성
	 *    
	 *    내부적으로 스프링이 해당 객체를 "기본생성자로 생성" 후 "setter메소드" 찾아서
	 *    요청 시 전달값을 해당 필드에 대입해주는 원리
	 */
	
	/*
	@RequestMapping("login.me")
	public String loginMember(Member m) {
	
		//System.out.println("userId : " + m.getUserId());
		//System.out.println("userPwd : " + m.getUserPwd());
		
		//MemberService mService = new MemberServiceImpl();
		// 개발자가 직접 new로 객체 생성하게 되면 "결합도가 높아진다고 함"
		// * 결합도가 높을 경우 발생할 수 있는 문제
		// 1. 생성하고 있는 클래스가 수정될 경우 => 현재 클래스에도 일일히 찾아서 수정해야되는 일 발생
		// 2. 매 번 새로이 객체가 생성되어 주소값이 달라짐. (메모리를 빈번하게 사용하게 됨) (한 번 생성된 객체를 재사용하는 싱글톤패턴 권장)
		// => 이런 문제를 개선한게 Spring임.
		
		Member loginUser = mService.loginMember(m);
		
		if(loginUser == null) { // 로그인 실패 => 에러문구를 requestScope에 담고 에러페이지 포워딩
			System.out.println("로그인 실패");
		}else { // 로그인 성공 => loginUser를 sessionScope에 담고 메인페이지 url 재요청
			System.out.println("로그인 성공");
			System.out.println(loginUser);
		}
		
		return "main";
	}
	
	*/
	
	/*
	 * * 요청처리 후에 응답페이지 포워딩/url재요청, 데이터를 scope에 담는 방법
	 * 
	 * 1) 스프링에서 제공하는 Model객체를 사용하는 방법
	 *    포워딩을 응답뷰에 필요한 데이터를 맵형식(key-value)으로 담을 수 있는 영역
	 *    Model 객체는 requestScope이다.
	 *    단, setAttribute가 아닌 addAttribute메소드 이용
	 */
	
	/*
	@RequestMapping("login.me")
	public String loginMember(Member m, Model model, HttpSession session) {
		
		Member loginUser = mService.loginMember(m);
		
		if(loginUser == null) { // 로그인 실패
			// 에러문구를 requestScope에 담기
			model.addAttribute("errorMsg","로그인 실패");
			// 에러페이지로 포워딩
			return "common/errorPage";
			
			// servlet-context.xml에 등록되어있는 ViewResolver에 의해서
			// 반환되는 문자열 앞에는 /WEB-INF/views/ 붙고
			//			    뒤에는 .jsp가 붙어서 해당 응답페이지를 찾아서 포워딩 됨
			
		}else { // 로그인 성공
			// loginUser를 sessionScope에 담기
			session.setAttribute("loginUser", loginUser);
			// 메인페이지에 url재요청
			return "redirect:/";
			
			// 리턴되는 문자열 앞에 redirect: 을 붙이면 url재요청
		}
	}
	*/
	
	/*
	 * 2) 스프링에서 제공하는 ModelAndView 객체를 이용하는 방법
	 * 	  Model : requestScope에 데이터를 key-value 세트로 담을 수 있는 공간
	 *    View : 응답할 뷰에 대한 정보를 담을 수 있는 공간
	 *    
	 *    단, 해당 ModelAndView 객체를 반환해야 됨
	 */
	@RequestMapping("login.me")
	public ModelAndView loginMember(Member m, HttpSession session, ModelAndView mv) {
		
		/*
		Member loginUser = mService.loginMember(m);
		
		if(loginUser == null) {
			mv.addObject("errorMsg", "로그인실패");
			mv.setViewName("common/errorPage");
		}else {
			session.setAttribute("loginUser", loginUser);
			mv.setViewName("redirect:/");
		}
		
		return mv;
		
		*/
		
		// --------- 암호화 과정 후 ----------
		
		// Member m userId필드 : 사용자가 입력한 아이디
		// Member m userPwd필드 : 사용자가 입력한 비밀번호 (평문)
		
		Member loginUser = mService.loginMember(m);
		// loginUser : 오로지 아이디만을 가지고 조회된 회원
		// loginUser userPwd필드 : db에 기록되어있는 비번 (암호문)
		
		if(loginUser != null && bcryptPasswordEncoder.matches(m.getUserPwd(), loginUser.getUserPwd())) { // 로그인 성공
			session.setAttribute("loginUser", loginUser);
			mv.setViewName("redirect:/");
		}else { // 로그인실패
			mv.addObject("errorMsg", "로그인 실패");
			mv.setViewName("common/errorPage");
		}
		
		return mv;
		
	}
	
	@RequestMapping("logout.me")
	public String logoutMember(HttpSession session) {
		// 세션만료
		session.invalidate();
		// 메인페이지 url 재요청
		return "redirect:/";
	}
	
	@RequestMapping("enrollForm.me")
	public String enrollForm() {
		// WEB-INF/views/member/memberEnrollForm.jsp
		return "member/memberEnrollForm";
	}
	
	@RequestMapping("insert.me")
	public String insertMember(Member m, Model model, HttpSession session) {
		//System.out.println(m);
		/*
		 * 1. 나이를 입력하지 않을 경우 400에러 (bad request)
		 * 	  "" 빈문자열을 int형 필드에 담을 수 없어서 (파싱불가)
		 * 	  
		 * 	  => Member 클래스의 age필드를 int형 --> String형
		 * 
		 * 2. 비밀번호가 사용자가 입력한 있는 그대로의 평문 
		 *    => 암호화 적용시켜서 암호문으로 변경 후에 db에 insert
		 *    
		 *    		암호화
		 *    평문 ----------> 암호문
		 *    평문 <---------- 암호문
		 *    		복호화
		 *    
		 *    1) 단방향암호화 : 암호화만 가능
		 *    2) 양방향암호화 : 암호화, 복호화 둘 다 가능 (단, 암호를 풀 수 있는 키가 있어야 복호화 가능)
		 *    
		 *    옛날 비밀번호 관련 암호화방식 : 양방향암호화
		 *    		=> 비밀번호 찾기 시 실제 기존의 비밀번호를 알아내서 알려줬었음
		 *    		=> 복호화가 가능하기 때문에 보안 취약 => 보안정책 바꿨음 (단방향 암호화방식)
		 *    
		 *    그 후에 비밀번호 관련 암호화방식 : 단방향암호화 (SHA방식)
		 *    		=> 절대 평문을 알아낼 수 없다는 장점이 있지만
		 *    		=> 문제점 : 매 번 똑같은 평문일 경우 똑같은 암호문으로 만들어짐
		 *    		ex) 1111 => xSDF@ssdf1233452XCDSDF@
		 *    			1111 => xSDF@ssdf1233452XCDSDF@
		 *    		=> 샘플데이터가 많이 수집되면 암호문을 가지고 평문을 유추할 수 있는 수준
		 *    
		 *    위의 문제를 해결하기 위해 나온게 "솔팅(salting)기법"
		 *    
		 *    > 솔팅기법
		 *    	  <평문> + salt값(랜덤값) ---------> <암호문>
		 *    	  1111  + 45612		   ---------> @23sdbf3424~@
		 *    	  1111  + 78923		   ---------> @23ghjghj23!
		 *    
		 *    스프링에서 제공하는 BCrypt 방식 : 솔팅기법이 적용된 암호화방식
		 *    
		 *    1) pom.xml에 스프링 시큐리티 관련 라이브러리
		 *    2) BcryptPasswordEncoder 클래스를 빈으로 등록
		 *    3) web.xml에 spring-security.xml 파일도 pre-loading 되게끔 등록
		 */
		
		//System.out.println("평문 : " + m.getUserPwd());
		
		// encode : 암호화해주는 메소드
		String encPwd = bcryptPasswordEncoder.encode(m.getUserPwd());
		//System.out.println("암호문 : " + encPwd);
		
		m.setUserPwd(encPwd);
		
		int result = mService.insertMember(m);
		
		if(result > 0) { // 회원가입 성공
			// sessionScope영역에 alertMsg라는 키값을 "성공적으로 회원가입 되었습니다." 담고 
			session.setAttribute("alertMsg", "성공적으로 회원가입 되었습니다.");
			// 메인페이지 url 재요청
			return "redirect:/";
		}else { // 회원가입 실패
			// requestScope영역에 에러문구 담고
			model.addAttribute("errorMsg", "회원가입 실패");
			// 에러페이지 포워딩
			return "common/errorPage";
			
		}
	}
	
	@RequestMapping("myPage.me")
	public String myPage() {
		return "member/myPage";
		
	}
	
	@RequestMapping("update.me")
	public String updateMember(Member m, Model model, HttpSession session) {
		
		int result = mService.updateMember(m);
		
		if(result > 0) { // 수정 성공
			
			// db로부터 갱신된 회원 정보를 다시 조회해와서 session에 담기
			//Member updateMember = mService.loginMember(m);
			session.setAttribute("loginUser", mService.loginMember(m));
			session.setAttribute("alertMsg", "성공적으로 회원정보 변경되었습니다.");
			
			// 마이페이지 url재요청
			return "redirect:myPage.me";
			
		}else { // 수정 실패
			model.addAttribute("errorMsg", "회원정보 변경 실패");
			return "common/errorPage";
		}
		
	}
	
	@RequestMapping("delete.me")
	public String deleteMember(String userPwd, HttpSession session, Model model) {
		
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		// userPwd매개변수 : 사용자가 입력한 비번(평문)
		// loginUser userPwd필드 : 진짜비번 (암호문)
		
		if(bcryptPasswordEncoder.matches(userPwd, loginUser.getUserPwd())) {
			// 비번맞음 => 탈퇴처리
			int result = mService.deleteMember(loginUser.getUserId());
			
			if(result > 0) { // 탈퇴처리성공
				session.removeAttribute("loginUser");
				session.setAttribute("alertMsg", "성공적으로 탈퇴되었습니다. 그동안 이용해주셔서 감사합니다.");
				return "redirect:/";
				
			}else {
				model.addAttribute("errorMsg", "회원 탈퇴 실패");
				return "common/errorPage";
			}
			
			
		}else {
			// 비번틀림 => 비밀번호가 틀림을 알리고 마이페이지 보여지게
			session.setAttribute("alertMsg", "비밀번호를 잘못 입력했습니다. 확인해주세요.");
			return "redirect:myPage.me";
		}
		
		
	}
}


