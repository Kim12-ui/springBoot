package com.dsa.web5.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.dsa.web5.dto.BoardDTO;

public interface BoardService {

	void write(BoardDTO boardDTO, String uploadPath, MultipartFile upload) throws IOException;

	List<BoardDTO> selectAllList();

	BoardDTO getBoard(int boardNum);

}
