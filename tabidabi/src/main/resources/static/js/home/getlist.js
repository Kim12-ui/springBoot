/**
 * 인기 패키지 리스트 출력
 */
$(document).ready(function() {
	// 페이지 버튼 클릭 시 해당 페이지의 데이터 요청
    $(document).on('click', '.page-link', function(e) {
		e.preventDefault(); // 링크 기본 동작 방지
        const page = $(this).data('page');
		
        getList(page);
    });
	
	$('#list_container').on('click', 'img.like-button', function() {
		processLike($(this));
	});
	
	// 찾기 버튼을 클릭할때
    $('#select-button').click(function() {
        getList(1); // 기본적으로 1 페이지로 검색
    });
	
	getList(1); // 초기 데이터 요청
});

function getList(page) {
	// 선택된 대륙과 나라 값 가져오기
    const continent = document.querySelector('#continentSelect').value;
    const country = document.querySelector('#countrySelect').value;
    const title = document.querySelector("#searchTitle").value;
	
	// 선택된 값 확인 (디버깅용)
	console.log("Selected continent : ",continent);
	console.log("Selected country : ",country);
	console.log("Search-Title : ",title);
	console.log("page : ", page);
	
	// ajax
	$.ajax({
		url : "popularPostSearch",
		method : "post",
		data : {continent : continent, country : country, title : title, page : page},
		success : function(response) {
			console.log(response.communityListDTOs);	// 게시글에 띄울 정보들
			console.log(response.selectedLikedList);	// 인증된 유저의 좋아요 목록
			
			// 결과 없을 시 처리
			if (response.communityListDTOs.content.length === 0) {
				$('#list_container').html('<p>결과가 없습니다.</p>')
				return;
			}
			// 이전 데이터 초기화
			$('#list_container').empty();
			
			// 커뮤니티 정보 동적으로 추가
			$(response.communityListDTOs.content).each(function(idx, community) {
					const communityDTO = community.communityDTO;
	                const infoDTO = community.communityInfoDTO; // 정보 DTO
					const commentsDTO = community.communityCommentsDTO;
					
					const communityReadUrl = "community/read?communityId=";
					let representativeImageUrl = "images/community/list/게시글기본이미지.png";
					if (commentsDTO.representativeImage != null) {
						representativeImageUrl = "http://localhost:4444/tabidabi/representativeimage/"+commentsDTO.representativeImage;
					}
					const profileUrl = "images/myinfo/기본프로필.jpeg";
					let likeUrl = "images/community/list/like.png";
					
					// 좋아요 상태에 따라 이미지 변경
					for (const likedCommunity of response.selectedLikedList) {
						if (likedCommunity.communityId == communityDTO.communityId) {
							likeUrl = "images/community/list/좋아요_클릭후.png";
						}
					}
					const viewUrl = "images/community/list/views.png";
					
					let html = 
		                `<div class="list_box">
							<div style="width: 100%; height: 300px; overflow: hidden; position: relative;">
		                    	<img src="${representativeImageUrl}" alt="대표 이미지" class="representative"
								style="width: 100%; height: 100%; object-fit: contain;">
							</div>
		                    <p class="tripLocation">${infoDTO.communityCountry}</p>
		                    <p class="title"><a href="${communityReadUrl}${communityDTO.communityId}">${communityDTO.title}</a></p>
		                    <p>
		                        <span>${infoDTO.communityDeparture}</span>
		                        ~
		                        <span>${infoDTO.communityReturn}</span>
		                    </p>
		                    <div class="info-row">
		                        <div class="nickname">
		                            <img src="${profileUrl}" alt="프로필 사진" class="profile">
		                            <span>${communityDTO.nickname}</span>
		                        </div>
		                        <div class="stats">
		                            <img id="like-button-${communityDTO.communityId}" class="like-button" like-data="${communityDTO.communityId}" src="${likeUrl}" alt="좋아요">
		                            <span id="like-number-${communityDTO.communityId}" class="like-number">${communityDTO.likeCount}</span>
		                            <img src="${viewUrl}" alt="조회수">
		                            <span>${communityDTO.viewCount}</span>
		                        </div>
		                    </div>
		                </div>`
	                ;
					$('#list_container').append(html);		
			});
			
			// 페이지네이션 생성
            createPagination(response.communityListDTOs.totalPages, page); // totalPages와 현재 페이지 전달
		},
		error: function(xhr) {
            alert("목록 조회 실패 : " + xhr.status + " " + xhr.statusText);
        }
	});
}

function createPagination(totalPages, currentPage) {
    const paginationContainer = $('#pagination_container');
    paginationContainer.empty(); // 이전 버튼들 제거

    // 이전 페이지 버튼
    if (currentPage > 1) {
        paginationContainer.append(
            `<li class="page-item">
                <a class="page-link" href="#" aria-label="Previous" data-page="${currentPage - 1}">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>`
        );
    }

    // 페이지 번호 리스트
    const startPage = Math.max(1, currentPage - 1); // 현재 페이지의 앞쪽 페이지
    const endPage = Math.min(totalPages, currentPage + 1); // 현재 페이지의 뒷쪽 페이지

    for (let i = startPage; i <= endPage; i++) {
        const activeClass = (i === currentPage) ? 'active' : '';
        paginationContainer.append(
            `<li class="page-item ${activeClass}">
                <a class="page-link" href="#" data-page="${i}">${i}</a>
            </li>`
        );
    }

    // 다음 페이지 버튼
    if (currentPage < totalPages) {
        paginationContainer.append(
            `<li class="page-item">
                <a class="page-link" href="#" aria-label="Next" data-page="${currentPage + 1}">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>`
        );
    }
}


function processLike(element) {
	// $(this).attr => element.attr
	const communityId = element.attr('like-data');
	const likeCountElement = $(`#like-number-${communityId}`);
			
	// 좋아요 처리 로직
	console.log("communityId :", communityId);
	
	// AJAX 요청으로 좋아요 증가 처리
	$.ajax({
		url : "like",
		method : "POST",
		data : {communityId : communityId},
		success : function(likeResult) {
			if (likeResult) {
				console.log("좋아요 누르기 성공, DB에 값 저장");
				element.attr("src","images/community/list/좋아요_클릭후.png");
				// 좋아요 수 증가
				let currentLikeCount = parseInt(likeCountElement.text(), 10); // 현재 좋아요 수 가져오기
				likeCountElement.text(currentLikeCount + 1); // 1 증가시키기
			}
			if (!likeResult) {
				console.log("좋아요 취소 성공, DB에 값 삭제");
				element.attr("src","images/community/list/like.png");
				// 좋아요 수 감소
				let currentLikeCount = parseInt(likeCountElement.text(), 10); // 현재 좋아요 수 가져오기
				likeCountElement.text(currentLikeCount - 1); // 1 감소시키기
			}
		},
		error : function() {
			alert("로그인을 해주세요.");
			console.log("인증되지 않는 사용자입니다.");
		}
	});
}