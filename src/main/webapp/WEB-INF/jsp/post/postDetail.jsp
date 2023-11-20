<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- jstl core 라이브러리 추가 --%>

<div class="d-flex justify-content-center">
	<div class="w-50">
		<h1>글 상세</h1>
		<input type="text" id="subject" class="form-control"
			placeholder="제목을 입력하세요." value="${post.subject}">
		<textarea id="content" class="form-control" rows="10"
			placeholder="내용을 입력하세요.">${post.content}</textarea>

		<%-- 이미지가 있을 때만 이미지 영역 추가 --%>
		<c:if test="${not empty post.imagePath}">
			<div class="my-4">
				<img src="${post.imagePath}" alt="업로드 된 이미지" width="300">
			</div>
		</c:if>

		<div class="d-flex justify-content-end my-4">
			<!-- 나중에 img파일로 변경 -->
			<input type="file" id="file" accept=".jpg, .jpeg, .png, .gif">
			<!--  file 올릴 때 이미지파일만 허용 accept // 선택만 할 수 있을 뿐 막아주는 애는 아님 -->
		</div>
		<div class="d-flex justify-content-between">
			<button type="button" id="deleteBtn" class="btn btn-secondary" data-post-id="${post.id}">삭제</button>
			<div>
				<a href="/post/post-list-view" id="postListBtn" class="btn btn-dark">목록</a>
				<button type="button" id="saveBtn" class="btn btn-warning"
					data-post-id="${post.id}">수정</button>
			</div>
		</div>
	</div>
</div>

<script>
	$(document).ready(function() {
		//글 수정 버튼
		$('#saveBtn').on('click', function() {
			//alert('수정 버튼');
			let postId = $(this).data("post-id");
			let subject = $('#subject').val().trim();
			let content = $('#content').val().trim();
			let fileName = $('#file').val(); //C:\fakepath\0013_###.jpg
			//alert(postId);

			//validation 체크
			if (!subject) {
				alert('제목을 입력해주세요');
				return; //form submit인 경우 return false;로 해
			}
			if (!content) {
				alert('내용을 입력해주세요');
				return; //form submit인 경우 return false;로 해
			}

			//!!!!!!! 이미지로 선택됐는지 확인
			//validation, 파일이 업로드 된 경우에만 확장자체크
			if (fileName) {
				//alert('파일이 있따.')
				//C:\fakepath\0013_###.jpg
				//확장자만 뽑은 후 소문자로 변경한다.
				// ext : 익스텐션(확장자) //자료구조에 나온대...pop ...ㅅㅂ
				let ext = fileName.split(".").pop().toLowerCase();

				//alert(ext); //jpg
				//inArray : 배열안에 ext가 들어있는지 물어보는 함수

				if ($.inArray(ext, [ 'jpg', 'jpeg', 'png', 'gif' ]) == -1) { //-1은 인덱스를 찾을 수 없다는 뜻 
					alert('이미지 파일만 업로드 할 수 있습니다.');
					$('#file').val(""); //파일을 비운다
					return;
				}

			}

			//request param구성
			//이미지를 업로드 할때는 반드시 form태그가 있어야 한다.
			let formData = new FormData(); //비어있는 폼태그를 만들어줌
			formData.append("postId", postId);
			formData.append("subject", subject); //key 는 form태그의 name속성과 같고 Request parameter명이 된다.
			formData.append("content", content);
			//이미지 파일 추가 
			formData.append("file", $('#file')[0].files[0]); //파일의 0번째 인풋 가져와서 files중에 첫번째꺼 가져오기 - 멀티파일은 구글쳐보기

			$.ajax({
				//request
				type : "put" //put도 post의 일종이지만 정확하게 기재
				,
				url : "/post/update",
				data : formData
				//파일 업로드 시 필수 설정
				,
				enctype : "multipart/form-data" //파일 업로드를 위한 필수 설정
				,
				processData : false //파일 업로드를 위한 필수 설정
				,
				contentType : false //파일 업로드를 위한 필수 설정

				//response
				,
				success : function(data) {
					//{"code":200, "result":"성공"}
					if (data.result == "성공") {
						// 저장 후 머물러 있거나 글목록으로 이동하든가 기획자 맴~
						alert("메모가 수정되었습니다.");
						location.reload(true); //새로 고침으로 변경함
					} else {
						alert("data.errorMessage");
					}
				},
				error : function(request, status, error) {
					alert("글을 저장하는데 실패했습니다.");
				}
			}); //ajax
		});

		//글삭제
		$('#deleteBtn').on('click', function() {
			let postId = $(this).data("post-id"); 
			//alert(postId);
			
			// ajax 글 삭제 요청
			$.ajax({
				type : "delete",
				url : "/post/delete",
				data : {
					"postId" : postId
				},
				success : function(data) {
					if (data.code == 200) {
						alert('글이 삭제 되었습니다');
						location.href="/post/post-list-view";
					} else {
						alert(data.errorMessage);
					}
				},
				error : function(request, status, error) {
					alert("삭제하는데 실패했습니다. 관리자에게 문의해주세요.");
				}
			});

		});

	});
</script>






