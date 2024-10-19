document.addEventListener('DOMContentLoaded',function(){
	
   console.log('DOM이 로드되었습니다.'); // DOMContentLoaded 확인
	document.getElementById('saveSchedule').addEventListener('click',function(){
	 console.log('saveSchedule 버튼이 클릭되었습니다.');
	// 클릭 후 버튼 비활성화
    const saveButton = document.getElementById('saveSchedule');
    saveButton.disabled = true;  //한번에 여러번 클릭해서 중복으로 데이터가 DB로 들어가는 것 방지

	 
console.log("다른 파일에서 markersData 확인: ", window.markersData);

	
	// 각 외부 js파일에 정의된 변수나 함수가 전역 스코프에 정의될 경우 기본적으로 window 객체 속성으로 등록된다.
	// map.js의 markersData 배열과 select.js의 getSelectedValues() 함수에서 값을 가져옴
	const markersData =  window.markersData; 
	console.log("마커 데이터가 정상적으로 로드되었습니다: ", markersData);  // 결과 : 넘어오지 않음. 
	const selectedValues = window.getSelectedValues();
	console.log("selectedValues : " ,selectedValues); //결과 : selectedValues :  {continent: '', country: '브라질'}
	// html의 input 값들
		//방 이름	
		const roomName = document.getElementById('roomName').value;
		
		//히든필드에서 roomId와 storageId 가져오기
		const roomId = document.getElementById('roomId').innerText; 
    	const storageId = document.getElementById('storageId').innerText; 

    console.log('룸 아이디:', roomId);
    console.log('스토리지 아이디:', storageId);

		
		// 로그인 성공 시 전역 변수 설정
			window.currentUserId = 'loggedInUserId'; // 예시 값
			
			// 현재 로그인한 사용자 ID 가져오기
			const userId = window.currentUserId;
			
			console.log('현재 로그인한 사용자 ID:', userId);
		
		//세부일정 이름
		const scheduleNames = document.querySelectorAll('.schedule-name');
		//세부일정 상세정보
		const scheduleDescriptions = document.querySelectorAll('.schedule-description');
		//입력된 값들을 배열로 변환 (세부일정)
			//Array.from() : 이 메서드는 NodeList(iterable객체)를 배열로 변환
			//map(input => input.value) : 변환된 배열의 각 요소(name)를 변환하여 새로운 배열을 만든다.
			//input => input.value : input요소의 .value값 반환 
		const names = Array.from(scheduleNames).map(input => input.value);
		const contents = Array.from(scheduleDescriptions).map(textarea => textarea.value);   //이 배열들 반복문에 돌려서 넣어야하는거 아님?
		
			//여행출발일
		const tripStart = document.querySelector('.tripStart').value;
		//여행복귀일
		const tripEnd = document.querySelector('.tripEnd').value;
		// 콘솔 출력
		
		/**
		 * 		console.log('방 이름', roomName);
		console.log('방 번호', roomId);
		console.log('세부일정 이름',scheduleNames );
		console.log('세부일정 내용',scheduleDescriptions)
		console.log('일정 이름 배열' , names);
		console.log('일정 설명 배열 ', contents);
		console.log('마커배열 : ', markersData);
		console.log('선택된: {} ', selectedValues, selectedValues.continent,selectedValues.country )
	    console.log('선택된 대륙: {} ', selectedValues.continent)
		console.log('선택된 나라: {} ', selectedValues.country )

		console.log('여행 출발일 : ', tripStart);
		console.log('여행 도착일 : ', tripEnd);
		 */
		
		console.log("마커데이터 : {}",markersData)

		
		

		
		
	//스프링 컨트롤러에 보낼 데이터를 준비
	const requestData = {
		sharingroom : {
			roomId : roomId,
			roomChief : userId,
			roomTitle : roomName
		},
		
		sharingroomInformation : {
			informationRoom : roomId,
			planType : "planA",
			informationDeparture : tripStart,
			informationReturn : tripEnd,
			informationContinent : selectedValues.continent,
			informationCountry : selectedValues.country,
			informationBudget : 500,
			informationBudgetPeople : 5
		},
		
		sharingroomInformationDetails : {
			planType : "planA",
			informationRoom : roomId,
			titles : names,
			contents : contents
		},
		
		sharingroomApi :{
			roomApiId : roomId,
			planType : "planA",
			markers : markersData
		},
		
		/*sharingroomParticipants : {
			participantId :  ,
			participantMember : ,
			participantRoom : roomId,
			
		}*/
		
		storage : {
			memberId : userId,
			roomTitle : roomName
		},
		
		storageInformation : {
			storageId : storageId ,
			planType : "planA",
			storageDeparture : tripStart,
			storageReturn : tripEnd,
			storageContinent : selectedValues.continent,
			storageCountry : selectedValues.country
		},
		storageInformationDetails : {
			storageId : storageId ,
			planType : "planA",
			titles : names,
			contents : contents
		}
		
	

		
		
	
    
    
}; //requestData 끝 
		
		
		
	
	
	
	//데이터 전송 코드 (fetch API)
	//fetch() : http요청 보낼때 사용
	//fetch('URL', option(ex: method , header...))
	console.log(requestData)
	console.log('전송할 데이터sharingroom : {} ', JSON.stringify(requestData.sharingroom)); // 데이터 로그
	console.log('전송할 데이터sharingroom : {} ', JSON.stringify(requestData.sharingroomApi)); // 데이터 로그
	console.log('전송할 데이터sharingroom : {} ', JSON.stringify(requestData.sharingroomInformation)); // 데이터 로그
	console.log('전송할 데이터sharingroom : {} ', JSON.stringify(requestData.sharingroomInformationDetails)); // 데이터 로그
	console.log('전송할 데이터sharingroom : {} ', JSON.stringify(requestData.storage)); // 데이터 로그
	console.log('전송할 데이터sharingroom : {} ', JSON.stringify(requestData.storageInformation)); // 데이터 로그
	console.log('전송할 데이터sharingroom : {} ', JSON.stringify(requestData.storageInformationDetails)); // 데이터 로그
	fetch(`http://localhost:4444/tabidabi/sharingroom/saveSchedule`,{
		method: 'POST',
		headers: { //이 헤더는 서버에 전송되는 데이터 형식이 JSON임을 알려준다.
			'Content-Type' : 'application/json'
		},
		//body: 요청에 포함될 실제 데이터 설정
		//JSON.stringify(requestData) :
				//requestData객체를 JSON문자열로 변환
		body: JSON.stringify(requestData)
	})
	//then : 서버의 응답을 처리하는 부분
		//response => response.json()) : 서버로부터 받은 응답객체를 JSON형식으로 변환
		//변환이 완료되면  다음 then으로 전달
	.then(response => {
    console.log('응답 상태:', response.status); // 응답 상태 로그
    if (!response.ok) {
        throw new Error('Network response was not ok ' + response.statusText);
    }
    return response.json();
})
	//JSON형식으로 변환된 데이터를 처리하는 부분
 		//data : 서버에서 응답받은 JSON데이터
	.then(data => {
    console.log('성공:', data);
    saveButton.disabled = false; // 버튼 다시 활성화
})	.catch((error) => {
		console.error('에러:', error);
		saveButton.disabled = false; // 에러 발생 시 버튼 활성화
	});
}); //button
}); //dom