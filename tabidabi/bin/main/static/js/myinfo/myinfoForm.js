

document.addEventListener("DOMContentLoaded", function() {
    const image = document.getElementById('preview');
    const noImageText = document.getElementById('no-image');

    // 페이지가 로드될 때 미리보기 이미지 설정
    if (image.src && image.src !== "about:blank") {
        image.style.display = 'block'; // 이미지가 있다면 보이기
        noImageText.style.display = 'none'; // '이미지가 없습니다' 숨기기
    } else {
        image.style.display = 'none'; // 이미지가 없다면 숨김
        noImageText.style.display = 'block'; // '이미지가 없습니다' 문구 보이기
    }

    // 파일 업로드 시 프로필 이미지 미리보기 함수
    window.loadFile = function(event) {
    if (event.target.files && event.target.files.length > 0) {
        const file = event.target.files[0];
        const reader = new FileReader();

        reader.onload = function(e) {
            image.src = e.target.result; // 파일 로드 후 src 설정
            image.style.display = 'block'; // 이미지 보이기
            noImageText.style.display = 'none'; // '이미지가 없습니다' 숨기기
        };

        reader.readAsDataURL(file); // 파일을 Data URL로 읽기
    } else {
        image.style.display = 'none'; // 파일이 없으면 숨김
        noImageText.style.display = 'block'; // '이미지가 없습니다' 문구 보이기
    }
};



    // 폼 제출 이벤트
    const form = document.querySelector('form');
    if (form) {
        form.onsubmit = function(event) {
            if (!checkPassword()) {
                event.preventDefault(); // 비밀번호 확인 실패 시 폼 제출 방지
            }
        };
    }

    // 폼 초기화 함수
    window.resetForm = function() {
        document.getElementById('file-input').value = '';
        if (image) image.style.display = 'none';
        if (noImageText) noImageText.style.display = 'block'; // 초기화 시 '이미지가 없습니다' 문구 보이기

        document.getElementById('password').value = '';
        document.getElementById('check-password').value = '';
        document.getElementById('email').value = '';
        document.getElementById('nickname').value = '';
    };
});
