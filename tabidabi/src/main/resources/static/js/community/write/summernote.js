/**
 * summernote 관련 js
 */

$(document).ready(function() {
    $('#summernote').summernote({
        height: 500,                  // 에디터 높이
        minHeight: 500,               // 최소 높이
        maxHeight: 500,               // 최대 높이
        focus: true,                  // 에디터 로딩 후 포커스
        lang: "ko-KR",                // 한글 설정
        placeholder: '타비다비 게시글입니다',  // placeholder 설정
        toolbar: [
            // [groupName, [list of button]]
            ['fontname', ['fontname']],
            ['fontsize', ['fontsize']],
            ['style', ['bold', 'italic', 'underline','strikethrough', 'clear']],
            ['color', ['forecolor','color']],
            ['para', ['ul', 'ol', 'paragraph']],
            ['height', ['height']],
            ['insert',['picture','link','video']],
            ['view', ['fullscreen', 'help']]
        ],
        fontNames: ['Arial', 'Comic Sans MS', 'Courier New', '맑은 고딕', '궁서', '굴림체', '돋움체', '바탕체'],
        fontSizes: ['8','9','10','11','12','14','16','18','20','22','24','28','30','36','50','72'],
        fontNamesIgnoreCheck: ['맑은 고딕', 'Arial'],  // 설치되지 않은 글꼴도 선택 가능하게 설정
        defaultFontName: '맑은 고딕'  // 기본 글꼴 설정
    });
});