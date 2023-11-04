package com.memo.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memo.post.mapper.PostMapper;

@Controller
public class TestController {
	
	@Autowired
	PostMapper postMapper;
	
	@ResponseBody
	@GetMapping("/test1")
	public String test1() {
		return "hello world";
	}
	
	@ResponseBody
	@GetMapping("/test2")
	public Map<String, Object> test2() {
		Map<String, Object> map = new HashMap<>();
		map.put("a", 1);
		map.put("b", 2);
		map.put("c", 3);
		return map;
	}
	//처음엔 에러가 뜸 : 왜? DB설정 전까지는 어노테이션 해야함ㅎㅎ
	
	
	@GetMapping("/test3")
	public String test3() {
		return "test/test";
	}
	
	@GetMapping("/test4")
	@ResponseBody
	public List<Map<String, Object>> test4() {
		return postMapper.selectPostList(); //json
	}
	
}
