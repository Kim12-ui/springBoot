document.addEventListener('DOMContentLoaded', () => {
    const continentSelect = document.getElementById('continentSelect');
    const countrySelect = document.getElementById('countrySelect');

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

        countrySelect.innerHTML = '<option value="">나라 선택</option>'; // 초기화
        if (countries[continent]) {
            countries[continent].forEach(country => {
                const option = document.createElement('option');
                option.value = country;
                option.textContent = country;
                countrySelect.appendChild(option);
            });
        }
    }

    // 대륙 선택 시 나라 업데이트 호출
    continentSelect.addEventListener('change', updateCountries);
});


const isLoggedIn = true;

function checkLogin(event, redirectUrl) {
    if (!isLoggedIn) {
        event.preventDefault();
        window.location.href = '/member/login';
    } else {
        window.location.href = redirectUrl;
    }
}

let currentRole = ''; // 현재 선택된 역할 저장 변수

// 역할 선택 함수
function selectRole(role) {
	const leaderButton = document.getElementById('leader-button'); // 방장 버튼
	const participantButton = document.getElementById('participant-button'); // 참가자 버튼

	if (currentRole === role) {
		currentRole = ''; // 선택 해제
		leaderButton.classList.remove('active'); // 방장 버튼 비활성화
		participantButton.classList.remove('active'); // 참가자 버튼 비활성화
	} else {
		currentRole = role; // 새 역할 설정
		leaderButton.classList.remove('active'); // 방장 버튼 비활성화
		participantButton.classList.remove('active'); // 참가자 버튼 비활성화

		// 선택된 역할에 따라 버튼 활성화
		if (role === 'leader') {
			leaderButton.classList.add('active'); // 방장 버튼 활성화
		} else if (role === 'participant') {
			participantButton.classList.add('active'); // 참가자 버튼 활성화
		}
	}
}