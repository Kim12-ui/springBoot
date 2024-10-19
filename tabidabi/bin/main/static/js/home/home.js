/**
 * 
 */
function updateCountries() {
    const continentSelect = document.getElementById('continent');
    const countrySelect = document.getElementById('country');
    const continent = continentSelect.value;

    const countries = {
        asia: ['한국', '중국', '일본', '인도', '태국'],
        europe: ['프랑스', '독일', '영국', '이탈리아', '스페인'],
        'north-america': ['미국', '캐나다', '멕시코', '쿠바', '온두라스'],
        'south-america': ['브라질', '아르헨티나', '콜롬비아', '칠레', '페루'],
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
const isLoggedIn = true;

function checkLogin(event, redirectUrl) {
    if (!isLoggedIn) {
        event.preventDefault();
        window.location.href = '/member/login';
    } else {
        window.location.href = redirectUrl;
    }
}