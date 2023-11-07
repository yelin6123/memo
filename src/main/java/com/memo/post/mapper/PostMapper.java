package com.memo.post.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.memo.post.domain.Post;

@Repository
public interface PostMapper {
	public List<Map<String, Object>> selectPostList();
	
	
	public List<Post> selectPostListByUserId(int userId);
	
	public void insertPost(
			//mybatis이기 때문에 내가 보내줘야 함
			//하나의 맵으로 구성을 해야 xml에 보낼 수 있으므로 @param 해줌
			@Param("userId") int userId, 
			@Param("subject")  String subject, 
			@Param("content") String content,
			@Param("imagePath") String imagePath);
}
