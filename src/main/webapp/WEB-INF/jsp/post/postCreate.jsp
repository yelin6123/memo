<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

	<div class="d-flex justify-content-center">
		<div class="w-50">
			<h1>글 쓰기</h1>
			<input type="text" id="subject" class="form-control" placeholder="제목을 입력하세요.">
			<textarea id="content" class="form-control" rows="10" placeholder="내용을 입력하세요."></textarea>
			<div class="d-flex justify-content-end my-4">
				<!-- 나중에 img파일로 변경 -->
				<input type="file" id="file" accept=".jpg, .jpeg, .png, .gif"> <!--  file 올릴 때 이미지파일만 허용 accept // 선택만 할 수 있을 뿐 막아주는 애는 아님 -->
			</div> 
			<div class="d-flex justify-content-between">
				<button type="button" id="postListBtn" class="btn btn-dark">목록</button>
				<div>
					<button type="button" id="clearBtn" class="btn btn-secondary">모두 지우기</button>
					<button type="button" id="saveBtn" class="btn btn-warning">저장</button>
				</div>
			</div>
		</div>
	</div>
	
	<script>
	$(document).ready(function() {
		//목록 버튼 클릭 => 글 목록 화면 이동
		$('#postListBtn').on('click', function() {
			//alert('목록화면 클릭')
			location.href = "/post/post-list-view";
		});
		
		//모두지우기 버튼 
		$('#clearBtn').on('click', function() {
			//alert('클릭');
			$('#subject').val("");
			$('#content').val("");
		});
		
		//글 저장 버튼
		$('#saveBtn').on('click', function() {
			//alert('아 졸려');
			let subject = $('#subject').val().trim();
			let content = $('#content').val().trim();
			let fileName = $('#file').val(); //C:\fakepath\0013_###.jpg
			
			//alert(file);
			
			//validation 체크
			if(!subject) {
				alert('제목을 입력해주세요');
				return; //form submit인 경우 return false;로 해
			}	
			if(!content) {
				alert('내용을 입력해주세요');
				return; //form submit인 경우 return false;로 해
			}	
			
			//!!!!!!! 이미지로 선택됐는지 확인
			//validation, 파일이 업로드 된 경우에만 확장자체크
			if(fileName) {
				//alert('파일이 있따.')
				//C:\fakepath\0013_###.jpg
				//확장자만 뽑은 후 소문자로 변경한다.
				// ext : 익스텐션(확장자) //자료구조에 나온대...pop ...ㅅㅂ
				let ext = fileName.split(".").pop().toLowerCase();
				
				//alert(ext); //jpg
				//inArray : 배열안에 ext가 들어있는지 물어보는 함수
	
				if($.inArray(ext, ['jpg', 'jpeg', 'png', 'gif']) == -1) { //-1은 인덱스를 찾을 수 없다는 뜻 
					alert('이미지 파일만 업로드 할 수 있습니다.');
					$('#file').val(""); //파일을 비운다
					return;
				} 
				
			}
			
			
			//return; // !!!!!!! 이미지 업로드 validation하기 위한 임시 코드 
			
			//request param구성
			//이미지를 업로드 할때는 반드시 form태그가 있어야 한다.
			let formData = new FormData(); //비어있는 폼태그를 만들어줌
			formData.append("subject", subject); //key 는 form태그의 name속성과 같고 Request parameter명이 된다.
			formData.append("content", content);
			//이미지 파일 추가 
			formData.append("file", $('#file')[0].files[0]); //파일의 0번째 인풋 가져와서 files중에 첫번째꺼 가져오기 - 멀티파일은 구글쳐보기
			
			
			$.ajax({
				//request
				type:"post"
				, url:"/post/create"
				, data:formData
				//파일 업로드 시 필수 설정
				, enctype:"multipart/form-data" 
				, processData:false
				, contentType:false
				
				//response
				, success:function(data){
					//{"code":200, "result":"성공"}
					if(data.result == "성공"){
						// 저장 후 머물러 있거나 글목록으로 이동하든가 기획자 맴~
						alert("메모가 저장되었습니다.");
						location.href = "/post/post-list-view";
					} else {
						alert("data.errorMessage");
					}
				}
				, error:function(request, status, error) {
					alert("글을 저장하는데 실패했습니다.");
				}
			});
		});
	});
	
	</script>