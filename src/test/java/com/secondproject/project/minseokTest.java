package com.secondproject.project;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.secondproject.project.entity.MemberInfoEntity;
import com.secondproject.project.repository.MemberInfoRepository;
import com.secondproject.project.repository.TargerAreaInfoRepository;

@SpringBootTest
@Transactional
public class minseokTest {

    private MemberInfoEntity member;
    private MemberInfoEntity newMember;

    @Autowired MemberInfoRepository mRepo;
    @Autowired TargerAreaInfoRepository tRepo;

    @BeforeEach
    public void beforeEach() {
        newMember = new MemberInfoEntity(null, "testUser2@test.com", "123456", null, null, 500000, "테스트유저2", 0, 1);
        mRepo.save(newMember);
    }

    @Test
    public void 회원등록() {
        MemberInfoEntity member = new MemberInfoEntity(null, "testUser@test.com", "123456", null, null, 500000, "테스트유저", 0, 1);
        mRepo.save(member);

        MemberInfoEntity findMember = mRepo.findByMiSeqAndMiStatus(member.getMiSeq(),1);

        Assertions.assertThat(member.getMiSeq()).isEqualTo(findMember.getMiSeq());
    }

    @Test
    public void 회원정보수정() {
        String originNickname = newMember.getMiNickname();
        String originPwd = newMember.getMiPwd();
        newMember.setMiNickname("수정닉네임");
        newMember.setMiPwd("수정암호12");
        mRepo.save(newMember);

        assertThat(newMember.getMiNickname()).isNotEqualTo(originNickname);
        assertThat(newMember.getMiPwd()).isNotEqualTo(originPwd);
    }

    @Test
    public void 목표금액수정() {
        Integer originTarget = newMember.getMiTargetAmount();
        newMember.setMiTargetAmount(9500);
        mRepo.save(newMember);

        assertThat(newMember.getMiTargetAmount()==originTarget);
    }

    @Test
    public void 회원탈퇴() {
        MemberInfoEntity findMember = mRepo.findByMiSeqAndMiStatus(newMember.getMiSeq(), 1);
        
        findMember.setMiStatus(2);
        mRepo.save(findMember);
        System.out.println(findMember.getMiStatus());
    }

    @Test
    public void 회원조회() {
        MemberInfoEntity findMember = mRepo.findByMiSeqAndMiStatus(newMember.getMiSeq(), 1);
        mRepo.save(findMember);

        assertThat(findMember.getMiSeq()).isEqualTo(newMember.getMiSeq());
    }

    @Test
    public void 회원로그인() {
        MemberInfoEntity loginMember = mRepo.findByMiEmailAndMiPwd("testUser2@test.com", "123456");
        mRepo.save(loginMember);
    }

}
