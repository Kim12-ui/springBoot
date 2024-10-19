window.onload = function(){
        const countriesByContinent = {
            asia: ['일본', '중국', '한국', '인도', '베트남'],
            europe: ['프랑스', '독일', '영국', '이탈리아', '스페인'],
            northAmerica: ['미국', '캐나다', '멕시코'],
            southAmerica: ['브라질', '아르헨티나', '칠레'],
            africa: ['이집트', '남아프리카 공화국', '케냐'],
            oceania: ['호주', '뉴질랜드', '피지']
        };

        const continentSelect = document.getElementById('continentSelect');
        const countrySelect = document.getElementById('countrySelect');
		
		
		// 선택된 대륙과 국가를 저장하는 변수
			let selectedContinent = '';
			let selectedCountry = '';
			
        // 대륙 선택시 나라 목록을 업데이트하는 함수
        continentSelect.addEventListener('change', function() {
            selectedContinent = this.value; // 대륙 선택 업데이트
            
            //선택된 대륙의 나라배열이 들어가는 변수 : countries
            const countries = countriesByContinent[selectedContinent];

            // 기존 나라 목록 초기화
            countrySelect.innerHTML = '<option value="" selected>나라</option>';

            // 선택한 대륙에 맞는 나라 목록 추가
            // 배열이 정의되어 있으면 참
            // 빈 배열이면 거짓
            if (countries) { //배열이 정의되어 있으면 반복문을 돌린다.
                countries.forEach(country => { //country: couyntries의 각 요소들
                    const option = document.createElement('option');
                    option.value = country;
                    option.textContent = country;
                    countrySelect.appendChild(option);
                });
            }
        });
        
        // 나라 선택 시 값 업데이트
   	     countrySelect.addEventListener('change', function() {
        selectedCountry = this.value;
    });
    
        // 선택된 값을 가져오는 함수
    function getSelectedValues() {
        return {
            continent: selectedContinent,
            country: selectedCountry
        };
    }
        
	 // 전역 함수로 설정 (다른 파일에서 이 함수에 접근할 수 있도록)
    window.getSelectedValues = getSelectedValues;
    }
    


