package com.secondproject.project.controller;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.secondproject.project.service.BoardService;
import com.secondproject.project.vo.MapVO;
import com.secondproject.project.vo.board.BoardDetailShowVO;
import com.secondproject.project.vo.board.BoardShowVO;
import com.secondproject.project.vo.board.BoardUpdateVO;
import com.secondproject.project.vo.board.BoardinsertVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
@Tag(description="게시판관련 기능", name="게시판")
public class BoardController {
    private final BoardService bService;

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "게시글 등록 성공", content = @Content(schema = @Schema(implementation = MapVO.class))),
        @ApiResponse(responseCode = "400", description = "회원번호 오류 또는 필수값 누락", content = @Content(schema = @Schema(implementation = MapVO.class))) })
    @Operation(summary = "게시글 등록", description ="게시글을 등록합니다. (form-data로 보내주세요)")
    @PostMapping(value = "/add/{seq}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MapVO> saveBoard(
        @Parameter(description = "회원 번호") @PathVariable Long seq,
        @Parameter(description = "글 제목") String title,
        @Parameter(description = "글 내용") String detail,
        // @Parameter(description = "등록할 게시글 정보") BoardinsertVO data,
        @Parameter(description = "게시글에 첨부할 파일(null가능, 같은 변수이름으로 여러개 등록 가능합니다.)") @Nullable MultipartFile... img //폼데이터로 받는 방법임
    ){
        BoardinsertVO data = new BoardinsertVO(title, detail);
        MapVO map = bService.addBoard(seq, data, img);

        return new ResponseEntity<>(map, map.getCode());
    }
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "게시글 수정 성공", content = @Content(schema = @Schema(implementation = MapVO.class))),
        @ApiResponse(responseCode = "400", description = "회원번호 오류 or 게시글번호 오류 or 삭제이미지번호 오류", content = @Content(schema = @Schema(implementation = MapVO.class))), 
        @ApiResponse(responseCode = "403", description = "본인이 작성한 글이 아니거나 변경할 내용이 없음", content = @Content(schema = @Schema(implementation = MapVO.class))) })
    @Operation(summary = "게시글 수정", description ="게시글을 수정합니다. (form-data로 보내주세요)")
    @PostMapping("/update/{member}/{post}")
    public ResponseEntity<Object> updateBoard(
        @Parameter(description = "회원번호") @PathVariable Long member,
        @Parameter(description = "수정할 게시글 번호") @PathVariable Long post,
        @Parameter(description = "수정할 내용") @Nullable BoardUpdateVO data,
        @Parameter(description = "게시글에 추가로 첨부할 파일(null가능)") @Nullable MultipartFile... img //폼데이터로 받는 방법임
    ){
        Map<String, Object> map = bService.updateBoard(member, post, data, img);
        return new ResponseEntity<>(map, (HttpStatus)map.get("code"));
    }
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "게시글 삭제 성공", content = @Content(schema = @Schema(implementation = MapVO.class))),
        @ApiResponse(responseCode = "400", description = "회원번호 오류 or 게시글번호 오류", content = @Content(schema = @Schema(implementation = MapVO.class))),
        @ApiResponse(responseCode = "403", description = "본인이 작성한 글 아님", content = @Content(schema = @Schema(implementation = MapVO.class))) })
    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/delete/{member}/{post}")
    public ResponseEntity<Object> deleteBoard(
        @Parameter(description = "회원번호") @PathVariable Long member,
        @Parameter(description = "삭제할 게시글 번호") @PathVariable Long post
    ){
        Map<String, Object> map = bService.deleteBoard(member, post);
        return new ResponseEntity<>(map, (HttpStatus)map.get("code"));
    }
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "게시글 상세조회 성공", content = @Content(schema = @Schema(implementation = BoardDetailShowVO.class))),
        @ApiResponse(responseCode = "403", description = "조회할수없는 구간의 게시글", content = @Content(schema = @Schema(implementation = MapVO.class))),
        @ApiResponse(responseCode = "400", description = "회원번호 오류 게시글번호 오류", content = @Content(schema = @Schema(implementation = MapVO.class))) })
    @Operation(summary = "게시글 상세 조회")
    @GetMapping("/show/detail/{member}/{post}")
    public ResponseEntity<Object> getBoard(
        @Parameter(description = "회원번호") @PathVariable Long member,
        @Parameter(description = "조회할 게시글 번호") @PathVariable Long post
    ){
        Map<String, Object> map = bService.getDetailBoard(member, post);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @Operation(summary = "게시글 목록 조회", description = "게시글 목록 조회입니다. board/show/list/1?page=1 처럼 page만 들어와도됩니다. page 값이 들어오지않으면 1페이지가 조회됩니다.",
    parameters = {
        @Parameter(in = ParameterIn.QUERY
                            , description = "페이지번호(0부터 시작), 입력안하면 0페이지 조회"
                            , name = "page"
                            , content = @Content(schema = @Schema(type = "integer", defaultValue = "0"))),
        @Parameter(in = ParameterIn.QUERY
                            , description = "입력안해도됨. 기본 한 페이지 당 10개 씩"
                            , name = "size"),
        @Parameter(in = ParameterIn.QUERY
                            , description = "입력안해도됨. 기본 최신순정렬"
                            , name = "sort")
            })
    @GetMapping("/show/list/{member}")
    public ResponseEntity<Page<BoardShowVO>> getBoard(
        @Parameter(description = "회원번호") @PathVariable Long member,
        @Parameter(hidden=true) @PageableDefault(size=10, sort="biRegDt",direction = Sort.Direction.ASC) Pageable page
    ){
        Map<String, Object> map = bService.getBoard(member, page);
        if(!(boolean)map.get("status")){
            return new ResponseEntity<>(null, HttpStatus.OK);        
        }else{
            return new ResponseEntity<Page<BoardShowVO>>((Page<BoardShowVO>)map.get("data"), HttpStatus.OK);
        }
    }
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "좋아요/싫어요 성공", content = @Content(schema = @Schema(implementation = MapVO.class))),
        @ApiResponse(responseCode = "400", description = "회원번호 오류 or 게시글번호 오류 or 타입오류(like/unlike)", content = @Content(schema = @Schema(implementation = MapVO.class))),
        @ApiResponse(responseCode = "202", description = "이미 좋아요/싫어요한 게시글", content = @Content(schema = @Schema(implementation = MapVO.class)))
    })
    @Operation(summary = "게시글 좋아요/싫어요", description = "게시글의 좋아요/싫어요 기능입니다. type이 like면 좋아요/ unlike면 싫어요입니다.")
    @PutMapping("/detail/{type}/{member}/{post}")
    public ResponseEntity<Object> likeAndUnlike(
        @Parameter(description = "타입") @PathVariable String type, 
        @Parameter(description = "회원번호") @PathVariable Long member, 
        @Parameter(description = "게시글번호") @PathVariable Long post
        ){
            Map<String, Object> map = bService.likeAndUnlike(type, member, post);
            
            return new ResponseEntity<>(map, HttpStatus.OK);        
        }
    @GetMapping("/search/list/{member}")
    @Operation(summary = "게시글 검색", description = "게시글의 제목으로 검색하는 기능입니다.",
    parameters = {
        @Parameter(in = ParameterIn.QUERY
                            , description = "페이지번호(0부터 시작), 입력안하면 0페이지 조회"
                            , name = "page"
                            , content = @Content(schema = @Schema(type = "integer", defaultValue = "0"))),
        @Parameter(in = ParameterIn.QUERY
                            , description = "입력안해도됨. 기본 한 페이지 당 10개 씩"
                            , name = "size"),
        @Parameter(in = ParameterIn.QUERY
                            , description = "입력안해도됨. 기본 최신순정렬"
                            , name = "sort")
            })
    public ResponseEntity<Page<BoardShowVO>> getSearchBoard(
        @Parameter(description = "회원번호") @PathVariable Long member,
        @Parameter(hidden=true) @PageableDefault(size=10, sort="biRegDt",direction = Sort.Direction.ASC) Pageable page,
        @Parameter(description = "검색어") @RequestParam String keyword
    ){
        Map<String, Object> map = bService.searchBoard(page, keyword, member);
        if(!(boolean)map.get("status")){
            return new ResponseEntity<>(null, HttpStatus.OK);        
        }else{
            return new ResponseEntity<Page<BoardShowVO>>((Page<BoardShowVO>)map.get("data"), HttpStatus.OK);
        }
    }
}
