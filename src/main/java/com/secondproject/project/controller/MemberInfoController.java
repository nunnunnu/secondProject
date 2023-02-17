package com.secondproject.project.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.secondproject.project.service.MemberInfoService;
import com.secondproject.project.vo.MapVO;
import com.secondproject.project.vo.MemberAddVO;
import com.secondproject.project.vo.MemberLoginVO;
import com.secondproject.project.vo.MemberMoneyUpdateVO;
import com.secondproject.project.vo.MemberLoginInfoVO;
import com.secondproject.project.vo.UpdateMemberVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Tag(description="회원관련 기능", name="회원")
public class MemberInfoController {
    private final MemberInfoService memberInfoService;
    
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "가입성공", content = @Content(schema = @Schema(implementation = MapVO.class))),
        @ApiResponse(responseCode = "404", description = "가입실패(주소 값 오류)", content = @Content(schema = @Schema(implementation = MapVO.class))),
        @ApiResponse(responseCode = "400", description = "가입실패(닉네임,이메일 중복or공백/비밀번호 양식 불일치/비밀번호 확인 불일치)", content = @Content(schema = @Schema(implementation = MapVO.class)))
    })
    @Operation(summary = "회원 가입", description ="회원 가입합니다.")
    @PostMapping("/join")
    public ResponseEntity<Object> memberJoin(
        @Parameter(description = "가입할 회원 정보") @RequestBody MemberAddVO data) {
        Map<String, Object> resultMap = memberInfoService.AddMember(data);
        return new ResponseEntity<Object>(resultMap, (HttpStatus)resultMap.get("code"));
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "조회성공", content = @Content(schema = @Schema(implementation = MemberLoginInfoVO.class))),
        @ApiResponse(responseCode = "404", description = "조회실패(주소 값 오류)", content = @Content(schema = @Schema(implementation = MapVO.class))),
        @ApiResponse(responseCode = "400", description = "조회실패(회원번호 값 입력 오류)", content = @Content(schema = @Schema(implementation = MapVO.class)))
    })
    @Operation(summary = "회원 조회", description ="회원을 조회합니다.")
    @GetMapping("/check/{member}")
    public ResponseEntity<Object> CheckMember(
        @Parameter(description = "조회할 회원 번호") @PathVariable Long member) throws Exception {
        Map<String, Object> resultMap = memberInfoService.CheckMember(member);
        return new ResponseEntity<Object>(resultMap, (HttpStatus)resultMap.get("code"));
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "202", description = "로그인성공", content = @Content(schema = @Schema(implementation = MemberLoginInfoVO.class))),
        @ApiResponse(responseCode = "404", description = "로그인실패(주소 값 오류)", content = @Content(schema = @Schema(implementation = MapVO.class))),
        @ApiResponse(responseCode = "400", description = "로그인실패(아이디,비밀번호 오류)", content = @Content(schema = @Schema(implementation = MapVO.class)))
    })
    @Operation(summary = "회원 로그인", description ="로그인 합니다.")
    @PostMapping("/login") 
    public ResponseEntity<Object> memberLogin(
        @Parameter(description = "입력할 로그인 정보") @RequestBody MemberLoginVO data) throws Exception{
        Map<String, Object> resultMap = memberInfoService.LoginMember(data);
        return new ResponseEntity<Object>(resultMap, (HttpStatus) resultMap.get("code"));
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "202", description = "탈퇴성공", content = @Content(schema = @Schema(implementation = MapVO.class))),
        @ApiResponse(responseCode = "404", description = "탈퇴실패(주소 값 오류)", content = @Content(schema = @Schema(implementation = MapVO.class))),
        @ApiResponse(responseCode = "400", description = "탈퇴실패(이미 탈퇴한 회원이거나,존재하지 않는 회원번호 입력)", content = @Content(schema = @Schema(implementation = MapVO.class)))
    })
    @Operation(summary = "회원 탈퇴", description ="회원을 탈퇴합니다.")
    @PostMapping("/delete")
    public ResponseEntity<Object> memberDelete(
        @Parameter(description = "탈퇴할 회원 번호") @RequestParam Long miSeq) throws Exception{
        Map<String, Object> resultMap = memberInfoService.DeleteMember(miSeq);
        return new ResponseEntity<Object>(resultMap, (HttpStatus) resultMap.get("code"));
    }
    
    @ApiResponses(value = {
        @ApiResponse(responseCode = "202", description = "수정성공", content = @Content(schema = @Schema(implementation = MapVO.class))),
        @ApiResponse(responseCode = "404", description = "수정실패(주소 값 오류)", content = @Content(schema = @Schema(implementation = MapVO.class))),
        @ApiResponse(responseCode = "400", description = "수정실패(존재하지 않는 회원번호 입력,기존 비밀번호 틀림,기존 닉네임과 동일or중복)", content = @Content(schema = @Schema(implementation = MapVO.class)))
    })
    @Operation(summary = "회원 정보 수정", description ="회원 정보를 수정합니다.")
    @PostMapping("/update/{type}/{member}")
    public ResponseEntity<Object> UpdateMember(
        @Parameter(description = "수정할 회원 정보") @RequestBody UpdateMemberVO data2, 
        @Parameter(description = "수정할 회원 정보 내용(nickname, pwd)")@PathVariable String type, 
        @Parameter(description = "수정할 회원 번호")@PathVariable Long member) throws Exception{
        Map<String, Object> resultMap = memberInfoService.UpdateMember(data2, type, member);
        return new ResponseEntity<Object>(resultMap, (HttpStatus) resultMap.get("code"));
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "202", description = "목표금액 수정 성공", content = @Content(schema = @Schema(implementation = MapVO.class))),
        @ApiResponse(responseCode = "404", description = "목표금액 수정실패(주소 값 오류)", content = @Content(schema = @Schema(implementation = MapVO.class))),
        @ApiResponse(responseCode = "400", description = "목표금액 수정실패(존재하지 않는 회원번호 입력,음수 값 입력)", content = @Content(schema = @Schema(implementation = MapVO.class)))
    })
    @Operation(summary = "회원 목표금액 수정", description ="목표금액을 수정합니다.")
    @PostMapping("/updatemoney/{member}")
    public ResponseEntity<Object> UpdateMemberMoney(
        @Parameter(description = "수정할 목표금액") @RequestBody MemberMoneyUpdateVO data, 
        @Parameter(description = "수정할 회원 정보") @PathVariable Long member) throws Exception{
        Map<String, Object> resultMap = memberInfoService.UpdateMemberMoney(data, member);
        return new ResponseEntity<Object>(resultMap, (HttpStatus) resultMap.get("code"));
    }
}
