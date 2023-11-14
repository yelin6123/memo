<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%-- jstl core 라이브러리 추가 --%>

	<div class="d-flex justify-content-center">
		<div class="w-50">
			<h1>글 상세</h1>
			<input type="text" id="subject" class="form-control" placeholder="제목을 입력하세요." value="${post.subject}">
			<textarea id="content" class="form-control" rows="10" placeholder="내용을 입력하세요.">${post.content}</textarea>
			
			<%-- 이미지가 있을 때만 이미지 영역 추가 --%>
			<c:if test="${not empty post.imagePath}">
				<div class="my-4">
					<img src="${post.imagePath}" alt="업로드 된 이미지" width="300">
				</div>
			</c:if>
			
			<div class="d-flex justify-content-end my-4">
				<!-- 나중에 img파일로 변경 -->
				<input type="file" id="file" accept=".jpg, .jpeg, .png, .gif"> <!--  file 올릴 때 이미지파일만 허용 accept // 선택만 할 수 있을 뿐 막아주는 애는 아님 -->
			</div> 
			<div class="d-flex justify-content-between">
				<button type="button" id="deleteBtn" class="btn btn-secondary">삭제</button>
				<div>
					<a href="/post/post-list-view" id="postListBtn" class="btn btn-dark">목록</a>
					<button type="button" id="saveBtn" class="btn btn-warning">수정</button>
				</div>
			</div>
		</div>
	</div>