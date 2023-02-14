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
import com.secondproject.project.vo.UpdateMemberVO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberInfoController {
    private final MemberInfoService memberInfoService;

    @PostMapping("/join")
    public ResponseEntity<Object> memberJoin(@RequestBody MemberAddVO data) {
        Map<String, Object> resultMap = memberInfoService.addMember(data);
        return new ResponseEntity<Object>(resultMap, (HttpStatus)resultMap.get("code"));
    }

    @PostMapping("/login") 
    public ResponseEntity<Object> memberLogin(@RequestBody MemberLoginVO data, HttpSession session) throws Exception{
        Map<String, Object> resultMap = memberInfoService.LoginMember(data, session);
        return new ResponseEntity<Object>(resultMap, (HttpStatus) resultMap.get("code"));
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> memberLogout(HttpSession session) {
        Map<String, Object> resultMap = memberInfoService.LogoutMember(session);
        return new ResponseEntity<Object>(resultMap, (HttpStatus) resultMap.get("code"));
    }

    @PostMapping("/delete")
    public ResponseEntity<Object> memberUpdate(@RequestParam Long miSeq, HttpSession session) throws Exception{
        Map<String, Object> resultMap = memberInfoService.DeleteMember(miSeq, session);
        return new ResponseEntity<Object>(resultMap, (HttpStatus) resultMap.get("code"));
    }
    // 총 회원수 조회 (필요시사용)
    // @GetMapping("/list")
    // public ResponseEntity<Object> CcountMember(Long data) {
        // Map<String, Object> resultMap = memberInfoService.CountMember(data);
        // return new ResponseEntity<Object>(resultMap, (HttpStatus) resultMap.get("code"));
    // }

    @PostMapping("/update/{type}")
    public ResponseEntity<Object> UpdateMember(@RequestBody UpdateMemberVO data2, @PathVariable String type, HttpSession session) throws Exception{
        Map<String, Object> resultMap = memberInfoService.UpdateMember(data2, type, session);
        return new ResponseEntity<Object>(resultMap, (HttpStatus) resultMap.get("code"));
    }

    @PostMapping("/edit/{type}")
    public ResponseEntity<Object> UpdateMemberMoney(@RequestBody UpdateMemberVO data2, @PathVariable String type, HttpSession session) throws Exception{
        Map<String, Object> resultMap = memberInfoService.UpdateMemberMoney(data2, type, session);
        return new ResponseEntity<Object>(resultMap, (HttpStatus) resultMap.get("code"));
    }
}
