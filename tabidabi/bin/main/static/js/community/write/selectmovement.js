/**
 * 모달 창에서 버튼 클릭시 해당 정보_상세 정보 출력하도록
 */
$(document).ready(function() {
	$('#select-button').click(getList);
});

function getList() {
	// 선택된 storageId와 planType 가져오기
    const selectedStorageId = document.querySelector('input[name="storageId"]:checked');
    const storageId = selectedStorageId ? selectedStorageId.value : null; // 선택된 storageId
    const planTypeSelect = document.querySelector('.planTypeSelect');
    const planType = planTypeSelect ? planTypeSelect.value : null; // 선택된 planType select

    // 선택된 값 확인 (디버깅용)
    console.log("Selected StorageId: ", storageId);
    console.log("Selected PlanType: ", planType);
    
    if (!storageId || !planType) {
	    alert("저장소 ID와 계획 타입을 모두 선택해야 합니다.");
	    return;
	}
    
    // ajax
    $.ajax({
		url : "selectmovement",
		method : "post",
		data : {storageId : storageId, planType : planType},
		success : function(response) {
			console.log(response.list);
			
			$('#movement-details-list').empty();
			$(response.list).each(function(idx, details) {
				let html = `
				<div class="movement-row">
		            <div class="movement-title">
		                <input type="text" value="${details.title}" name="storageTitle[]" class="movement-input-title" placeholder="제목을 입력하세요">
		            </div>
		            <div class="movement-content">
		                <input type="text" value="${details.content}" name="storageContent[]" class="movement-input-content" placeholder="내용을 입력하세요">
		            </div>
	            </div>
				`;
				$('#movement-details-list').append(html);
			});
			
			// 출발일과 도착일은 input 태그에다가 입력되게
			$("#inputStorageDeparture").val(response.dto.storageDeparture);
			$("#inputStorageReturn").val(response.dto.storageReturn);
			
			// 대륙 선택 값을 설정 (예: "Asia", "Europe" 등의 값으로 선택)
		    const storageContinent = response.dto.storageContinent;
		    if (storageContinent) {
		        $('#continentSelect').val(storageContinent);
		    }
			
			// 나라 선택 값을 설정 (예: "일본", "캐나다" 등의 값으로 선택)
			// 대륙별 나라 목록 업데이트
			const countriesByContinent = {
			    asia: ['일본', '중국', '한국', '인도', '베트남'],
			    europe: ['프랑스', '독일', '영국', '이탈리아', '스페인'],
			    northAmerica: ['미국', '캐나다', '멕시코'],
			    southAmerica: ['브라질', '아르헨티나', '칠레'],
			    africa: ['이집트', '남아프리카 공화국', '케냐'],
			    oceania: ['호주', '뉴질랜드', '피지']
			};

			const storageCountry = response.dto.storageCountry; // 선택된 나라
			const countrySelect = $('#countrySelect'); // 나라 select 요소

			// 나라 목록 초기화
			countrySelect.empty().append('<option selected>나라</option>');

			// 선택된 대륙에 해당하는 나라 목록 추가
			const countries = countriesByContinent[storageContinent];
			if (countries) {
			    countries.forEach(function(country) {
			        const option = $('<option></option>').val(country).text(country);
			        countrySelect.append(option);
			    });
			}

			// 나라 값이 있는 경우 해당 값을 선택
			if (storageCountry) {
			    countrySelect.val(storageCountry);
			}
		},
		error : function() {
			alert("목록 조회 실패");
		}
	});
	
}