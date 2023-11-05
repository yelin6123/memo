package com.memo.user.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memo.user.Entity.UserEntity;
import com.memo.user.repository.UserRepository;

@Service
public class UserBO {
	
	@Autowired
	private UserRepository userRepository;
	
	//input: loginId output:UserEntity(null이거나 entity)
	public UserEntity getUserEntityByLoginId(String loginId) {
		return userRepository.findByLoginId(loginId);
	}
	
	//input: 4개 파라미터  output: id(pk) >> jpa 에서 줌
	public Integer addUser(String loginId, String password, String name, String email) { //password : 해싱이든 뭐든 bo는 알빠 없엉
		//UserEntity = save(UserEntity);
		UserEntity userEntity= userRepository.save(
				UserEntity.builder()
				.loginId(loginId)
				.password(password)
				.name(name)
				.email(email)
				.build());
		
		return userEntity == null ? null : userEntity.getId();
	}
	
}
