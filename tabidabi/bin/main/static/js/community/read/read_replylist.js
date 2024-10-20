/**
 * 댓글 리스트 불러오기
 */
$(document).ready(function() {
	// 리플 목록 조회
	replyList();
	
	// 삭제 버튼
	$('#delete-btn').click(function() {
		if(confirm("해당 커뮤니티 게시판을 삭제하시겠습니까?")) {
			const communityId = $('#communityId').val();
			location.href='delete?communityId='+communityId;
		}
	});
	
	// 리플 작성 버튼 클릭시
	$('#reply-submit-button').click(function() {
		const communityId = $('#communityId').val();
		const replyContent = $('#replyContent').val();
		
		if (replyContent == '') {
			alert("댓글에 내용을 적어주세요");
		}
		
		$.ajax({
			url : 'replyWrite',
			type: 'post',
			data: {communityId : communityId, replyContent : replyContent},
			success : function() {
				$('#replyContent').val('');
				replyList();
			},
			error : function(e) {
				console.log("리플 목록 조회 실패",e);
			}
		});
	});
});

function replyList() {
	$.ajax({
		url : 'replyList',
		type : 'get',
		data : {communityId : $('#communityId').val()},
		success(list) {
			console.log(list);
		}
	});
}