/**
 * 
 */

 const isLoggedIn = true; // 로그인 상태를 나타내는 변수

	// 로그인 체크 후 리다이렉트 함수
	function checkLogin(event, redirectUrl) {
		if (!isLoggedIn) {
			event.preventDefault(); // 기본 동작 방지
			window.location.href = '/member/login'; // 로그인 페이지로 리다이렉트
		} else {
			window.location.href = redirectUrl; // 원래 가고자 하는 URL로 이동
		}
	}

	function initMap() {
		const map = L.map('map').setView([ 37.5512, 126.9882 ], 13); // 서울 남산 타워 좌표
		L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
			maxZoom : 19,
		}).addTo(map);

		const marker = L.marker([ 37.5512, 126.9882 ]).addTo(map); // 마커 위치
	}

	document.addEventListener('DOMContentLoaded', initMap); // 페이지 로드 시 지도 초기화