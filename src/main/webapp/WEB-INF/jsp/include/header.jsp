<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%-- jstl core 라이브러리 추가 --%>

<div class="h-100 d-flex align-items-center justify-content-between">
	<%-- logo --%>
	<div>
		<h1 class="font-weight-bold">MEMO 게시판</h1>
	</div>
	
	<%-- 로그인 정보 --%>
	<div>
		<c:if test="${not empty userName}"> <%-- 만약 로그인이 안되어 있다면 아무것도 안보여지고..! userName이 존재할떄만 보여진다!--%>
			<span>${userName}님 안녕하세요</span> <%-- userName key값으로 쓰기 /el문법  --%> 
			<a href="/user/sign-out">로그아웃</a>
		</c:if>
	</div>
</div>


