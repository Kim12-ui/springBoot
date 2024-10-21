document.addEventListener('DOMContentLoaded', () => {
	// 대륙 선택 시 나라 업데이트 호출
    const continentSelect = document.getElementById('continentSelect');
    const countrySelect = document.getElementById('countrySelect');

    continentSelect.addEventListener('change', updateCountries);
	
	// 리더 & 참여자 명단
    const leaderDiv = $('#leader-list');
    const participantDiv = $('#participant-list');
    
    const leaderButton = $('#leader-button'); // jQuery를 사용하여 선택
    const participantButton = $('#participant-button');
    
    // 초기 상태 설정
    leaderDiv.show(); // show() 메서드로 표시
    participantDiv.hide(); // hide() 메서드로 숨김
    leaderButton.css('backgroundColor', '#B5FFAF'); // CSS 변경
    participantButton.css('backgroundColor', '#dcdcdc');

    // 동선 버튼 클릭 이벤트
    leaderButton.click(function() {
        // div 영역 변경
        leaderDiv.show(); // show() 메서드로 표시
        participantDiv.hide(); // hide() 메서드로 숨김

        // 버튼 색깔 변경
        leaderButton.css('backgroundColor', '#B5FFAF'); // 변경할 색상
        participantButton.css('backgroundColor', '#dcdcdc'); // 변경할 색상
    });
	
	// 댓글 버튼 클릭 이벤트
    participantButton.click(function() {
        // div 영역 변경
        leaderDiv.hide(); // hide() 메서드로 숨김
        participantDiv.show(); // show() 메서드로 표시

        // 버튼 색깔 변경
        leaderButton.css('backgroundColor', '#dcdcdc'); // 변경할 색상
        participantButton.css('backgroundColor', '#B5FFAF'); // 변경할 색상
    });
});

function updateCountries() {
        const continent = continentSelect.value;

        const countries = {
            asia: ['한국', '중국', '일본', '인도', '태국'],
            europe: ['프랑스', '독일', '영국', '이탈리아', '스페인'],
            northAmerica: ['미국', '캐나다', '멕시코', '쿠바', '온두라스'],
            southAmerica: ['브라질', '아르헨티나', '콜롬비아', '칠레', '페루'],
            africa: ['이집트', '남아프리카 공화국', '나이지리아', '케냐', '모로코'],
            oceania: ['호주', '뉴질랜드', '피지', '사모아', '타히티']
        };

        countrySelect.innerHTML = '<option value="">나라</option>'; // 초기화
        if (countries[continent]) {
            countries[continent].forEach(country => {
                const option = document.createElement('option');
                option.value = country;
                option.textContent = country;
                countrySelect.appendChild(option);
            });
        }
    }
