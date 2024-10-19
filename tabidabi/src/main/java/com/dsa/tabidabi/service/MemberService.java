package com.dsa.tabidabi.service;

import org.springframework.web.multipart.MultipartFile;

import com.dsa.tabidabi.domain.dto.MemberDTO;

public interface MemberService {

	boolean idDuplicate(String id);

	void join(MemberDTO member);

	MemberDTO findById(String memberId);

	void update(MemberDTO memberDTO);

	
}