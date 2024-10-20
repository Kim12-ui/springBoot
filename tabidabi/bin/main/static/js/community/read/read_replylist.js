/**
 * 댓글 리스트 불러오기
 */
$(document).ready(function() {
	
	// 리플 목록 조회
	replyList();
	
	// 좋아요 버튼 누르기
	$('.like-image').click(function() {
		processLike($(this));
	});
	
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

// 댓글 목록 출력
function replyList() {
	$.ajax({
		url : 'replyList',
		type : 'get',
		data : {communityId : $('#communityId').val()},
		success(response) {
			console.log(response.list);
			console.log(response.loginId);
			
			$('#replyTable').empty();
			$(response.list).each(function(idx, reply) {
				let html = `
					<tr>
						<th>${reply.communityReplyId}</th>
						<td style="width: 200px; text-align: center;">${reply.replyMemberNickname}</td>
						<td style="width: 800px;">${reply.replyContent}</td>
						<td>
				`;

                // 현재 사용자와 댓글 작성자가 같은 경우에만 삭제 버튼 추가
                if (reply.replyMemberId == response.loginId) {
                    html += `
                        <button id="replyDeleteButton" class="replyDeleteButton" data-communityReplyId="${reply.communityReplyId}" data-communityId="${reply.communityId}">
                            삭제
                        </button>
                    `;
                }

				html += `</td></tr>`;
				$('#replyTable').append(html);
			});
			
			$('#replyTable').on('click', '.replyDeleteButton', replyDelete);
		}
	});
}

// 댓글 목록 삭제
function replyDelete() {
	const communityReplyId = $(this).attr('data-communityReplyId');
	const communityId = $(this).attr('data-communityId');
	
	console.log(communityReplyId);
	console.log(communityId);
	
	$.ajax({
		url : 'replyDelete',
		type : 'post',
		data : {communityReplyId : communityReplyId, communityId : communityId},
		success : function() {
			alert('삭제되었습니다.');
			replyList();
		}
	});
}

// 좋아요 처리
function processLike(element) {
	const communityId = element.attr('data-like-data');
	const likeCountElement = $(".like-number");
	console.log(communityId);
	
	// 예시: AJAX 요청으로 좋아요 증가 처리
	$.ajax({
        url: 'like', // 좋아요 처리 API 경로
        type: 'POST',
        data: { communityId : communityId },
        success: function(likeResult) {
			if (likeResult) {
				console.log("좋아요 누르기 성공, DB에 값 저장");
				element.attr("src","../images/community/list/좋아요_클릭후.png");
				// 좋아요 수 증가
				let currentLikeCount = parseInt(likeCountElement.text(), 10); // 현재 좋아요 수 가져오기
				likeCountElement.text(currentLikeCount + 1); // 1 증가시키기
			}
			if (!likeResult) {
				console.log("좋아요 취소 성공, DB에 값 삭제");
				element.attr("src","../images/community/list/like.png");
				// 좋아요 수 감소
				let currentLikeCount = parseInt(likeCountElement.text(), 10); // 현재 좋아요 수 가져오기
				likeCountElement.text(currentLikeCount - 1); // 1 감소시키기
			}
        },
        error: function(error) {
            alert("로그인을 해주세요.");
			console.log("인증되지 않은 사용자입니다.");
        }
    });
}