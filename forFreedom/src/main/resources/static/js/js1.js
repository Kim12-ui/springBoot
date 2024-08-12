// javascript 작성
window.onload=function() {
    // 5. DOM의 element들을 JavaScript로 불러와 활용 및 조작

    // subject1~5 (input태그, type="text", id)
    const subject1=document.querySelector('#subject1');
    const subject2=document.querySelector('#subject2');
    const subject3=document.querySelector('#subject3');
    const subject4=document.querySelector('#subject4');
    const subject5=document.querySelector('#subject5');

    // subject (input 태그, type="text", class)
    const subject=document.querySelector('.subject');

    // exScore1~5 (p태그, id)
    const exScore1=document.querySelector('#exScore1'); 
    const exScore2=document.querySelector('#exScore2'); 
    const exScore3=document.querySelector('#exScore3'); 
    const exScore4=document.querySelector('#exScore4'); 
    const exScore5=document.querySelector('#exScore5'); 

    // exScore (p태그, 클래스)
    const exScore=document.querySelector('.exScore');

    // button (input태그, type="button", id)
    const button=document.querySelector('#button');

    // target (div태그, id)
    const target=document.querySelector('#target');

    // average, fail (li태그, id)
    const average=document.querySelector('#average');
    const fail=document.querySelector('#fail');

    // result (div태그, id)
    const result=document.querySelector('#result');

    // 1. 예상점수 1문제당 5점으로 계산하여 출력
    // 1과목
    subject1.addEventListener('keyup',function() {
        let exScore_1=parseInt(5*subject1.value);
        exScore1.innerHTML='[예상점수]'+exScore_1+'점';
    });
    // 2과목
    subject2.addEventListener('keyup',function() {
        let exScore_2=parseInt(5*subject2.value);
        exScore2.innerHTML='[예상점수]'+exScore_2+'점';
    });
    // 3과목
    subject3.addEventListener('keyup',function() {
        let exScore_3=parseInt(5*subject3.value);
        exScore3.innerHTML='[예상점수]'+exScore_3+'점';
    });
    // 4과목
    subject4.addEventListener('keyup',function() {
        let exScore_4=parseInt(5*subject4.value);
        exScore4.innerHTML='[예상점수]'+exScore_4+'점';
    });
    // 5과목
    subject5.addEventListener('keyup',function() {
        let exScore_5=parseInt(5*subject5.value);
        exScore5.innerHTML='[예상점수]'+exScore_5+'점';
    });

    // 2. 문자 입력 => '숫자를 입력하세요'
    // 20을 넘어갈 경우 => '각 과목당 맞춘 문제수(0~20)을 입력하세요'
    // 해당 입력박스의 값을 null로 초기화 시키고 예상점수 문구를 안보이도록 설정
    // 1과목
    subject1.addEventListener('keyup',function(){
        if(isNaN(subject1.value)) {
            subject1.value=null;
            exScore1.innerHTML='';
            alert('숫자를 입력하세요');
        }
        if(0>parseInt(subject1.value)||parseInt(subject1.value)>20) {
            subject1.value=null;
            exScore1.innerHTML='';
            alert('각 과목당 맞춘 문제수(0~20)를 입력하세요');
        }
    });
    // 2과목
    subject2.addEventListener('keyup',function(){
        if(isNaN(subject2.value)) {
            subject2.value=null;
            exScore2.innerHTML='';
            alert('숫자를 입력하세요');
        }
        if(0>parseInt(subject2.value)||parseInt(subject2.value)>20) {
            subject2.value=null;
            exScore2.innerHTML='';
            alert('각 과목당 맞춘 문제수(0~20)를 입력하세요');
        }
    });
    // 3과목
    subject3.addEventListener('keyup',function(){
        if(isNaN(subject3.value)) {
            subject3.value=null;
            exScore3.innerHTML='';
            alert('숫자를 입력하세요');
        }
        if(0>parseInt(subject3.value)||parseInt(subject3.value)>20) {
            subject3.value=null;
            exScore3.innerHTML='';
            alert('각 과목당 맞춘 문제수(0~20)를 입력하세요');
        }
    });
    // 4과목
    subject4.addEventListener('keyup',function(){
        if(isNaN(subject4.value)) {
            subject4.value=null;
            exScore4.innerHTML='';
            alert('숫자를 입력하세요');
        }
        if(0>parseInt(subject4.value)||parseInt(subject4.value)>20) {
            subject4.value=null;
            exScore4.innerHTML='';
            alert('각 과목당 맞춘 문제수(0~20)를 입력하세요');
        }
    });
    // 5과목
    subject5.addEventListener('keyup',function(){
        if(isNaN(subject5.value)) {
            subject5.value=null;
            exScore5.innerHTML='';
            alert('숫자를 입력하세요');
        }
        if(0>parseInt(subject5.value)||parseInt(subject5.value)>20) {
            subject5.value=null;
            exScore5.innerHTML='';
            alert('각 과목당 맞춘 문제수(0~20)를 입력하세요');
        }
    });

    // 3. 최종 결과 확인 버튼을 누를 경우에만 최종 결과가 보이도록 클릭 이벤트를
    // 설정하고 해당 최종 결과 영역에 display 속성값 부여

    // 4.최종 결과 영역에 평균, 과락된 과목 수의 값을 계산하여 출력
    
    button.addEventListener('click',function(){
        let str=``; 
        let sub1Score=(parseInt(subject1.value))*5
        let sub2Score=(parseInt(subject2.value))*5
        let sub3Score=(parseInt(subject3.value))*5
        let sub4Score=(parseInt(subject4.value))*5
        let sub5Score=(parseInt(subject5.value))*5
        let averageScore=(sub1Score+sub2Score+sub3Score+sub4Score+sub5Score)/5
        let failNumber=0;
        let failarr=[sub1Score,sub2Score,sub3Score,sub4Score,sub5Score];
        for(let i=0;i<failarr.length;i++) {
            if(failarr[i]<40) {
                failNumber++
            }
        }

        // 점수 기입 X시
        if(isNaN(averageScore)) {
            alert('점수를 모두 기입해야 합니다');
            target.style.display='none';
        }
        // 과락 시 (불합격)
        else if(
            sub1Score<40||
            sub2Score<40||
            sub3Score<40||
            sub4Score<40||
            sub5Score<40
        ) {
            str+='<ul>';
            str+=`<li id="average">평균: ${averageScore}</li>`;
            // 불합격시 과락된 과목수
            str+=`<li id="fail">과락된 과목 수: ${failNumber}과목</li>`;
            str+=`</ul>`;
            str+=`<div id="result">불합격</div>`;
            target.innerHTML=str;
            target.style.display='block';
        }
        // 과락X & 평균 60 미만 (불합격)
        else if (averageScore<60) {
            str+='<ul>';
            str+=`<li id="average">평균: ${averageScore}</li>`;
            str+=`<li id="fail">과락된 과목 수: ${failNumber}과목</li>`;
            str+=`</ul>`;
            str+=`<div id="result">불합격</div>`;
            target.innerHTML=str;
            target.style.display='block'; 
        }
        // 과락X & 평균 60 이상 (합격)
        else {
            str+='<ul>';
            str+=`<li id="average">평균: ${averageScore}</li>`;
            str+=`<li id="fail">과락된 과목 수: ${failNumber}과목</li>`;
            str+=`</ul>`;
            str+=`<div id="result">합격</div>`;
            target.innerHTML=str;
            target.style.display='block';
        }
    });
}