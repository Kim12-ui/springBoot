package com.dsa.web5.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.dsa.web5.dto.BoardDTO;
import com.dsa.web5.dto.ReplyDTO;

import jakarta.servlet.http.HttpServletResponse;

public interface BoardService {

	void write(BoardDTO boardDTO, String uploadPath, MultipartFile upload) throws IOException;

	List<BoardDTO> selectAllList();

	BoardDTO getBoard(int boardNum);

	void download(Integer boardNum, HttpServletResponse response, String uploadPath);

	void setLike(Integer boardNum);

	BoardDTO getBoardInfo(int boardNum);

	void update(BoardDTO boardDTO, String username, String uploadPath, MultipartFile upload) throws Exception;

	void delete(int boardNum, String username, String uploadPath) throws Exception;

	void replyWrite(ReplyDTO replyDTO);

	void replyDelete(Integer replyNum, String username);

	Page<BoardDTO> getList(int page, int pageSize, String searchType, String searchWord);

}
