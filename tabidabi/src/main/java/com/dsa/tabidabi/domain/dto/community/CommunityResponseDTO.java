package com.dsa.tabidabi.domain.dto.community;

import org.springframework.data.domain.Page;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CommunityResponseDTO {
    private Page<CommunityDTO> communityDTOPage;
    private Page<CommunityInfoDTO> infoDTOPage;
    private Page<CommunityCommentsDTO> commentsDTOPage;

    // 생성자
    public CommunityResponseDTO(Page<CommunityDTO> communityDTOPage, 
    							Page<CommunityInfoDTO> infoDTOPage, 
    							Page<CommunityCommentsDTO> commentsDTOPage) {
        this.communityDTOPage = communityDTOPage;
        this.infoDTOPage = infoDTOPage;
        this.commentsDTOPage = commentsDTOPage;
    }
}
