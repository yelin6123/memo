package com.memo.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.memo.common.EncryptUtils;
import com.memo.user.Entity.UserEntity;
import com.memo.user.bo.UserBO;

@RequestMapping("/user")
@RestController
public class UserRestController {
	// userController : ResponseBody가 없는 화면만 만들기
	// userRestController : ajax가 요청하는 responseBody로 내려야 하는 모든 컨트롤러 (API가 묶여져 있음!)
	
	@Autowired
	private UserBO userBO;
	
	/**
	 * 로그인 아이디 중복 확인 API
	 * @param loginId
	 * @return
	 */
	// /**하고 엔터치면 위에처럼 된다 
	@RequestMapping("/is-duplcated-id")
	public Map<String, Object> isDuplicatedId(
			@RequestParam("loginId") String loginId) {
		
		//db조회 
		UserEntity user = userBO.getUserEntityByLoginId(loginId);
		
		//응답값 만들고 리턴 => JSON
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		if(user == null) {
			//중복 아님
			result.put("isDuplicated", false);
		} else {
			//중복
			result.put("isDuplicated", true);
		}
		return result;
	}
	
	@PostMapping("/sign-up") //API는 post로 설정했었음
	public Map<String, Object> signUp( //param들 받아오기 / 비밀번호 확인은 안보내려고 id도 없음!!!
			@RequestParam("loginId") String loginId, //id 말고 name과 맵핑하기
			@RequestParam("password") String password,
			@RequestParam("name") String name,
			@RequestParam("email") String email) {
		
		//password 해싱 => 복구화 못함 (암호화 - 복구화가 필요함) //mb5 알고리즘 사용 : 보안이 젤 취약함 왜냐면 슈퍼컴으로 많이 돌려서 해석이 많이 되어있대
		//password 'aaaa' => '74b8733745420d4d33f80c4663dc5e5'
		String hashedPassword = EncryptUtils.md5(password);

		//DB insert
		Integer id = userBO.addUser(loginId, hashedPassword, name, email);
		
		//응답값
		Map<String, Object> result = new HashMap<>();
		if(id == null) {
			result.put("code", 500);
			result.put("errorMessage", "회원가입 하는데 실패했습니다.");
			
		} else {
			result.put("code", 200);
			result.put("result", "성공");
		}
		return result; //json
	}
}
