package com.memo.post.domain;

import java.time.ZonedDateTime;

import lombok.Data;
import lombok.ToString;

@ToString //화면 노출 가넝
@Data  //lombok에 있음 자동으로 getter, setter생성 
public class Post {
	private int id;
	private String userId;
	private String subject;
	private String content;
	private String imagePath;
	private ZonedDateTime createdAt;
	private ZonedDateTime updatedAt;
	
	
}
