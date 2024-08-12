package com.dsa.web3.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.dsa.web3.dto.MemberDTO;
import com.dsa.web3.entity.MemberEntity;
import com.dsa.web3.repository.MemberRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional // import jakarta.transaction.Transactional;
public class MemberServiceImpl implements MemberService {
	
	private final MemberRepository mr;
	
	/*
	 * 회원정보 생성 후 DB에 저장
	 * @Param	없음
	 * @Return	없음
	 * */
	@Override
	public void insertData() {
		
		// 일반적인 인스턴스 생성
//		MemberEntity m = new MemberEntity("aaa","111","홍길동",
//				"010-1111-2222","광주무역회관");
		
		// builder
		MemberEntity m = MemberEntity.builder()
						.id("aaa")
						.pw("111")
						.name("홍길동")
						.phone("010-1111-2222")
						.address("광주무역회관")
						.build();
		mr.save(m);
		log.debug("[service-save] memberEntity: {}",m);
	}

	/*
	 * 회원정보 조회
	 * @Param	id : 조회할 아이디
	 * @return  MemberDTO : 조회 결과에 담은 객체
	 * */
	// alt+shift+s,v
	@Override
	public MemberDTO selectData(String id) {
		MemberEntity member = mr.findById(id).orElse(null);
		if (member==null) return null;
		
		log.debug("[service-find] memberEntity : {}",member);
		
		// MemberDTO
		MemberDTO memberDTO = MemberDTO.builder()
								.id(member.getId())
								.pw(member.getPw())
								.name(member.getName())
								.phone(member.getPhone())
								.address(member.getAddress())
								.build();

		return memberDTO;
	}
	
	/*
	 * 회원정보 수정
	 * @param MemberDTO 회원정보를 담은 객체
	 * */
	@Override
	public void updateData(MemberDTO m) {
		// TODO Auto-generated method stub
		try {
			// DB 정보를 조회
			MemberEntity entity = mr.findById(m.getId())
					.orElseThrow(() -> new EntityNotFoundException("없는 ID"));
			
			// MemberDTO의 수정할 정보를 entity에 세팅
			entity.setName(m.getName());
			entity.setAddress(m.getAddress());
			entity.setPw(m.getPw());
			entity.setPhone(m.getPhone());
			
			// entity 저장
			mr.save(entity);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	/*
	 * 회원정보 삭제
	 * @param id 삭제할 아이디
	 * @return 삭제여부 true/false
	 * */
	@Override
	public boolean deleteData(String id) {
		
		// id에 일치하는 레코드가 있는지 없는지를 true / false로 리턴
		boolean result = mr.existsById(id);
		
		if (result) 
			mr.deleteById(id); // id에 일치하는 레코드 삭제
		
		return result;
	}

	/*
	 * 모든 정보 조회
	 * @return 모든 정보를 담은 객체의 리스트
	 * */
	@Override
	public List<MemberDTO> selectAllData() {
		
		List<MemberEntity> entityList = mr.findAll();
		List<MemberDTO> dtoList = new ArrayList<>();
		
		for (MemberEntity entity:entityList) {
			MemberDTO dto = new MemberDTO();
			dto.setId(entity.getId());
			dto.setPw(entity.getPw());
			dto.setName(entity.getName());
			dto.setPhone(entity.getPhone());
			dto.setAddress(entity.getAddress());
			dtoList.add(dto);
		}
		
		return dtoList;
	}

	// 내가 만든 답안 (회원가입)
//	@Override
//	public void joinMember(String id, String pw, String name, String phone, String address) {
//		MemberEntity m = MemberEntity.builder()
//				.id(id)
//				.pw(pw)
//				.name(name)
//				.phone(phone)
//				.address(address)
//				.build();
//		mr.save(m);
//	}
	
	/*
	 * 회원가입 처리
	 * @param MemberDTO (HTML에서 받아온 입력데이터 객체 = 회원가입정보)
	 * */
	@Override
	public void joinData(MemberDTO member) {
		MemberEntity m = MemberEntity.builder()
						.id(member.getId())
						.pw(member.getPw())
						.name(member.getName())
						.phone(member.getPhone())
						.address(member.getAddress())
						.build();
		mr.save(m);
	}
}
