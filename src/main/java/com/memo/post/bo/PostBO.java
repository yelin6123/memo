package com.memo.post.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memo.post.domain.Post;
import com.memo.post.mapper.PostMapper;

@Service
public class PostBO {
	
	@Autowired
	private PostMapper postMapper;
	
	//input : userId 	output: List<Post>
	public List<Post> getPostListByUserId(int userId) {
		
		return postMapper.selectPostListByUserId(userId);
	}
	
	//input : params output :  x 성공되면 오류 안뜸~
	public void addPost(int userId, String subject, String content) {
		//추후 이미지패스가 생길 거임 (일단 null로)
		String imagePath = null;
		
		//TODO : 이미지가 있으면 업로드
		
		postMapper.insertPost(userId, subject, content, imagePath);
	}

}
