package com.secondproject.project;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.secondproject.project.entity.BoardImageEntity;
import com.secondproject.project.repository.BoardImageRepository;
import com.secondproject.project.repository.BoardInfoRepository;
import com.secondproject.project.repository.CategoryInfoRepository;
import com.secondproject.project.repository.CommentInfoRepository;
import com.secondproject.project.repository.CommentLikesRepository;
import com.secondproject.project.repository.ExpensesDetailRepository;
import com.secondproject.project.repository.MemberInfoRepository;
import com.secondproject.project.repository.TargerAreaInfoRepository;

@SpringBootTest
class ProjectApplicationTests {

	@Autowired BoardImageRepository bimagRepo;
	@Autowired BoardInfoRepository bRepo;
	@Autowired CategoryInfoRepository cateRepo;
	@Autowired CommentInfoRepository ciRepo;
	@Autowired CommentLikesRepository clRepo;
	@Autowired ExpensesDetailRepository eRepo;
	@Autowired MemberInfoRepository mRepo;
	@Autowired TargerAreaInfoRepository tRepo;

	@Test
	void 엔터티테스트() {
		System.out.println(bRepo.findAll().get(0).getBiSeq());
		System.out.println(bimagRepo.findAll().get(0).getBimgSeq());
		System.out.println(cateRepo.findAll().get(0).getCateSeq());
		System.out.println(ciRepo.findAll().get(0).getCiSeq());
		System.out.println(clRepo.findAll().get(0).getClSeq());
		System.out.println(eRepo.findAll().get(0).getEdSeq());
		System.out.println(mRepo.findAll().get(0).getMiSeq());
		System.out.println(tRepo.findAll().get(0).getTaiSeq());
	}

}
