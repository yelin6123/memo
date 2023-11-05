<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
<div class="m-5 p-5">

	<div class="d-flex justify-content-center">
	
	<div class="sign-up-box">
		<h1 class="mb-4">회원가입</h1>
		<form id="signUpForm" method="post" action="/user/sign-up">
			<table class="sign-up-table table table-bordered">
				<tr>
					<th>* 아이디(4자 이상)<br></th>
					<td>
						<%-- 인풋박스 옆에 중복확인을 붙이기 위해 div를 하나 더 만들고 d-flex --%>
						<div class="d-flex">
							<input type="text" id="loginId" name="loginId" class="form-control col-9" placeholder="아이디를 입력하세요.">
							<button type="button" id="loginIdCheckBtn" class="btn btn-success">중복확인</button><br>
						</div>
						
						<%-- 아이디 체크 결과 --%>
						<%-- d-none 클래스: display none (보이지 않게) --%>
						<div id="idCheckLength" class="small text-danger d-none">ID를 4자 이상 입력해주세요.</div>
						<div id="idCheckDuplicated" class="small text-danger d-none">이미 사용중인 ID입니다.</div>
						<div id="idCheckOk" class="small text-success d-none">사용 가능한 ID 입니다.</div>
					</td>
				</tr>
				<tr>
					<th>* 비밀번호</th>
					<td><input type="password" id="password" name="password" class="form-control" placeholder="비밀번호를 입력하세요."></td>
				</tr>
				<tr>
					<th>* 비밀번호 확인</th>
					<td><input type="password" id="confirmPassword" class="form-control" placeholder="비밀번호를 입력하세요."></td>
				</tr>
				<tr>
					<th>* 이름</th>
					<td><input type="text" id="name" name="name" class="form-control" placeholder="이름을 입력하세요."></td>
				</tr>
				<tr>
					<th>* 이메일</th>
					<td><input type="text" id="email" name="email" class="form-control" placeholder="이메일 주소를 입력하세요."></td>
				</tr>
			</table>
			<br>
		
			<button type="submit" id="signUpBtn" class="btn btn-primary float-right">회원가입</button>
		</form>
	</div>
</div>

<script type="text/javascript">
$(document).ready(function() {
	//중복버튼 클릭
	$('#loginIdCheckBtn').on('click', function() {
		//alert("중복확인");
		//경고 문구 초기화 (d-none)
		$('#idCheckLength').addClass('d-none');
		$('#idCheckDuplicated').addClass('d-none');
		$('#idCheckOk').addClass('d-none');
		
		let loginId = $("#loginId").val().trim();
		if(loginId.length < 4 ) {
			$('#idCheckLength').removeClass('d-none');
			return; //밑으로 내려가지 못하게 함
		}
		
		//AJAX - 중복확인
		$.ajax({
			//request
			//type:get 생략가능
			url:"/user/is-duplcated-id"
			, data:{"loginId":loginId}
			
			//response
			, success:function(data){
				//{"code":200, "isDuplicated":true} 중복은 true
				if(data.isDuplicated){ //중복
					$('#idCheckDuplicated').removeClass('d-none');
				} else { //중복 아님 => 사용 가능
					$('#idCheckOk').removeClass('d-none');
				}
			}
			
			, error:function(request, satatus, error) {
				alert('중복 확인에 실패했습니다.');
			}
		});
	});
	
	//회원가입 submit
	$('#signUpForm').on('submit', function(e) { //e : submit기능을 안쓸것이다! 
		e.preventDefault(); //submit기능 막음 -> ajax로 할거임
		//alert('aaaa');
		
		//validation -> input들 다 확인 (id로 가져오기) 
		let loginId = $('#loginId').val().trim();
		let password = $('#password').val();
		let confirmPassword = $('#confirmPassword').val();
		let name = $('#name').val().trim();
		let email = $('#email').val().trim();
		
		if(loginId == '') {
			alert('아이디를 입력하세요.');
			return false;
		}
		if(!password || !confirmPassword) {
			alert('비밀번호를 입력하세요.');
			return false;
		}
		
		if(password != confirmPassword) {
			alert('비밀번호가 일치하지 않습니다.');
			return false;
		}
		
		if(!name) {
			alert('이름을 입력하세요');
			return false;
		}
		if(!email) {
			alert('이메일을 입력하세요');
			return false;
		}
		
		// 중복확인 후 사용 가능한지 확인 => idCheckOk가 d-none이 없는 상태 -> d-none이 있을 때 alert띄움
		if($('#idCheckOk').hasClass('d-none')) {
			alert('아이디 중복확인을 다시 해주세요.');
			return false;
		}
		
		//회원가입 버튼 누른 후 서버로 보내는 방법 2가지
		//1) form 태그의 submit 동작을 막아놈 -> submit을 자바스크립트로 동작시킴
		//$(this)[0].submit(); //화면 이동이 반드시된다. (jsp, redirect) // $(this) => form태그 [0] : 첫번째 애, 
		
		//2) ajax - 응답값 : JSON
		//name속성이 반드시 있어야 함
		//let url = "/user/sign-up"
		let url = $(this).attr('action');
		//alert(url);
		
		let params = $(this).serialize(); //form태그에 있는 name속성-값으로 파라미터 구성 : 직렬화
		console.log(params);
		
		//ajax로도 할 수 있지만 post메소드로도 할 수 있음 (password등 보이면 안되기에 Post로 함 )
		$.post(url, params) //request //url어디로 보낼건지, params받은것
		.done(function(data) { //callback함수- response
			//{"code":200, "result":"성공"}  //기획서를 보면 로그인 화면으로 이동 한다고 되어있음 ~
			if(data.code == 200) { //성
				alert('가입을 환영합니다. 로그인을 해주세요!');
				location.href =	"/user/sign-in-view"; //로그인 화면으로 이동
			} else { 
				//로직 실패 - 에러는 아님
				alert(data.errorMessage);
			}
		}); 
	});
	
});
</script>

</div>