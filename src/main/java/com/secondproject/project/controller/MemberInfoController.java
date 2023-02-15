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
import com.secondproject.project.vo.MemberAddVO;
import com.secondproject.project.vo.MemberLoginVO;
import com.secondproject.project.vo.MemberDeleteVO;
import com.secondproject.project.vo.UpdateMemberVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Tag(description="회원관련 기능", name="회원")
public class MemberInfoController {
    private final MemberInfoService memberInfoService;
    
    @Operation(summary = "회원 가입", description ="회원 가입합니다.")
    @PostMapping("/join")
    public ResponseEntity<Object> memberJoin(
        @Parameter(description = "가입할 회원 정보") @RequestBody MemberAddVO data) {
        Map<String, Object> resultMap = memberInfoService.AddMember(data);
        return new ResponseEntity<Object>(resultMap, (HttpStatus)resultMap.get("code"));
    }
    
    @Operation(summary = "회원 조회", description ="회원을 조회합니다.")
    @GetMapping("/check/{member}")
    public ResponseEntity<Object> CheckMember(
        @Parameter(description = "조회할 회원 번호") @PathVariable Long member) throws Exception {
        Map<String, Object> resultMap = memberInfoService.CheckMember(member);
        return new ResponseEntity<Object>(resultMap, (HttpStatus)resultMap.get("code"));
    }

    @Operation(summary = "회원 로그인", description ="로그인 합니다.")
    @PostMapping("/login") 
    public ResponseEntity<Object> memberLogin(
        @Parameter(description = "입력할 로그인 정보") @RequestBody MemberLoginVO data) throws Exception{
        Map<String, Object> resultMap = memberInfoService.LoginMember(data);
        return new ResponseEntity<Object>(resultMap, (HttpStatus) resultMap.get("code"));
    }

    // @PostMapping("/logout")
    // public ResponseEntity<Object> memberLogout(HttpSession session) {
    //     Map<String, Object> resultMap = memberInfoService.LogoutMember(session);
    //     return new ResponseEntity<Object>(resultMap, (HttpStatus) resultMap.get("code"));
    // }

    @Operation(summary = "회원 탈퇴", description ="회원을 탈퇴합니다.")
    @PostMapping("/delete")
    public ResponseEntity<Object> memberDelete(
        @Parameter(description = "탈퇴할 회원 번호") @RequestParam Long miSeq) throws Exception{
        Map<String, Object> resultMap = memberInfoService.DeleteMember(miSeq);
        return new ResponseEntity<Object>(resultMap, (HttpStatus) resultMap.get("code"));
    }
    // 총 회원수 조회 (필요시사용)
    // @GetMapping("/list")
    // public ResponseEntity<Object> CcountMember(Long data) {
        // Map<String, Object> resultMap = memberInfoService.CountMember(data);
        // return new ResponseEntity<Object>(resultMap, (HttpStatus) resultMap.get("code"));
    // }

    @Operation(summary = "회원 정보 수정", description ="회원 정보를 수정합니다.")
    @PostMapping("/update/{type}/{member}")
    public ResponseEntity<Object> UpdateMember(
        @Parameter(description = "수정할 회원 정보") @RequestBody UpdateMemberVO data2, 
        @Parameter(description = "수정할 회원 정보 내용(nickname, pwd)")@PathVariable String type, 
        @Parameter(description = "수정할 회원 번호")@PathVariable Long member) throws Exception{
        Map<String, Object> resultMap = memberInfoService.UpdateMember(data2, type, member);
        return new ResponseEntity<Object>(resultMap, (HttpStatus) resultMap.get("code"));
    }

    @Operation(summary = "회원 목표금액", description ="목표금액을 수정합니다.")
    @PostMapping("/updatemoney/{member}")
    public ResponseEntity<Object> UpdateMemberMoney(
        @Parameter(description = "수정할 목표금액") @RequestBody MemberDeleteVO data, 
        @Parameter(description = "수정할 회원 정보") @PathVariable Long member) throws Exception{
        Map<String, Object> resultMap = memberInfoService.UpdateMemberMoney(data,member);
        return new ResponseEntity<Object>(resultMap, (HttpStatus) resultMap.get("code"));
    }
}
