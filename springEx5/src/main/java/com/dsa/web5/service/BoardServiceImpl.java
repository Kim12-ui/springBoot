package com.dsa.web5.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dsa.web5.dto.BoardDTO;
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

	/**
	 * DB로부터 게시글 전체 조회
	 * @return 글 목록
	 */
	@Override
	public List<BoardDTO> selectAllList() {		
		// boardNum을 정렬기준으로 데이터를 entity로 가져오기
		// DESC = 내림차순, ASC = 오름차순
		Sort sort = Sort.by(Sort.Direction.DESC, "boardNum");
		List<BoardEntity> entityList = boardRepository.findAll(sort);
		
		// entity 데이터를 dto 데이터로 옮겨담기
		List<BoardDTO> dtoList = new ArrayList<>();
		for (BoardEntity entity : entityList) {
			BoardDTO dto = convertDTO(entity);
			dtoList.add(dto);
		}
		return dtoList;
	}

	/**
	 * 게시글 1개 조회
	 * @param boardNum 글번호
	 * @return BoardDTO 글정보
	 */
	@Override
	public BoardDTO getBoard(int boardNum) {
		// boardNum에 일치하는 게시글이 있는지 조회
		BoardEntity entity = boardRepository.findById(boardNum)
				.orElseThrow(() -> 
				new EntityNotFoundException("해당 번호의 글이 없습니다."));
		
		// entity의 viewCount + 1
		entity.setViewCount(entity.getViewCount() + 1);
		
		// 존재한다면 entity의 정보를 dto에 옮겨담기
		BoardDTO dto = convertDTO(entity);
		// dto 리턴
		return dto;
	}
	
	/**
	 * DB에서 조회한 게시글 정보인 BoardEntity 객체를 BoardDTO 객체로 변환
	 * @param entity 객체
	 * @return dto 객체
	 */
	private BoardDTO convertDTO(BoardEntity entity) {
		return BoardDTO.builder()
			   .boardNum(entity.getBoardNum())
			   .memberId(entity.getMember() != null
			   ? entity.getMember().getMemberId() : null)
			   .memberName(entity.getMember() != null
			   ? entity.getMember().getMemberName() : null)
			   .title(entity.getTitle())
			   .contents(entity.getContents())
			   .viewCount(entity.getViewCount())
			   .likeCount(entity.getLikeCount())
			   .originalName(entity.getOriginalName())
			   .fileName(entity.getFileName())
			   .createDate(entity.getCreateDate())
			   .updateDate(entity.getUpdateDate())
			   .build();
	}
}
