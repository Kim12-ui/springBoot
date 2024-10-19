document.getElementById('adjustButton').addEventListener('click',function(){
    console.log('정산 버튼 클릭');

    const Sub_Area = document.querySelector(".sub-func");
     // 기존 내용 제거 (필요한 경우)
     Sub_Area.innerHTML = '';

     // a input 요소 생성
     const inputA = document.createElement('input');
     inputA.type = 'text';
     inputA.id = 'cost';
     inputA.className='search_input_small';
     inputA.placeholder = '여행 경비 총 금액';

     // b input 요소 생성
     const inputB = document.createElement('input');
     inputB.type = 'text';
     inputB.id = 'people';
     inputB.className='search_input_small';
     inputB.placeholder = '여행 인원 수';

     // 결과를 출력할 div 생성
     const outputText = document.createElement('div');
     outputText.className = 'output';
     outputText.id = 'outputText';

     // 값 출력 버튼 생성
     const outputButton = document.createElement('button');
     outputButton.id='outputButton';
     outputButton.className='mid_button';
     outputButton.textContent = '계산';
     outputButton.addEventListener('click', function() {
         let cost = parseFloat(inputA.value); // input a의 값
         let people = parseFloat(inputB.value); // input b의 값

         if (isNaN(cost) || isNaN(people) || cost <= 0 || people <= 0) {
            alert("금액과 인원 수는 0보다 큰 숫자여야 합니다.");
            inputA.value = '';  // 입력값 리셋
            inputB.value = '';  // 입력값 리셋
            outputText.textContent = '';  // 결과 리셋
            return;  // 잘못된 입력 시 계산하지 않음
        }

         let result = (cost / people).toFixed(1); // 소수점 1자리까지 문자열로 변환
             result = parseFloat(result).toLocaleString('ko-KR'); // 문자열을 숫자로 변환 후 로케일 형식으로 변환

         // 출력 텍스트 업데이트
         outputText.textContent = `1인당 : ${result.toLocaleString('ko.KR')} 원`;
     });

     // 요소를 .sub-func에 추가
     Sub_Area.appendChild(inputA);
     Sub_Area.appendChild(inputB);
     Sub_Area.appendChild(outputButton);
     Sub_Area.appendChild(outputText);
 });
