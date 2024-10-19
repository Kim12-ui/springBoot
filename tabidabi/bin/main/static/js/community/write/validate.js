/**
 * submit전 유효성 검사
 */
document.getElementById('submitButton').addEventListener('submit', function(event) {
    
	// 제목이 비어 있는지 확인
    const title = document.querySelector('input[name="title"]').value;

    if (!title.trim()) {
        alert('제목을 입력해야 합니다.');
        event.preventDefault(); // 제출 막기
        return;
    }

	// 게시글_상세글 적기가 아예 안 적혀져 있을때
    const storageTitleList = document.querySelectorAll('input[name="storageTitle[]"]');
	const storageContentList = document.querySelectorAll('input[name="storageContent[]"]');
	const plusTitleList = document.querySelectorAll('input[name="plusTitle[]"]');
	const plusContentList = document.querySelectorAll('input[name="plusContent[]"]');
	
	// 모든 리스트의 값이 비어 있는지 확인
    const allStorageTitlesEmpty = [...storageTitleList].every(input => !input.value.trim());
    const allStorageContentsEmpty = [...storageContentList].every(input => !input.value.trim());
    const allPlusTitlesEmpty = [...plusTitleList].every(input => !input.value.trim());
    const allPlusContentsEmpty = [...plusContentList].every(input => !input.value.trim());
	
	// 모든 리스트가 비어있는지 확인
	const allEmpty = 
       [...storageTitleList].every(input => !input.value.trim()) &&
       [...storageContentList].every(input => !input.value.trim()) &&
       [...plusTitleList].every(input => !input.value.trim()) &&
       [...plusContentList].every(input => !input.value.trim());
	   
   // 모든 리스트가 비어있으면 폼 전송 방지
   if (allEmpty) {
       alert('게시판은 하나 이상 작성하세요.'); // 사용자에게 경고 메시지 표시
       event.preventDefault(); // 폼 제출 방지
	   return;
   }
   
   if ((allStorageTitlesEmpty || allStorageContentsEmpty) && (allPlusTitlesEmpty || allPlusContentsEmpty)) {
		alert("게시판에 제목과 내용을 모두 입력하세요.");
		event.preventDefault(); // 폼 제출 방지
	    return;
   }
   
   if ((storageTitleList.length !== storageContentList.length) || (plusTitleList.length !== plusContentList.length)) {
       alert("게시판에 제목과 내용을 모두 입력하세요.");
       event.preventDefault(); // 폼 제출 방지
       return;
   }

   
   // 출발일, 도착일, 대륙, 나라 선택X 시에는 폼 제출 방지
   const departureDate = document.querySelector("#inputStorageDeparture").value;
   const returnDate = document.querySelector("#inputStorageReturn").value;
   const continent = document.querySelector("#continentSelect").value;
   const country = document.querySelector("#countrySelect").value;
   
   if (!departureDate) {
	alert("출발일을 적으세요");
	event.preventDefault(); // 폼 제출 방지
   	return;
   }
   
   if (!returnDate) {
	alert("도착일을 적으세요");
	event.preventDefault(); // 폼 제출 방지
	return;
   }
   
   if (continent == "") {
   	alert("대륙을 선택하세요");
   	event.preventDefault(); // 폼 제출 방지
   	return;
  }
	  
  if (country == "나라" || country == "") {
  	alert("나라를 선택하세요");
  	event.preventDefault(); // 폼 제출 방지
  	return;
  }
});	
