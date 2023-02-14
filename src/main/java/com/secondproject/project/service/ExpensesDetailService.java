package com.secondproject.project.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.secondproject.project.entity.CategoryInfoEntity;
import com.secondproject.project.entity.ExpensesDetailEntity;
import com.secondproject.project.entity.MemberInfoEntity;
import com.secondproject.project.repository.CategoryInfoRepository;
import com.secondproject.project.repository.ExpensesDetailRepository;
import com.secondproject.project.repository.MemberInfoRepository;
import com.secondproject.project.vo.CategoryExpensesVO;
import com.secondproject.project.vo.DailyExpensesSearchVO;
import com.secondproject.project.vo.DailyExpensesVO;
import com.secondproject.project.vo.MonthExpensesResponseVO;
import com.secondproject.project.vo.PlusMinusExpensesVO;
import com.secondproject.project.vo.PutExpensesVO;
import com.secondproject.project.vo.YearExpensesListVO;
import com.secondproject.project.vo.YearExpensesResponseVO;
import com.secondproject.project.vo.YearExpensesVO;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class ExpensesDetailService {
    private final MemberInfoRepository mRepo;
    private final ExpensesDetailRepository edRepo;
    private final CategoryInfoRepository cateRepo;

    public Map<String, Object> daily(DailyExpensesSearchVO search, DailyExpensesSearchVO pastSearch){
        Map<String, Object> map = new LinkedHashMap<>();
        List<DailyExpensesVO> list =edRepo.dailyExpenses(search);
        if(list.size()==0){
            MonthExpensesResponseVO month = new MonthExpensesResponseVO();
            map.put("status", false);
            map.put("message", "등록된 지출 내역이 존재하지 않습니다.");
            map.put("code", HttpStatus.NO_CONTENT);
            map.put("list", month);
            return map;
        }
        
        MonthExpensesResponseVO month = new MonthExpensesResponseVO();
        month.setting(list);
        month.changeRate(edRepo.totalSum(pastSearch));
        map.put("status", true);
        map.put("message", "조회했습니다.");
        map.put("code", HttpStatus.OK);
        map.put("list", month);
        
        return map;
    }
    public Map<String, Object> cateTotal(DailyExpensesSearchVO search){
        Map<String, Object> map = new LinkedHashMap<>();
        List<CategoryExpensesVO> list =edRepo.CategoryExpenses(search);
        if(list.size()==0){
            MonthExpensesResponseVO month = new MonthExpensesResponseVO();
            map.put("status", false);
            map.put("message", "등록된 지출 내역이 존재하지 않습니다.");
            map.put("code", HttpStatus.NO_CONTENT);
            map.put("list", month);
            return map;
        }
        int total = 0;
        for(CategoryExpensesVO cate : list){
            total += cate.getPrice();
        }
        for(CategoryExpensesVO cate : list){
            cate.countRate(total);
        }
        map.put("status", true);
        map.put("message", "조회했습니다.");
        map.put("code", HttpStatus.OK);
        map.put("cate", list);

        return map;
    }

    public Map<String, Object> yearShow(DailyExpensesSearchVO search, DailyExpensesSearchVO pastSearch){
        Map<String, Object> map = new LinkedHashMap<>();
        List<YearExpensesVO> list = edRepo.yearSum(search);
        List<YearExpensesVO> pastList = edRepo.yearSum(pastSearch);
        YearExpensesListVO result = new YearExpensesListVO();
        result.setName("series1");
        for(int i=1;i<=12;i++){
            Boolean chk = true;
            for(YearExpensesVO y : list){
                if(y.getMonth()==i){
                    chk = false;
                    result.addData(y.getSum());
                }
            }
            if(chk){
                result.addData(0);
            }
            
        }
        YearExpensesListVO result2 = new YearExpensesListVO();
        result2.setName("series2");
        for(int i=1;i<=12;i++){
            Boolean chk = true;
            for(YearExpensesVO y : pastList){
                if(y.getMonth()==i){
                    chk = false;
                    result2.addData(y.getSum());
                }
            }
            if(chk){
                result2.addData(0);
            }
            
        }
        YearExpensesResponseVO finalResult = new YearExpensesResponseVO();
        finalResult.addList(result);
        finalResult.addList(result2);
        map.put("data", finalResult);
        map.put("code", HttpStatus.OK);
        return map;
    }
    
    // 지출입력
    public Map<String, Object> putExpensesService(Long miSeq, PutExpensesVO data) {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

        MemberInfoEntity member = mRepo.findById(miSeq).get();
        CategoryInfoEntity cate = cateRepo.findById(data.getCateSeq()).orElse(null);
        List<ExpensesDetailEntity> expenses = edRepo.findMemberAndCate(member, cate);
        if(cate != null && member != null && data.getEdAmont()!=null && data.getEdtitle() != null) {
            ExpensesDetailEntity newExpenses = ExpensesDetailEntity.builder()
                .edMiSeq(member)
                .edAmount(data.getEdAmont())
                .edCateSeq(cateRepo.findById(data.getCateSeq()).get())
                .edTitle(data.getEdtitle())
                .edDate(data.getEdDate())
                .build();
            edRepo.save(newExpenses);
            resultMap.put("status", true);
            resultMap.put("message", "지출내역이 등록되었습니다.");
        }
        else {
            System.out.println("aaaa");
            // 이부분이 아니라 값이 입력이 안되면 500에러가 뜸
            resultMap.put("status", false);
            resultMap.put("message", "필수 입력정보가 누락되었습니다.");
        }

        return resultMap;
    }

    // 지출 수정 (제목/ 카테고리/ 날짜? null 이면 entity에 dynamic 걸려있어서 시간으로 적어줌/ 금액)
    public Map<String, Object> updateExpensesService(Long miSeq, PlusMinusExpensesVO data) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        MemberInfoEntity member = mRepo.findById(miSeq).orElse(null);
        ExpensesDetailEntity entity = edRepo.findMemberAndEdSeq(member, data.getEdSeq());

        // System.out.println(data);
        // 유효성 검사      // 비교할려면 다 가져와서 비교후 넘어가기
        if((data.getEdAmount()==null && data.getEdCateSeq()==null && data.getEdTitle()==null) || data.getEdSeq()==null){
            map.put("update", false);
            map.put("message", "값이 입력되지 않았습니다.");
            return map;
        }
        // 멤버랑, 지출내역을 찾아서 entity로 넣어주야뎀
        if(member==null){
            map.put("update", false);
            map.put("message", "잘못된 회원번호입니다.");
            return map;
        }
        if(entity==null){
            map.put("update", false);
            map.put("message", "수정할수없습니다. 잘못된번호거나 본인이 작성한 내역이 아닙니다.");
            return map;
        }
        if(!StringUtils.hasText(data.getEdTitle())) {
            // !StringUtils.hasText  =>이거임 data.getEdTitle() == null || data.getEdTitle == ""
            entity.setEdTitle(data.getEdTitle());
        }
        else if(data.getEdCateSeq() != null) {
            CategoryInfoEntity cate = cateRepo.findById(data.getEdCateSeq()).orElse(null);

            entity.setEdCateSeq(cate);
        }
        else if(data.getEdAmount() != null) {
            entity.setEdAmount(data.getEdAmount());
        }

        map.put("updated", true);
        map.put("message", "지출내역이 변경되었습니다.");
        return map;
    }
    
    // 지출 삭제 - 유효성 검사는 순서대로 하니까 조심하세요!
    public Map<String, Object> deleteExpensesService(Long miSeq, Long edSeq) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        MemberInfoEntity member = mRepo.findById(miSeq).orElse(null);
        if(member==null){
            map.put("deleted", false);
            map.put("message", "잘못된 회원입니다.");
            return map;
        }
        ExpensesDetailEntity expenses = edRepo.findById(edSeq).orElse(null);
        if(expenses==null) {
            map.put("deleted", false);
            map.put("message", "존재하지 않는 내역 입니다.");
            return map;
        }
        if(miSeq == null || edSeq == null) {
            map.put("deleted", false);
            map.put("message", "회원번호나 지출내역번호가 잘못되었습니다.");
            return map;
        }
        System.out.println(expenses);
        if(edRepo.findByEdMiSeq(member) != mRepo.findById(miSeq).orElse(member) ) {
            map.put("deleted", false);
            map.put("message", "본인이 작성한 내역이 아닙니다.");
            return map;
        }
        //findbyId isempty 가 true 이면 없는 번호 입니다.
        edRepo.delete(expenses);
        map.put("deleted", true);
        map.put("message", "지출내역이 삭제 되었습니다.");
        // edRepo.delete(edRepo.findMiSeqAndEdSeq(miSeq, edSeq).orElse(null)); 

        return map;
    }
}
