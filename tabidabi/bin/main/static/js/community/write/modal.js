/**
 * 
 */
window.addEventListener('DOMContentLoaded', function() {
    // 모달 관련 변수
	const modal = document.getElementById("modal");
	const btn = document.getElementById("openModal");
	const span = document.getElementsByClassName("close")[0];
	
	// 버튼을 클릭하면 모달이 나타남
	btn.onclick = function() {
	    modal.style.display = "block";
	}
	
	// 닫기 버튼을 클릭하면 모달이 사라짐
	span.onclick = function() {
	    modal.style.display = "none";
	}
	
	// 모달 외부를 클릭하면 모달이 사라짐
	window.onclick = function(event) {
	    if (event.target == modal) {
	        modal.style.display = "none";
	    }
	}
});

// 선택된 라디오에 따라 select를 보여줌
function showSelect(radio) {
    // 모든 select를 숨김
    document.querySelectorAll('.planTypeSelect').forEach(function(select) {
        select.style.display = 'none';
    });

    // 선택된 라디오와 연결된 select만 보이게 함
    radio.parentElement.querySelector('.planTypeSelect').style.display = 'block';
}

