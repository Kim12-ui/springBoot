/**
 * 
 */
function deleteFunc(paramNum) {
	const paramPw = document.getElementById(paramNum).value;
	location.href='delete?num='+paramNum+"&password="+paramPw;
}