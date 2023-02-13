package com.secondproject.project.service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.secondproject.project.entity.MemberInfoEntity;
import com.secondproject.project.repository.MemberInfoRepository;
import com.secondproject.project.vo.MemberAddVO;
import com.secondproject.utilities.AESAlgorithm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberInfoService {
    private final MemberInfoRepository memberInfoRepository;

    public Map<String, Object> addMember(MemberAddVO data) {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();    
        Boolean loginStatus = true;
        String replaceEmail = data.getMiEmail().replaceAll(" ", "");
        String replacePwd = data.getMiPwd().replaceAll(" ", "");
        String replaceNickname = data.getMiNickname().replaceAll(" ", "");

        if(replaceEmail.length() == 0) {
            loginStatus = false;
            resultMap.put("message", "이메일은 공백을 입력할 수 없습니다.");
        }
        else if(replacePwd.length() == 0) {
            loginStatus = false;
            resultMap.put("message", "비밀번호는 공백을 입력할 수 없습니다."    );
        }
        else if(replaceNickname.length() == 0) {
            loginStatus = false;
            resultMap.put("message", "닉네임은 공백을 입력할 수 없습니다.");
        }
        if(!loginStatus) {
            resultMap.put("loginStatus", false);
            resultMap.put("code", HttpStatus.BAD_REQUEST);
            return resultMap;
        }

        if(memberInfoRepository.countByMiEmail(replaceEmail) == 1) {
            loginStatus = false;
            resultMap.put("message", replaceEmail+"은/는 이미 등록된 이메일입니다");
        }
        else if(memberInfoRepository.countByMiNickname(replaceNickname) == 1) {
            loginStatus = false;
            resultMap.put("message", replaceNickname+"은/는 이미 등록된 닉네임입니다.");
        }
        if(!loginStatus) {
            resultMap.put("loginStatus", false);
            resultMap.put("code", HttpStatus.BAD_REQUEST);
            return resultMap;
        }
        
        String pwd_pattern = "^[a-zA-Z0-9`~!@#$%^&*()-_=+]{6,20}$";
        String email_pattern = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";        
        String nikcname_pattern = "^[0-9a-zA-Zㄱ-ㅎ가-힣]*$";
        Pattern a = Pattern.compile(email_pattern);
        Pattern b = Pattern.compile(pwd_pattern);
        Pattern c = Pattern.compile(nikcname_pattern);

        if(!a.matcher(replaceEmail).matches()) {
            loginStatus = false;
            resultMap.put("message", "잘못된 이메일 입력방식입니다.");
        }
        else if(!b.matcher(replacePwd).matches()) {
            loginStatus = false;
            resultMap.put("message", "비밀번호는 영문자,특수문자,숫자 포함으로 입력해주세요.(6자이상)");
        }
        else if(!data.getMiCheckPwd().equals(replacePwd)) {
            loginStatus = false;
            resultMap.put("message", "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }
        else if(!c.matcher(replaceNickname).matches()) {
            loginStatus = false;
            resultMap.put("message", "닉네임은 한글,영문,숫자만 가능합니다.");
        }
        if(!loginStatus) {
            resultMap.put("loginStatus", "false");
            resultMap.put("code", HttpStatus.BAD_REQUEST);
            return resultMap;
        }

        data.setMiEmail(replaceEmail);
        data.setMiNickname(replaceNickname);
        try {
            String encPwd = AESAlgorithm.Encrypt(replacePwd);
            data.setMiPwd(encPwd);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        MemberInfoEntity entity = new MemberInfoEntity(data);
        memberInfoRepository.save(entity);
        resultMap.put("loginStatus", true);
        resultMap.put("message", "회원가입이 완료되었습니다.");
        resultMap.put("code", HttpStatus.CREATED);
        return resultMap;
    }

}

