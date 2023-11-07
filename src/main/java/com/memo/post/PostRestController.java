package com.memo.post;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.memo.post.bo.PostBO;

@RequestMapping("/post")
@RestController
public class PostRestController {
	
	@Autowired
	private PostBO postBO;
	
	
	@PostMapping("/create")
	public Map<String, Object> create( //userId는 안받아도 되나요..?
			@RequestParam("subject") String subject,
			@RequestParam("content") String content,
			@RequestParam(value = "file", required = false) MultipartFile file, //객체로 받아오기 위해 Multi~ 클래스로 가져옴~
			//용량 초과로 505에러 -> 구글에서 'spring 파일 업로드 용량 제한' 검색 후 application.yml에 추가 
			
			
			//userId를 빼내기 위해 session씀  
			HttpSession session) {
		
		//session에 들어있는 유저 id 꺼낸다.
		//만약 명칭이 기억이 안난다면 UserRestController에 가서 set한 내용을 확인!
		int userId = (int)session.getAttribute("userId");
		String userLoginId = (String)session.getAttribute("userLoginId");
		
 		//DB insert BO
		postBO.addPost(userId, userLoginId, subject, content, file);
		//error가 안났다면 성공하고 아래로 내려감! 돌려주는 값 없음(에러만 확인)
		
		//응답값
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		result.put("result", "성공");
		
		return result;
		
	}
}
