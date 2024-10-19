/**
 * 
 */
function previewImage(event) {
    const input = event.target; // 입력된 파일
    const imagePreview = document.getElementById('imagePreview'); // 미리보기 이미지 요소

    // 선택된 파일이 존재하는지 확인
    if (input.files && input.files[0]) {
        const reader = new FileReader(); // 파일 읽기 객체 생성

        // 파일이 읽기 완료된 후 실행할 함수
        reader.onload = function(e) {
            imagePreview.src = e.target.result; // 읽은 파일의 결과를 이미지 src에 설정
            imagePreview.style.display = 'block'; // 이미지 미리보이도록 설정
        };

        reader.readAsDataURL(input.files[0]); // 파일을 데이터 URL로 읽기
    } else {
        imagePreview.src = ""; // 파일이 없으면 src를 빈 문자열로
        imagePreview.style.display = 'none'; // 이미지 숨김
    }
}
