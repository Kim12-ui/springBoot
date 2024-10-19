$(document).ready(function() {
    getCommunityList();
});

function getCommunityList() {
    $.ajax({
        url: "/tabidabi/community/getCommunityList",
        method: "post",
        data: {},
        success: function(list) {
            if (list.length === 0) {
                $('#list_container').html('<p>결과가 없습니다.</p>');
                return;
            }

            $('#list_container').empty();
            // 커뮤니티 정보 동적으로 추가
			$(list).each(function(idx, community) {
			   const communityReadUrl = `/tabidabi/community/read?communityId=${community.communityDTO.communityId}`;
               const representativeImageUrl = `../images/community/list/게시글기본이미지.png`;
               const profileUrl = "../images/myinfo/기본프로필.jpeg";
               const likeUrl = "../images/community/list/좋아요_클릭전.png";
               const viewUrl = "../images/community/list/조회수.png";
               
               let html = `
               <div class="list_box">
                    <img src="${representativeImageUrl}" alt="대표 이미지" class="representative">
                    <p class="tripLocation">${community.communityInfoDTO.communityCountry}</p>
                    <p class="title"><a href="${communityReadUrl}">${community.communityDTO.title}</a></p>
                    <p>
                    	<span>${community.communityInfoDTO.communityDeparture}</span>
                    	~
                    	<span>${community.communityInfoDTO.communityReturn}</span>
                    </p>
                    <div class="info-row">
                        <div class="nickname">
                            <img src="${profileUrl}" alt="프로필 사진" class="profile">
                            <span>${community.communityDTO.nickname}</span>
                        </div>
                        <div class="stats">
                            <img src="${likeUrl}" id="like-button" alt="좋아요" like-data="${community.communityDTO.communityId}"> <span>${community.communityDTO.likeCount}</span> 
                            <img src="${viewUrl}" alt="조회수"> <span>${community.communityDTO.viewCount}</span>
                        </div>
                    </div>
                </div>
               `;
               
               $('#list_container').append(html);
			});
		},
        error: function() {
            alert("목록 조회 실패");
        }
    });
}
