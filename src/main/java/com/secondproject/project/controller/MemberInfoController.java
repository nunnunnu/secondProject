package com.secondproject.project.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.secondproject.project.service.MemberInfoService;
import com.secondproject.project.vo.MemberAddVO;

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
}
