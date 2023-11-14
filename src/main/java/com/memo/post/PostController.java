package com.memo.post;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.memo.post.bo.PostBO;
import com.memo.post.domain.Post;

@RequestMapping("/post")
@Controller
public class PostController {
	
	@Autowired
	private PostBO postBO;
	
	@GetMapping("/post-list-view")
	public String postListView(Model model, HttpSession session) {
		//로그인 여부 조회 
		Integer userId = (Integer)session.getAttribute("userId"); //로그인이 안된 사람이 있을수 있음
		if (userId == null) {
			//비로그인이면 로그인 페이지로 이동
			return "redirect:/user/sign-in-view";
		} 
		
		//Mybatis
		List<Post> postList = postBO.getPostListByUserId(userId); 	//domain Post만들기
		model.addAttribute("postList", postList);
		model.addAttribute("viewName", "post/postList");
		//viewName으로 내릴꺼고 아직 만들지 않았지만post/postList로 만들어서 내려줄거다..
		return "template/layout";
	}
	
	/**
	 * 글쓰기 화면
	 * @param model
	 * @return
	 */
	@GetMapping("/post-create-view")
	public String postCreateView(Model model) {
		model.addAttribute("viewName", "post/postCreate");
		return "template/layout";
	}
	
	@GetMapping("/post-detail-view")
	public String postDetailView(
			@RequestParam("postId") int postId,
			HttpSession session,
			Model model) {
			
			//권한검사 한걸로 가정함!
			int userId = (int)session.getAttribute("userId");
			
			//DB Select
			// select where postId and userId 
			Post post = postBO.getPostByPostIdAndUserId(postId, userId);
			
			model.addAttribute("post", post);
			model.addAttribute("viewName", "post/postDetail");
		return "template/layout";
	}
	
}
