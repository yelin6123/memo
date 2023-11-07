package com.memo.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component //로직용 spring bean
public class FileManagerService {
	//Autowired를 하면 돼
	
	//실제 업로드가 된 이미지가 저장된 경로(서버)
	public static final String FILE_UPLOAD_PATH = "/Users/jiyelin/Documents/Java/06_spring_project/memo/workspace/images/";

	//input: userLoginId, file(이미지)		output: web imagePath
		public String saveFile(String loginId, MultipartFile file){
			//이 메소드가 하는 기능, 역할
			//폴더 생성 (directroy)
			//경로 예: aaaa_178945646/sun.png
			String directoryName = loginId + "_" + System.currentTimeMillis();
			String filePath = FILE_UPLOAD_PATH + directoryName; //Users/jiyelin/Documents/Java/06_spring_project/memo/workspace/images/aaaa_178945646/sun.png
			
			File directory  = new File(filePath);		
			if (directory.mkdir() == false) {
				//폴더 생성 실패 시 이미지 경로 null로 리턴 
				return null;
			}
			
			//파일 업로드 : byte 단위로 업로드
			try {
				byte[] bytes = file.getBytes();
				//★★★★★★ 한글 이름 이미지는 올릴 수 없으므로 나중에 영문자로 바꿔서 올리기
				Path path = Paths.get(filePath + "/" + file.getOriginalFilename()); //디렉토리 경로 + 사용자가 올린 파일명
				Files.write(path, bytes); //파일 업로드
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null; //이미지 업로드 실패시 null 리턴
			} 
			
			
			
			//파일 업로드가 성공했으면 웹 이미지 url path를 리턴
			//주소는 이렇게 될 것이다 (예언)
			// /images/aaaa_123482543/sun.png
			return "/images/" + directoryName + "/" + file.getOriginalFilename() ;
			
		}
}
 