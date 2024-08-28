package com.dsa.web6.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dsa.web6.dto.CommentDTO;
import com.dsa.web6.entity.CommentEntity;
import com.dsa.web6.repository.CommentRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository repository;
	
	/**
	 * 댓글 목록 조회
	 * @return 댓글 목록
	 */
	public ArrayList<CommentDTO> getList() {
		List<CommentEntity> entityList = repository.findAll();
		ArrayList<CommentDTO> dtoList = new ArrayList<>();
		
		for (CommentEntity entity : entityList) {
			CommentDTO dto = CommentDTO.builder()
					.num(entity.getNum())
					.name(entity.getName())
					.comment(entity.getComment())
					.build();
			dtoList.add(dto);
		}
		return dtoList;
	}

	/**
	 * 댓글 저장
	 * @param commentDTO 저장할 댓글 정보
	 */
	public void save(CommentDTO commentDTO) {
		CommentEntity c = CommentEntity.builder()
						.name(commentDTO.getName())
						.comment(commentDTO.getComment())
						.build();
		repository.save(c);
	}

	/**
	 * 댓글 삭제
	 * @param num 삭제할 댓글 번호
	 */
	public void delete(Integer num) {
		repository.deleteById(num);
	}

	/**
	 * 댓글 수정
	 * @param CommentDTO 수정할 댓글 정보
	 */
	public void update(CommentDTO dto) {
		CommentEntity entity = repository.findById(dto.getNum())
				.orElseThrow(() -> new EntityNotFoundException("수정할 댓글이 없습니다."));
		entity.setComment(dto.getComment());
	}

}
