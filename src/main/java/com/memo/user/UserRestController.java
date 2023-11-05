package com.memo.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.memo.user.Entity.UserEntity;
import com.memo.user.bo.UserBO;

@RequestMapping("/user")
@RestController
public class UserRestController {
	// userController : ResponseBody가 없는 화면만 만들기
	// userRestController : ajax가 요청하는 responseBody로 내려야 하는 모든 컨트롤러 (API가 묶여져 있음!)
	
	@Autowired
	private UserBO userBO;
	
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
	
	
}
