/**
 * 
 */
window.onload=function() {
	const text1=document.querySelector('#text1');
	const text2=document.querySelector('#text2');
	const textNumber=document.querySelector('#textNumber');
	
	text1.addEventListener('keyup',function() {
		text2.value=text1.value;
		textNumber.innerHTML=text1.value.length+"글자";
	});
};