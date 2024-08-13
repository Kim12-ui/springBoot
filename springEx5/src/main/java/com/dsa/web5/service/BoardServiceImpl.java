package com.dsa.web5.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dsa.web5.dto.BoardDTO;
import com.dsa.web5.dto.MemberDTO;
import com.dsa.web5.entity.BoardEntity;
import com.dsa.web5.entity.MemberEntity;
import com.dsa.web5.repository.BoardRepository;
import com.dsa.web5.repository.MemberRepository;
import com.dsa.web5.repository.ReplyRepository;
import com.dsa.web5.util.FileManager;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 게시판 관련 서비스
 */
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class BoardServiceImpl implements BoardService {

	private final BoardRepository boardRepository;
	private final MemberRepository memberRepository;
	private final ReplyRepository replyRepository;
	private final FileManager fileManager;
	
	@Override
	public void write(BoardDTO boardDTO, String uploadPath, MultipartFile upload) throws IOException {
		MemberEntity memberEntity = memberRepository.findById(boardDTO.getMemberId())
				.orElseThrow(() -> new EntityNotFoundException("아이디가 없습니다."));
		
		BoardEntity entity = new BoardEntity();
		entity.setMember(memberEntity);
		entity.setTitle(boardDTO.getTitle());
		entity.setContents(boardDTO.getContents());
		
		// 첨부파일이 있는 경우
		if (upload != null && !upload.isEmpty()) {
			String fileName = fileManager.saveFile(uploadPath, upload);
			entity.setFileName(fileName);
			entity.setOriginalName(upload.getOriginalFilename());
		}
		
		boardRepository.save(entity);
	}

	@Override
	public List<BoardDTO> selectAllList() {
		List<BoardEntity> entityList = boardRepository.findAll();
		List<BoardDTO> dtoList = new ArrayList<>();
		
		for (BoardEntity entity:entityList) {
			BoardDTO dto = new BoardDTO();
			dto.setBoardNum(entity.getBoardNum());
			dto.setTitle(entity.getTitle());
			dto.setMemberId(entity.getMember().getMemberId());
			dto.setViewCount(entity.getViewCount());
			dto.setCreateDate(entity.getCreateDate());
			dtoList.add(dto);
		}
		return dtoList;
	}
	
}
