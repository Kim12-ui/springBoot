/**
 * 회원가입 유효성검사
 * 아이디 중복확인 (ajax)
 */
$(document).ready(function() {
	
	// 회원가입 유효성검사
	$('#joinForm').submit(function() {
		if ($('#password').val().length < 3 || $('#password').val().length > 10) {
			alert('비밀번호는 3 ~ 10자로 입력하세요.');
			return false;
		}
		if ($('#password').val() != $('#password2').val()) {
			alert('비밀번호를 정확하게 입력하세요.');
			return false;
		}
		if ($('#nickname').val().length < 3 || $('#nickname').val().length > 15) {
			alert('닉네임은 3~15자로 입력하세요.');
			return false;
		}
		if ($('#email').val()=='') {
			alert('이메일값을 입력하세요.');
			return false;
		}
		return true;
	});
	
	$('#memberId').keyup(function() {
		let id = $(this).val();
		
		if (id.length < 3 || id.length > 10) {
			$('#msg').css('color','red');
			$('#msg').html('ID는 3~10자로 입력하세요.');
			$('#submitButton').attr('disabled',true);
			return;
		}
		
		$.ajax({
			url : 'idDuplicate',
			method : 'post',
			data : {id : id},
			success : function(result) {
				if (result) {
					$('#msg').css('color' , 'red');
					$('#msg').html('이미 사용중인 ID입니다.');
					$('#submitButton').attr('disabled' , true);
				} else {
					$('#msg').css('color' , 'blue');
					$('#msg').html('사용할 수 있는 ID입니다.');
					$('#submitButton').attr('disabled' , false);
				}
			},
			error : function(e) {
				alert('error' , e);
			}
		});
	});
});