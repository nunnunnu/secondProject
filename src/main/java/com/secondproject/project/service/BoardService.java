package com.secondproject.project.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.secondproject.project.entity.BoardImageEntity;
import com.secondproject.project.entity.BoardInfoEntity;
import com.secondproject.project.entity.CommentLikesEntity;
import com.secondproject.project.entity.MemberInfoEntity;
import com.secondproject.project.entity.TargetAreaInfoEntity;
import com.secondproject.project.repository.BoardImageRepository;
import com.secondproject.project.repository.BoardInfoRepository;
import com.secondproject.project.repository.CommentInfoRepository;
import com.secondproject.project.repository.CommentLikesRepository;
import com.secondproject.project.repository.MemberInfoRepository;
import com.secondproject.project.repository.TargerAreaInfoRepository;
import com.secondproject.project.vo.MapVO;
import com.secondproject.project.vo.board.BoardDetailShowVO;
import com.secondproject.project.vo.board.BoardShowVO;
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
    private final CommentLikesRepository clRepo;
    private final CommentInfoRepository ciRepo;

    public MapVO addBoard(Long memberSeq, BoardinsertVO data, MultipartFile... file){
        MapVO map = new MapVO();
        System.out.println(data);
        MemberInfoEntity member = miRepo.findById(memberSeq).orElse(null);
        if(member==null){
            map.setStatus(false);
            map.setMessage("회원번호 오류입니다.");
            map.setCode(HttpStatus.BAD_REQUEST);
            return map;
        }
        if(data.getDetail()==null || data.getTitle()==null){
            map.setStatus(false);
            map.setMessage("모든 필수 값을 입력해주세요");
            map.setCode(HttpStatus.BAD_REQUEST);
            return map;
        }
        TargetAreaInfoEntity target = tRepo.findTarget(member.getMiTargetAmount());
        
        List<BoardImageEntity> fileList = new ArrayList<>();
        BoardInfoEntity entity = BoardInfoEntity.builder()
                                .biMiSeq(member)
                                .biTitle(data.getTitle())
                                .biDetail(data.getDetail())
                                .biRegDt(LocalDateTime.now())
                                .biTaiSeq(target)
                                .build();
        // BoardInfoEntity entity = new BoardInfoEntity(null, member, data.getTitle(), data.getDetail(), LocalDateTime.now(), null, 0, target);
        biRepo.save(entity);
        if(file!=null && file.length!=0){
            for(MultipartFile f : file){
                BoardImageEntity img = fService.saveImageFile(f);
                img.setBimgBiSeq(entity);
                fileList.add(img);
            }
        }
        bimgRepo.saveAll(fileList);
        
        map.setStatus(true);
        map.setMessage("게시글을 등록하였습니다.");
        map.setCode(HttpStatus.OK);
        
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
        BoardInfoEntity boardEntity = biRepo.findByBiSeqAndBiStatus(postSeq, 0);
        if(boardEntity==null){
            map.put("status", false);
            map.put("message", "없는 게시글 번호입니다.");
            map.put("code", HttpStatus.BAD_REQUEST);
            return map;    
        }
        if(boardEntity.getBiMiSeq().getMiSeq()!=member.getMiSeq()){
            map.put("status", false);
            map.put("message", "본인이 작성한 글만 수정할수있습니다.");
            map.put("code", HttpStatus.FORBIDDEN);
            return map;
        }
        if(img==null && data.getDetail() == null 
        && data.getTitle()==null && data.getImgSeq().size()==0){ //
            map.put("status", false);
            map.put("message", "변경할 내용이 없습니다.");
            map.put("code", HttpStatus.FORBIDDEN);
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
        boardEntity.setBiEditDt(LocalDateTime.now());
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
        BoardInfoEntity boardEntity = biRepo.findByBiSeqAndBiStatus(postSeq,0);
        if(boardEntity==null){
            map.put("status", false);
            map.put("message", "없는 게시글 번호입니다.");
            map.put("code", HttpStatus.BAD_REQUEST);
            return map;    
        }
        if(boardEntity.getBiMiSeq().getMiSeq()!=member.getMiSeq()){
            map.put("status", false);
            map.put("message", "본인이 작성한 글만 수정할수있습니다.");
            map.put("code", HttpStatus.FORBIDDEN);
            return map;
        }
        bimgRepo.boardDeleteQuery(boardEntity);
        ciRepo.commentDeleteQuery(boardEntity);
        boardEntity.setBiStatus(1);
        biRepo.save(boardEntity);
        map.put("status", true);
        map.put("message", "게시글을 삭제했습니다.");
        map.put("code", HttpStatus.OK);
        return map;
    }
    public Map<String, Object> getDetailBoard(Long memberSeq, Long postSeq){
        Map<String, Object> map = new LinkedHashMap<>();
        MemberInfoEntity member = miRepo.findById(memberSeq).orElse(null);
        if(member==null){
            map.put("status", false);
            map.put("message", "없는 회원번호입니다.");
            map.put("code", HttpStatus.BAD_REQUEST);
            return map;
        }
        BoardInfoEntity board = biRepo.findByBiSeqAndBiStatus(postSeq, 0);
        if(board==null){
            map.put("status", false);
            map.put("message", "없는 게시글 번호입니다.");
            map.put("code", HttpStatus.BAD_REQUEST);
            return map;    
        }
        TargetAreaInfoEntity target = tRepo.findTarget(member.getMiTargetAmount());
        if((board.getBiTaiSeq()!=null && target!=null && board.getBiTaiSeq().getTaiSeq() != target.getTaiSeq())
            || (board.getBiTaiSeq()==null && target!=null)
        ){
            map.put("status", false);
            map.put("message", "조회할 수 없는 구간의 게시글입니다.");
            map.put("code", HttpStatus.FORBIDDEN);
            return map;        
        }
        
        BoardDetailShowVO bVo = new BoardDetailShowVO(board);
        bVo.setLikes(clRepo.countByClStatusAndClBiSeq(0, board));
        bVo.setUnLikes(clRepo.countByClStatusAndClBiSeq(1, board));

        board.upView();

        biRepo.save(board);
        
        // map.put("status", true);
        // map.put("message", "조회 성공했습니다.");
        // map.put("code", HttpStatus.OK);
        map.put("data", bVo);

        return map;
    }
    //목록조회
    public Map<String, Object> getBoard(Long memberSeq, Pageable page){
        Map<String, Object> map = new LinkedHashMap<>();
        MemberInfoEntity member = miRepo.findById(memberSeq).orElse(null);
        if(member==null){
            map.put("status", false);
            map.put("message", "없는 회원번호입니다.");
            map.put("code", HttpStatus.BAD_REQUEST);
            // map.put("data", null);
            return map;
        }
        if(member.getMiTargetAmount()!=null){
            TargetAreaInfoEntity target = tRepo.findTarget(member.getMiTargetAmount());
            Page<BoardInfoEntity> boards = biRepo.findByBiTaiSeqOrderByBiRegDtDesc(target, page);
            Page<BoardShowVO> result = boards.map(b->new BoardShowVO(b, clRepo.countByClStatusAndClBiSeq(0, b)));

            map.put("status", true);
            // map.put("message", "해당 구간의 게시글을 조회했습니다");
            // map.put("code", HttpStatus.OK);
            map.put("data", result);
        }else{
            Page<BoardInfoEntity> boards = biRepo.findByBiStatusOrderByBiRegDtDesc(0,page);
            Page<BoardShowVO> result = boards.map(b->new BoardShowVO(b, clRepo.countByClStatusAndClBiSeq(0, b)));

            map.put("status", true);
            // map.put("message", "모든 게시글을 조회했습니다");
            // map.put("code", HttpStatus.OK);
            map.put("data", result);

        }
        
        return map;
    }

    public Map<String, Object> likeAndUnlike(String type, Long memberSeq, Long postSeq) {
        Map<String, Object> map = new LinkedHashMap<>();
        MemberInfoEntity member = miRepo.findById(memberSeq).orElse(null);
        if(member==null){
            map.put("status", false);
            map.put("message", "없는 회원번호입니다.");
            map.put("code", HttpStatus.BAD_REQUEST);
            return map;
        }
        BoardInfoEntity board = biRepo.findByBiSeqAndBiStatus(postSeq,0);
        if(board==null){
            map.put("status", false);
            map.put("message", "없는 게시글 번호입니다.");
            map.put("code", HttpStatus.BAD_REQUEST);
            return map;    
        }
        if(clRepo.countByClMiSeqAndClBiSeq(member, board)>=1){
            map.put("status", false);
            map.put("message", "이미 좋아요/싫어요한 게시글입니다.");
            map.put("code", HttpStatus.ACCEPTED);
            return map;    
        }
        String kind = "";
        if(type.equals("like")){
            CommentLikesEntity like = new CommentLikesEntity(null, member, board, 0);
            clRepo.save(like);
            kind="좋아요";
        }else if(type.equals("unlike")){
            CommentLikesEntity unlike = new CommentLikesEntity(null, member, board, 1);
            clRepo.save(unlike);
            kind="싫어요";
        }else{
            map.put("status", false);
            map.put("message", "타입이 잘못되었습니다. (like or unlike)");
            map.put("code", HttpStatus.BAD_REQUEST);
            return map;     
        }
        map.put("status", true);
        map.put("message", kind+" 성공");
        map.put("code", HttpStatus.OK);
        return map;     

    }

    //게시글 검색 조회
    public Map<String, Object> searchBoard(Pageable page, String keyword, Long memberSeq){
        Map<String, Object> map = new LinkedHashMap<>();
        if(keyword==null){
            keyword = "";
        }
        MemberInfoEntity member = miRepo.findById(memberSeq).orElse(null);
        if(member==null){
            map.put("status", false);
            map.put("message", "없는 회원번호입니다.");
            map.put("code", HttpStatus.BAD_REQUEST);
            // map.put("data", null);
            return map;
        }
        System.out.println(keyword);
        Page<BoardInfoEntity> boards = null;
        if(member.getMiTargetAmount()!=null){
            TargetAreaInfoEntity target = tRepo.findTarget(member.getMiTargetAmount());
            boards = biRepo.findByBiTaiSeqAndBiTitleContains(target, keyword, page);
        }else{
            boards = biRepo.findByBiTitleContains(keyword, page);
        }
        if(boards.getTotalElements()==0){
            map.put("status", false);
            map.put("message", "해당 검색어에 해당하는 게시글이 없습니다.");
            map.put("code", HttpStatus.NO_CONTENT);
            return map;  
        }
        
        Page<BoardShowVO> result = boards.map(b->new BoardShowVO(b, clRepo.countByClStatusAndClBiSeq(0, b)));
        System.out.println(boards.getTotalElements());
        map.put("status", true);
        map.put("data", result);

        return map;
    }
}
