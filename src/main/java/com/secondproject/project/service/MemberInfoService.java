package com.secondproject.project.service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.secondproject.project.entity.MemberInfoEntity;
import com.secondproject.project.repository.MemberInfoRepository;
import com.secondproject.project.vo.MemberAddVO;
import com.secondproject.project.vo.MemberLoginInfoVO;
import com.secondproject.project.vo.MemberLoginVO;
import com.secondproject.project.vo.MemberDeleteVO;
import com.secondproject.project.vo.UpdateMemberVO;
import com.secondproject.utilities.AESAlgorithm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberInfoService {
    private final MemberInfoRepository memberInfoRepository;
    
    // 회원가입
    public Map<String, Object> AddMember(MemberAddVO data) {
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
        
        String pwd_pattern = "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z])(?=.*[A-Z]).{6,12}$";
        String email_pattern = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";        
        String nikcname_pattern = "^[0-9a-zA-Zㄱ-ㅎ가-힣]*.{2,10}$";
        Pattern a = Pattern.compile(email_pattern);
        Pattern b = Pattern.compile(pwd_pattern);
        Pattern c = Pattern.compile(nikcname_pattern);

        if(!a.matcher(replaceEmail).matches()) {
            loginStatus = false;
            resultMap.put("message", "잘못된 이메일 입력방식입니다.");
        }
        else if(!b.matcher(replacePwd).matches()) {
            loginStatus = false;
            resultMap.put("message", "비밀번호는 (영)대,소문자,특수문자,숫자 포함으로 입력해주세요.(6~12자리)");
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

    // 회원정보 조회
    public Map<String, Object> CheckMember(Long member) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

        MemberInfoEntity entity = memberInfoRepository.findById(member).orElse(null);
        MemberLoginInfoVO loginInfo = new MemberLoginInfoVO(entity);

        if(entity == null){
            resultMap.put("status", false);
            resultMap.put("message", "회원정보가 없습니다. 로그인 먼저해주세요.");
            resultMap.put("code", HttpStatus.BAD_REQUEST);
            return resultMap;
        }
        else {
            resultMap.put("Status", true);
            resultMap.put("message", "조회에 성공했습니다.");
            resultMap.put("UserInfo", loginInfo);
            resultMap.put("code", HttpStatus.CREATED);
            return resultMap;
        }
    }

    // 로그인
    public Map<String, Object> LoginMember(MemberLoginVO data) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        String encPwd = AESAlgorithm.Encrypt(data.getMiPwd());
        MemberInfoEntity loginUser = memberInfoRepository.findByMiEmailAndMiPwd(data.getMiEmail(), encPwd);
        MemberLoginInfoVO loginInfo = new MemberLoginInfoVO(loginUser);
        
        if(loginUser == null) {
            resultMap.put("loginStatus", false);
            resultMap.put("message", "아이디 또는 비밀번호 오류입니다.");
            resultMap.put("code", HttpStatus.BAD_REQUEST);
            return resultMap;
        }
        else if(loginInfo.getMiStatus() == 2) {
            resultMap.put("loginStatus", false);
            resultMap.put("message", "탈퇴한 사용자입니다.");
            resultMap.put("code", HttpStatus.BAD_REQUEST);
            return resultMap;
        }
        
        resultMap.put("loginStatus", true);
        resultMap.put("message", "로그인에 성공하였습니다.");
        resultMap.put("code", HttpStatus.ACCEPTED);
        resultMap.put("loginUser", loginInfo);
        return resultMap;
    }

    // 로그아웃
    // public Map<String, Object> LogoutMember(/* HttpSession session */) {
    //     Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
    //     // if (session.getAttribute("loginUser") == null) {
    //     //     resultMap.put("loginStatus", false);
    //     //     resultMap.put("message", "회원정보가 없습니다. 로그인 먼저해주세요.");
    //     //     resultMap.put("code", HttpStatus.BAD_REQUEST);
    //     //     return resultMap;
    //     // }
    //     // session.invalidate();
    //     resultMap.put("loginStatus", true);
    //     resultMap.put("message", "로그아웃되었습니다.");
    //     resultMap.put("code", HttpStatus.ACCEPTED);
    //     return resultMap;
    // }

    // 탈퇴
    public Map<String, Object> DeleteMember(Long miSeq) throws Exception{
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        
        MemberInfoEntity entity = memberInfoRepository.findById(miSeq).orElse(null);
        MemberDeleteVO delMember = new MemberDeleteVO(entity);
        
        if(entity == null){
            resultMap.put("status", false);
            resultMap.put("message", "회원정보가 없습니다. 로그인 먼저해주세요.");
            resultMap.put("code", HttpStatus.BAD_REQUEST);
            return resultMap;
        }
    
        if(delMember.getMiStatus() == 2) {
            resultMap.put("Status", false);
            resultMap.put("message", "이미 탈퇴한 회원입니다.");
            resultMap.put("code", HttpStatus.BAD_REQUEST);
            return resultMap;  
        }
        delMember.setMiStatus(2);
        entity.setMiStatus(delMember.getMiStatus());
        memberInfoRepository.save(entity);
        resultMap.put("Status", "true");
        resultMap.put("message","탈퇴되었습니다.");
        resultMap.put("code", HttpStatus.ACCEPTED);
        return resultMap;
        }
    
    // 총 회원수 조회 (필요시사용)
    // public Map<String, Object> CountMember(Long data) {
        // Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        // data = memberInfoRepository.count();
        // resultMap.put("total_member_num", data);
        // resultMap.put("status", true);
        // resultMap.put("code", HttpStatus.ACCEPTED);
        // return resultMap;
    // }

    // 회원정보 수정
    public Map<String, Object> UpdateMember(UpdateMemberVO data2, String type, Long memberSeq) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

        MemberInfoEntity entity = memberInfoRepository.findById(memberSeq).orElse(null);
        
        if(entity == null){
            resultMap.put("status", false);
            resultMap.put("message", "회원정보가 없습니다.");
            resultMap.put("code", HttpStatus.BAD_REQUEST);
            return resultMap;
        }

        if (type.equals("nickname")) {
            String nickname_pattern = "^[0-9a-zA-Zㄱ-ㅎ가-힣]*.{2,10}$";
            String replaceNickname = data2.getMiNickname().replaceAll(" ", "");
            Pattern k = Pattern.compile(nickname_pattern);
            if (replaceNickname.length() == 0) {
                resultMap.put("status", "false");
                resultMap.put("message", "공백이 있습니다. 다시 확인해주세요.");
                resultMap.put("code", HttpStatus.BAD_REQUEST);
                return resultMap;
            }
            else if (replaceNickname.equals(entity.getMiNickname())) {
                resultMap.put("status", "false");
                resultMap.put("message", "기존 닉네임으로 변경할 수 없습니다.");
                resultMap.put("code", HttpStatus.BAD_REQUEST);
                return resultMap;
            }
            else if (memberInfoRepository.countByMiNickname(replaceNickname) == 1) {
                resultMap.put("status", "false");
                resultMap.put("message", replaceNickname + " 은/는 이미 등록된 닉네임입니다.");
                resultMap.put("code", HttpStatus.BAD_REQUEST);
                return resultMap;
            }
            else if (!k.matcher(replaceNickname).matches()) {
                resultMap.put("status", "false");
                resultMap.put("message", "올바르지 않은 닉네임 형식입니다.(한,영,숫자 2~10자)");
                resultMap.put("code", HttpStatus.BAD_REQUEST);
                return resultMap;
            }
            data2.setMiNickname(replaceNickname);
            entity.setMiNickname(data2.getMiNickname());
            memberInfoRepository.save(entity);
            resultMap.put("status", "true");
            resultMap.put("message","변경되었습니다.");
            resultMap.put("code", HttpStatus.ACCEPTED);
            return resultMap;
        }

        if (type.equals("pwd")) {
            String pwd_pattern = "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z])(?=.*[A-Z]).{6,12}$";
            String replacepwd = data2.getMiUpdatePwd().replaceAll(" ", "");
            Pattern d = Pattern.compile(pwd_pattern);
            String decPwd = AESAlgorithm.Decrypt(entity.getMiPwd());
            if (!data2.getMiPwd().equals(decPwd)) {
                resultMap.put("message", "기존 비밀번호가 틀렸습니다. 다시 입력해주세요.");
                resultMap.put("code", HttpStatus.BAD_REQUEST);
                return resultMap;
            } 
            else if (!d.matcher(replacepwd).matches()) {
                resultMap.put("message", "올바르지 않은 비밀번호 형식입니다. (영어 대소문자, 숫자, 특수문자(6자이상)");
                resultMap.put("code", HttpStatus.BAD_REQUEST);
                return resultMap;
            }
            else if (replacepwd.equals(decPwd)) {
                resultMap.put("message", "기존 비밀번호와 동일합니다.");
                resultMap.put("code", HttpStatus.BAD_REQUEST);
                return resultMap;
            } 
            else if (replacepwd.length() == 0) {
                resultMap.put("message", "공백을 입력하셨습니다.");
                resultMap.put("code", HttpStatus.BAD_REQUEST);
                return resultMap;
            }
            else if (!data2.getMiCheckUpdatePwd().equals(replacepwd)) {
                resultMap.put("message", "새 비밀번호와 비밀번호 확인이 일치하지 않습니다.");
                resultMap.put("code", HttpStatus.BAD_REQUEST);
                return resultMap;
            }
            try {
                String encPwd = AESAlgorithm.Encrypt(replacepwd);
                data2.setMiPwd(encPwd);
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
            entity.setMiPwd(data2.getMiPwd());
            memberInfoRepository.save(entity);
            resultMap.put("status", "true");
            resultMap.put("message", "비밀번호가 변경되었습니다");
            resultMap.put("code", HttpStatus.ACCEPTED);
            return resultMap;
        }
        return resultMap;
    }

    // 목표금액 수정
    public Map<String, Object> UpdateMemberMoney(MemberDeleteVO data, Long member) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        
        MemberInfoEntity entity = memberInfoRepository.findById(member).orElse(null);
        if(entity == null){
            resultMap.put("status", false);
            resultMap.put("message", "회원정보가 없습니다. 로그인 먼저해주세요.");
            resultMap.put("code", HttpStatus.BAD_REQUEST);
            return resultMap;
        }

        Integer money = data.getMiTargetAmount();
        if(money < 0) {
            resultMap.put("status", false);
            resultMap.put("message", "음수값은 입력 할 수 없습니다.");
            resultMap.put("code", HttpStatus.BAD_REQUEST);
            return resultMap;
        }
        entity.setMiTargetAmount(data.getMiTargetAmount());
        memberInfoRepository.save(entity);
        resultMap.put("status", "true");
        resultMap.put("message","변경되었습니다.");
        resultMap.put("code", HttpStatus.ACCEPTED);
        return resultMap;
    }
}