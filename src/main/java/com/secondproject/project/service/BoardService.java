package com.secondproject.project.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.secondproject.project.entity.BoardImageEntity;
import com.secondproject.project.entity.BoardInfoEntity;
import com.secondproject.project.entity.MemberInfoEntity;
import com.secondproject.project.entity.TargetAreaInfoEntity;
import com.secondproject.project.repository.BoardImageRepository;
import com.secondproject.project.repository.BoardInfoRepository;
import com.secondproject.project.repository.MemberInfoRepository;
import com.secondproject.project.repository.TargerAreaInfoRepository;
import com.secondproject.project.vo.board.BoardUpdateVO;
import com.secondproject.project.vo.board.BoardinsertVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final MemberInfoRepository miRepo;
    private final BoardInfoRepository biRepo;
    private final BoardImageRepository bimgRepo;
    private final TargerAreaInfoRepository tRepo;
    private final FileService fService;

    public Map<String, Object> addBoard(Long memberSeq, BoardinsertVO data, MultipartFile... file){
        Map<String, Object> map = new LinkedHashMap<>();
        System.out.println(data);
        MemberInfoEntity member = miRepo.findById(memberSeq).orElse(null);
        if(member==null){
            map.put("status", false);
            map.put("message", "회원번호 오류입니다.");
            map.put("status", HttpStatus.BAD_REQUEST);
            return map;
        }
        if(data.getDetail()==null || data.getTitle()==null){
            map.put("status", false);
            map.put("message", "모든 필수 값을 입력해주세요");
            map.put("code", HttpStatus.BAD_REQUEST);
            return map;
        }
        TargetAreaInfoEntity target = tRepo.findTarget(member.getMiTargetAmount());
        
        BoardInfoEntity entity = new BoardInfoEntity(null, member, data.getTitle(), data.getDetail(), LocalDateTime.now(), null, 0, target);
        biRepo.save(entity);
        if(file.length!=0){
            List<BoardImageEntity> fileList = new ArrayList<>();
            for(MultipartFile f : file){
                BoardImageEntity img = fService.saveImageFile(f);
                img.setBimgBiSeq(entity);
                fileList.add(img);
            }
            bimgRepo.saveAll(fileList);
        }
        
        map.put("status", true);
        map.put("message", "게시글을 등록하였습니다.");
        map.put("code", HttpStatus.OK);
        
        return map;
    }

    public Map<String, Object> updateBoard(Long memberSeq, Long postSeq, BoardUpdateVO data, MultipartFile... img) {
        Map<String, Object> map = new LinkedHashMap<>();
        MemberInfoEntity member = miRepo.findById(memberSeq).orElse(null);
        if(member==null){
            map.put("status", false);
            map.put("message", "없는 회원번호입니다.");
            map.put("code", HttpStatus.BAD_REQUEST);
            return map;
        }
        BoardInfoEntity boardEntity = biRepo.findById(postSeq).orElse(null);
        if(boardEntity==null){
            map.put("status", false);
            map.put("message", "없는 게시글 번호입니다.");
            map.put("code", HttpStatus.BAD_REQUEST);
            return map;    
        }
        if(boardEntity.getBiMiSeq().getMiSeq()!=member.getMiSeq()){
            map.put("status", false);
            map.put("message", "본인이 작성한 글만 수정할수있습니다.");
            map.put("code", HttpStatus.BAD_REQUEST);
            return map;
        }
        if(img==null && data.getDetail() == null 
        && data.getTitle()==null && data.getImgSeq().size()==0){ //
            map.put("status", false);
            map.put("message", "변경할 내용이 없습니다.");
            map.put("code", HttpStatus.BAD_REQUEST);
            return map;
        }
        if(data.getTitle()!=null){
            boardEntity.setBiTitle(data.getTitle());
        }
        if(data.getDetail()!=null){
            boardEntity.setBiDetail(data.getTitle());
        }
        if(data.getImgSeq().size()!=0){
            List<BoardImageEntity> imgEntity = bimgRepo.findByBimgBiSeqAndBimgSeqIn(boardEntity, data.getImgSeq());
            if(data.getImgSeq().size()!=imgEntity.size()){
                map.put("status", false);
                map.put("message", "잘못된 이미지 번호가 포함되어있습니다. 해당 게시글의 이미지가 아니거나 없는 이미지번호입니다.");
                map.put("code", HttpStatus.BAD_REQUEST);
                return map;
            }
            for(BoardImageEntity file : imgEntity){
                bimgRepo.delete(file);
            }
        }
        if(img!=null){
            List<BoardImageEntity> fileList = new ArrayList<>();
            for(MultipartFile f : img){
                BoardImageEntity file = fService.saveImageFile(f);
                file.setBimgBiSeq(boardEntity);
                fileList.add(file);
            }
            bimgRepo.saveAll(fileList);
        }
        biRepo.save(boardEntity);
        map.put("status", true);
        map.put("message", "게시글 수정 성공");
        map.put("code", HttpStatus.OK);
        return map;
    }

    public Map<String, Object> deleteBoard(Long memberSeq, Long postSeq){
        Map<String, Object> map = new LinkedHashMap<>();
        MemberInfoEntity member = miRepo.findById(memberSeq).orElse(null);
        if(member==null){
            map.put("status", false);
            map.put("message", "없는 회원번호입니다.");
            map.put("code", HttpStatus.BAD_REQUEST);
            return map;
        }
        BoardInfoEntity boardEntity = biRepo.findById(postSeq).orElse(null);
        if(boardEntity==null){
            map.put("status", false);
            map.put("message", "없는 게시글 번호입니다.");
            map.put("code", HttpStatus.BAD_REQUEST);
            return map;    
        }
        if(boardEntity.getBiMiSeq().getMiSeq()!=member.getMiSeq()){
            map.put("status", false);
            map.put("message", "본인이 작성한 글만 수정할수있습니다.");
            map.put("code", HttpStatus.BAD_REQUEST);
            return map;
        }
        List<BoardImageEntity> imgs = bimgRepo.findByBimgBiSeq(boardEntity);
        if(imgs.size()!=0){
            bimgRepo.deleteAllInBatch(imgs);
        }
        biRepo.delete(boardEntity);
        map.put("status", true);
        map.put("message", "게시글을 삭제했습니다.");
        map.put("code", HttpStatus.OK);
        return map;
    }
}
