package com.memo.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.common.FileManagerService;
import com.memo.post.domain.Post;
import com.memo.post.mapper.PostMapper;

@Service
public class PostBO {
	
	@Autowired
	private PostMapper postMapper;
	
	@Autowired
	private FileManagerService fileManager;
	
	//input : userId 	output: List<Post>
	public List<Post> getPostListByUserId(int userId) {
		
		return postMapper.selectPostListByUserId(userId);
	}
	
	//input : postID, userId 	output:Post
	public Post getPostByPostIdAndUserId(int postId, int userId) {
		return postMapper.selectPostByPostIdAndUserId(postId, userId) ;
	}
	
	
	
	//input : params output :  x 성공되면 오류 안뜸~
	public void addPost(int userId, String userLoginId, String subject, String content, MultipartFile file) {
		//DB에 직접 넣을 수 없음 -> 우리 서버(컴퓨터)에 이미지를 올려놔야함 
		
		//추후 이미지패스가 생길 거임 (일단 null로)
		String imagePath = null;
		
		//이미지가 있으면 업로드
		if(file != null) {
			imagePath = fileManager.saveFile(userLoginId, file);
		}
		postMapper.insertPost(userId, subject, content, imagePath);
	}

}
