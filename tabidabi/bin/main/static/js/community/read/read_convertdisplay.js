/**
 * 화면전환
 */
$(document).ready(function() {
	// 변수 선언
    const movementBox = $('#movement');
    const commentBox = $('#comment');
    
    const movementButton = $('#movement-button'); // jQuery를 사용하여 선택
    const commentButton = $('#comment-button');
    
    // 초기 상태 설정
    movementBox.show(); // show() 메서드로 표시
    commentBox.hide(); // hide() 메서드로 숨김
    movementButton.css('backgroundColor', '#FFFD12'); // CSS 변경
    commentButton.css('backgroundColor', '#dcdcdc');

    // 동선 버튼 클릭 이벤트
    movementButton.click(function() {
        // div 영역 변경
        movementBox.show(); // show() 메서드로 표시
        commentBox.hide(); // hide() 메서드로 숨김

        // 버튼 색깔 변경
        movementButton.css('backgroundColor', '#FFFD12'); // 변경할 색상
        commentButton.css('backgroundColor', '#dcdcdc'); // 변경할 색상
    });
    
    // 댓글 버튼 클릭 이벤트
    commentButton.click(function() {
        // div 영역 변경
        movementBox.hide(); // hide() 메서드로 숨김
        commentBox.show(); // show() 메서드로 표시

        // 버튼 색깔 변경
        movementButton.css('backgroundColor', '#dcdcdc'); // 변경할 색상
        commentButton.css('backgroundColor', '#FFFD12'); // 변경할 색상
    });
});
