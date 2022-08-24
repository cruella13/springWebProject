package com.br.spring.member.model.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.spring.member.model.dao.MemberDao;
import com.br.spring.member.model.vo.Member;

@Service 
public class MemberServiceImpl implements MemberService {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Autowired
	private MemberDao mDao;
	
	@Override
	public Member loginMember(Member m) {
		
		Member loginUser = mDao.loginMember(sqlSession, m);
		return loginUser;
			
	}

	@Override
	public int insertMember(Member m) {
		int result = mDao.insertMember(sqlSession, m);
		return result;
	}

	@Override
	public int updateMember(Member m) {
		return mDao.updateMember(sqlSession, m);
	}

	@Override
	public int deleteMember(String userId) {
		return mDao.deleteMember(sqlSession, userId);
		
	}

	@Override
	public int idCheck(String userId) {
		return 0;
	}

	@Override
	public int uploadProfileImg(Member m) {
		return 0;
	}
	

}
