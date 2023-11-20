package com.memo.post.bo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.common.FileManagerService;
import com.memo.post.domain.Post;
import com.memo.post.mapper.PostMapper;

@Service
public class PostBO {
	//로깅
	private Logger logger = LoggerFactory.getLogger(this.getClass()); //this => BO
	
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
	
	//update 
	//input : 파라미터들   	output:x
	public void updatePost(int userId, String userLoginId, int postId, String subject, String content, MultipartFile file) {
		//이미지에 대한 처리가 필요함 
		//기존 이미지 메모리 삭제 후 수정한 이미지를 업로드
		//수정이미지를 업로드 중 에러가 뜬 경우 1) 전체적으로 오류화면을보여주기, 2) 이미지 외 다른 내용은 넘겨주고 이미지만 옛날 그대로 머물러 있게 -> 기획자 맘대로
		
		//1)기존 글을 가져와본다. (이미지 교체 시 삭제를 위해 / 업데이트 대상이 있는지  validation을 하기 위해)
		Post post = postMapper.selectPostByPostIdAndUserId(postId, userId);
		if (post == null) { //null체크 꼭 해보기! 없는데 왜 안돼?라는 상황이 있대 - 로깅:기록해둔다
			//System.out.println(); ->console창에 로깅하기 위해 서버 개발자는 절대 이거 쓰면 안돼!!!!!!
			logger.error("[글 수정] post is null. postId:{}, userId:{}", postId, userId); //와일드카드문법
			return;
		}
		
		//2) 기존 글에 이미지 파일이 있는게 확인이 된다면
		// 2-1) 새이미지를 업로드 한다.
		// 2-2) 새 이미지 업로드 성공 시 기존 이미지 제거 (기존 이미지가 있을 때)
		String imagePath = null;
		if (file != null) {
			//업로드
		 	imagePath = fileManager.saveFile(userLoginId, file);
			
			
			//업로드 성공 시 기존 이미지 세거(있으면)
		 	if(imagePath != null && post.getImagePath() != null) {
		 		//업로드가 성공을 했고, 기존 이미지가 존재한다면 => 삭제
		 		//이미지 제거 로직
		 		fileManager.deleteFile(post.getImagePath());
		 	}
			
		}
		
		//3) DB글 업데이트 
		postMapper.updatePostByPostIdUserId(postId, userId, subject, content, imagePath);
		
	}
	
	//글 삭제하기
	//input : postId, userId 		output:x
	public void deleteByPostIdUserId(int postId, int userId) {
		//기존 글 가져옴 (이미지가 있으면 삭제해야하기 때문)
		Post post = postMapper.selectPostByPostIdAndUserId(postId, userId);
		if(post == null) {
			logger.info("[글 삭제] post is null. postId:{}, userId:{}", postId, userId);
			return;
		}

		//기존이미지가 존재하면 -> 삭제
		if (post.getImagePath() != null) {
			fileManager.deleteFile(post.getImagePath());
		}
		
		//DB에서 삭제
		postMapper.deleteByPostIdUserId(postId, userId);
	}
	
	
}
