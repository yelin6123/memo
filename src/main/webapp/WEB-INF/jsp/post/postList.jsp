<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%-- jstl core 라이브러리 추가 --%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


	<div class="d-flex justify-content-center">
		<div class="w-50">
			<h1>글 목록</h1>
			<table class="table"> 
				<thead>
					<tr>
						<th>NO.</th>
						<th>제목</th>
						<th>작성날짜</th>
						<th>수정날짜</th> 
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${postList}" var="post">
					<tr>
						<td>${post.id}</td>
						<td>${post.subject}</td>
						<td>
						<%-- zonedDateTime -> Date -> String --%>
							<fmt:formatDate value="${post.createdAt}" pattern="yyyy년 M월 d일 HH:mm:dd" />
						</td>
						<td>
							<fmt:formatDate value="${post.updatedAt}" pattern="yyyy년 M월 d일 HH:mm:dd" />
						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
			<divj class="d-flex justify-content-end">
				<a href="/post/post-create-view" class="btn btn-warning">글쓰기</a>
			</div>
		</div>
	</div>