package com.secondproject.project.service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.secondproject.project.entity.CategoryInfoEntity;
import com.secondproject.project.entity.ExpensesDetailEntity;
import com.secondproject.project.entity.MemberInfoEntity;
import com.secondproject.project.entity.TargetAreaInfoEntity;
import com.secondproject.project.repository.CategoryInfoRepository;
import com.secondproject.project.repository.ExpensesDetailRepository;
import com.secondproject.project.repository.MemberInfoRepository;
import com.secondproject.project.repository.TargerAreaInfoRepository;
import com.secondproject.project.vo.CategoryExpensesListVO;
import com.secondproject.project.vo.CategoryExpensesVO;
import com.secondproject.project.vo.DailyExpensesSearchVO;
import com.secondproject.project.vo.DailyExpensesVO;
import com.secondproject.project.vo.MapVO;
import com.secondproject.project.vo.MonthExpensesResponseVO;
import com.secondproject.project.vo.MonthListExpensesVO;
import com.secondproject.project.vo.PlusMinusExpensesVO;
import com.secondproject.project.vo.PutExpensesVO;
import com.secondproject.project.vo.YearExpensesListVO;
import com.secondproject.project.vo.YearExpensesVO;
import com.secondproject.project.vo.expenses.TargetRateVO;
import com.secondproject.project.vo.expenses.UserCompare;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class ExpensesDetailService {
    private final MemberInfoRepository mRepo;
    private final ExpensesDetailRepository edRepo;
    private final CategoryInfoRepository cateRepo;
    private final TargerAreaInfoRepository tRepo;

    public Map<String, Object> daily(DailyExpensesSearchVO search, DailyExpensesSearchVO pastSearch){
        Map<String, Object> map = new LinkedHashMap<>();
        List<DailyExpensesVO> list =edRepo.dailyExpenses(search);
        if(list.size()==0){
            map.put("status", false);
            map.put("message", "등록된 지출 내역이 존재하지 않습니다.");
            map.put("code", HttpStatus.NO_CONTENT);
            // map.put("list", month);
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
            map.put("status", false);
            map.put("message", "등록된 지출 내역이 존재하지 않습니다.");
            map.put("code", HttpStatus.NO_CONTENT);
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
        
        YearExpensesListVO nowExpenses = new YearExpensesListVO(list);
        nowExpenses.setYear(search.getStartDay().getYear());
        
        YearExpensesListVO pastExpeses = new YearExpensesListVO(pastList);
        pastExpeses.setYear(pastSearch.getStartDay().getYear());
        
        List<YearExpensesListVO> finalResult = new ArrayList<>();
        finalResult.add(pastExpeses);
        finalResult.add(nowExpenses);
        map.put("data", finalResult);
        // map.put("code", HttpStatus.OK);
        return map;
    }
    
    // 지출내역 카테고리선택 내역 조회 - 검색
    // public CategoryExpensesListVO getCategoryExpensesListSearchService(String keyword) { 
    //     if(keyword == null) keyword = "";
    //     CategoryInfoEntity entity = cateRepo.findByCateName(keyword);
    //     CategoryExpensesListVO cateExList = CategoryExpensesListVO.builder()
    //         .cateSeq(entity.getCateSeq())
    //         .cateName(entity.getCateName())
    //         .build();
    //     return cateExList;
    // }

    // 지출내역 카테고리 리스트
    public List<CategoryExpensesListVO> cateExpensesList() {
        List<CategoryExpensesListVO> cateExpensesList = new ArrayList<>();
        List<CategoryInfoEntity> cateEntity = cateRepo.findAll();
        for(CategoryInfoEntity c : cateEntity){
            cateExpensesList.add(new CategoryExpensesListVO(c));
        }
        return cateExpensesList;
    }

    // 지출내역 조회 1차 회원의 한달단위 지출 리스트 Get 
    public List<MonthListExpensesVO> MonthExpensesList(Long miSeq, Integer year, Integer month) {
        //  Integer year, Integer month -> localdate start localdate end
        //  LocalDate firstDate = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        //  LocalDate lastedDate = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        MemberInfoEntity member = mRepo.findByMiSeq(miSeq);
        List<ExpensesDetailEntity> entity = edRepo.findByEdMiSeq(member);
            // VO를 리스트로 가져오면 builder에 있는 것을 계속 해서 
            // 하드코딩으로 변수명 같은 것을 바꿔줘야해서 VO 생성자를 가지고 하는 것이
            // Service에서 활용도가 높다.
        List<MonthListExpensesVO> list = new ArrayList<>();
        for(int i=0; i<entity.size(); i++){
            if(year == entity.get(i).getEdDate().getYear() && month == entity.get(i).getEdDate().getMonthValue()) {
                list.add( new MonthListExpensesVO(entity.get(i)));
            }
        }
        // 유효성 검사 하기! null 값이 들어왔을때! controller에 400에러 같은거 api에 붙여주기

        return list;
    }
    // 선택 월 지출내역 조회 2차 최근 소비내역 3개만 나오게 FINDTOP
    public List<MonthListExpensesVO> MonthExpensesListTop3(Long miSeq, Integer year, Integer month) {
        LocalDate start = LocalDate.of(year, month, 1).with(TemporalAdjusters.firstDayOfMonth());
        LocalDate end = start.with(TemporalAdjusters.lastDayOfMonth());

        MemberInfoEntity member = mRepo.findByMiSeq(miSeq);
        List<ExpensesDetailEntity> entity = edRepo.findTop3ByEdMiSeqAndEdDateBetweenOrderByEdDateDesc(member, start, end);
        List<MonthListExpensesVO> list = new ArrayList<>();
        for(int i=0; i<entity.size(); i++){
                list.add( new MonthListExpensesVO(entity.get(i)));
        }
        return list;
    }

    // NOW FINDTOP3 현월 지출내역 조회 2차 최근 소비내역 3개만 나오게   
    public List<MonthListExpensesVO> NowMonthExpensesListTop3(Long miSeq) {
        LocalDate start = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate end = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());

        MemberInfoEntity member = mRepo.findByMiSeq(miSeq);
        List<ExpensesDetailEntity> entity = edRepo.findTop3ByEdMiSeqAndEdDateBetweenOrderByEdDateDesc(member, start, end);
        List<MonthListExpensesVO> list = new ArrayList<>();
        for(int i=0; i<entity.size(); i++){
                list.add( new MonthListExpensesVO(entity.get(i)));
        }
        return list;
    }

    // 지출입력
    public MapVO putExpensesService(Long miSeq, PutExpensesVO data) {
        // Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        MapVO map = new MapVO();
        MemberInfoEntity member = mRepo.findById(miSeq).get();
        CategoryInfoEntity cate = cateRepo.findById(data.getCateSeq()).orElse(null);
        // List<ExpensesDetailEntity> expenses = edRepo.findMemberAndCate(member, cate); //안씀
        if(cate != null && member != null && data.getEdAmont()!=null && data.getEdtitle() != null) {
            ExpensesDetailEntity newExpenses = ExpensesDetailEntity.builder()
                .edMiSeq(member)
                .edAmount(data.getEdAmont())
                .edCateSeq(cateRepo.findById(data.getCateSeq()).get())
                .edTitle(data.getEdtitle())
                .edDate(data.getEdDate())
                .build();
            edRepo.save(newExpenses);
            map.setStatus(true);
            map.setMessage("지출내역이 등록되었습니다");
            map.setCode(HttpStatus.OK);
        }
        else {
            // System.out.println("aaaa");
            // 이부분이 아니라 값이 입력이 안되면 500에러가 뜸
            map.setStatus(false);
            map.setMessage("필수 입력정보가 누락되었습니다.");
            map.setCode(HttpStatus.BAD_REQUEST);
        }
        return map;
    }

    // 지출 수정 (제목/ 카테고리/ 날짜? null 이면 entity에 dynamic 걸려있어서 시간으로 적어줌/ 금액)
    public MapVO updateExpensesService(Long miSeq, PlusMinusExpensesVO data) {
        // Map<String, Object> map = new LinkedHashMap<String, Object>();
        MapVO map = new MapVO();
        MemberInfoEntity member = mRepo.findById(miSeq).orElse(null);
        ExpensesDetailEntity entity = edRepo.findMemberAndEdSeq(member, data.getEdSeq());
        // System.out.println(data);
        // 유효성 검사      // 비교할려면 다 가져와서 비교후 넘어가기
        if((data.getEdAmount()==null && data.getEdCateSeq()==null && data.getEdTitle()==null) || data.getEdSeq()==null){
            map.setStatus(false);
            map.setMessage("필수 값이 입력되지 않았습니다.");
            map.setCode(HttpStatus.BAD_REQUEST);
            return map;
        }
        // 멤버랑, 지출내역을 찾아서 entity로 넣어주야뎀
        if(member==null){
            map.setStatus(false);
            map.setMessage("잘못된 회원번호입니다.");
            map.setCode(HttpStatus.BAD_REQUEST);
            return map;
        }
        if(entity==null){
            map.setStatus(false);
            map.setMessage("수정할수없습니다. 잘못된번호거나 본인이 작성한 내역이 아닙니다.");
            map.setCode(HttpStatus.BAD_REQUEST);
            return map;
        }
        if(StringUtils.hasText(data.getEdTitle())) { // null 이 아니라면 save.문이 실행한다.
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
        edRepo.save(entity);
        
        map.setStatus(true);
        map.setMessage("지출내역이 변경되었습니다.");
        map.setCode(HttpStatus.ACCEPTED);
        return map;
    }
    
    // 지출 삭제 - 유효성 검사는 순서대로 하니까 조심하세요!
    public MapVO deleteExpensesService(Long miSeq, Long edSeq) {
        // Map<String, Object> map = new LinkedHashMap<String, Object>();
        MapVO map = new MapVO();
        MemberInfoEntity member = mRepo.findById(miSeq).orElse(null);
        if(member==null){
            map.setStatus(false);
            map.setMessage("잘못된 회원입니다.");
            map.setCode(HttpStatus.BAD_REQUEST);
            return map;
        }
        ExpensesDetailEntity expenses = edRepo.findById(edSeq).orElse(null);
        if(expenses==null){
            map.setStatus(false);
            map.setMessage("지출내역번호 오류입니다.");
            map.setCode(HttpStatus.BAD_REQUEST);
            return map;
        }
        if(expenses.getEdMiSeq().getMiSeq() !=  member.getMiSeq()) {
            map.setStatus(false);
            map.setMessage("본인이 작성한 내역이 아닙니다.");
            map.setCode(HttpStatus.BAD_REQUEST);
            return map;
        }
        if(miSeq == null || edSeq == null) {
            map.setStatus(false);
            map.setMessage("회원번호나 지출내역번호가 잘못되었습니다.");
            map.setCode(HttpStatus.BAD_REQUEST);
            return map;
        }
        //findbyId isempty 가 true 이면 없는 번호 입니다.
        
        edRepo.delete(expenses);
        map.setStatus(true);
        map.setMessage("지출내역이 삭제 되었습니다.");
        map.setCode(HttpStatus.OK);
        // edRepo.delete(edRepo.findMiSeqAndEdSeq(miSeq, edSeq).orElse(null)); 

        return map;
    }

    public Map<String, Object> amountRate(DailyExpensesSearchVO search){
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        
        Integer totalSum = edRepo.totalSum(search);
        
        TargetRateVO result = new TargetRateVO(search.getMember().getMiTargetAmount(), totalSum);
        // map.put("status", true);
        // map.put("message", "조회했습니다.");
        // map.put("code", HttpStatus.OK);
        map.put("data", result);
        
        return map;
    }
    public Map<String, Object> userCompare(DailyExpensesSearchVO search) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        if(search.getMember().getMiTargetAmount()==null){
            map.put("status", false);
            map.put("message", "목표 금액이 설정되지않았습니다..");
            map.put("code", HttpStatus.NO_CONTENT);
            return map;
        }
        TargetAreaInfoEntity target = tRepo.findTarget(search.getMember().getMiTargetAmount());
        List<MemberInfoEntity> members = mRepo.findByMiTargetAmountBetween(target.getTaiMinCost(), target.getTaiMaxCost());
        System.out.println(members);
        List<UserCompare> result = edRepo.userCompareQuery(search, members);
        if(result.size()==0){
            map.put("status", false);
            map.put("message", "입력된 데이터가 없습니다.");
            map.put("code", HttpStatus.NO_CONTENT);
            return map;
        }
        
        map.put("status", true);
        map.put("message", "조회성공");
        map.put("code", HttpStatus.OK);
        map.put("data", result);
        return map;
    }


}
