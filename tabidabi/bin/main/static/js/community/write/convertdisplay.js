/**
 * 코멘트 버튼, 동선 버튼 클릭시 div의 display 변환
 */
window.addEventListener('load', function() {
	// 공통된 요소 가져오기
	const movementBox = document.querySelector('#movement-box');
	const commentBox = document.querySelector('#comment-box');
	
	// 버튼
	const movementButton = document.querySelector('#movement');
	const commentButton = document.querySelector('#comment');
	
	// 초기 상태 설정
	movementBox.style.display = 'block'; // 처음에 동선이 보이도록 설정
	commentBox.style.display = 'none';   // 처음에 코멘트는 숨김
	movementButton.style.backgroundColor = 'gray';
	commentButton.style.backgroundColor = '#ffeb3b';
	
	// 동선 버튼 클릭 시
	document.querySelector('#movement').addEventListener('click', function() {
		// div 영역 변경
	    movementBox.style.display = "block";
	    commentBox.style.display = "none";
		
		// 버튼 색깔 변경
		movementButton.style.backgroundColor = 'gray';
		commentButton.style.backgroundColor = "#ffeb3b";
	});
	
	// 코멘트 버튼 클릭 시
	document.querySelector('#comment').addEventListener('click', function() {
		// div 영역 변경
	    movementBox.style.display = "none";
	    commentBox.style.display = "block";
		
		// 버튼 색깔 변경
		movementButton.style.backgroundColor = '#ffeb3b';
		commentButton.style.backgroundColor = "gray";
	});
});