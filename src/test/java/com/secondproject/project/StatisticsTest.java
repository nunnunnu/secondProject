package com.secondproject.project;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.secondproject.project.entity.CategoryInfoEntity;
import com.secondproject.project.entity.ExpensesDetailEntity;
import com.secondproject.project.entity.MemberInfoEntity;
import com.secondproject.project.entity.TargetAreaInfoEntity;
import com.secondproject.project.repository.CategoryInfoRepository;
import com.secondproject.project.repository.ExpensesDetailRepository;
import com.secondproject.project.repository.MemberInfoRepository;
import com.secondproject.project.repository.TargerAreaInfoRepository;
import com.secondproject.project.vo.CategoryExpensesVO;
import com.secondproject.project.vo.DailyExpensesSearchVO;
import com.secondproject.project.vo.DailyExpensesVO;
import com.secondproject.project.vo.MonthExpensesResponseVO;
import com.secondproject.project.vo.YearExpensesVO;
import com.secondproject.project.vo.expenses.TargetRateVO;
import com.secondproject.project.vo.expenses.UserCompare;

import lombok.RequiredArgsConstructor;

@SpringBootTest
@RequiredArgsConstructor
@Transactional
public class StatisticsTest {

    private MemberInfoEntity member;
    private CategoryInfoEntity cate;
    private CategoryInfoEntity cate2;
    private ExpensesDetailEntity nowExpense, nowExpense2, nowExpense3, pastExpense, pastExpense2, pastExpense3, LastYearExpense;

    @Autowired MemberInfoRepository mRepo;
    @Autowired CategoryInfoRepository cRepo;
    @Autowired ExpensesDetailRepository edRepo;
    @Autowired TargerAreaInfoRepository tRepo;

    @BeforeEach
    public void beforeEach(){
        member = new MemberInfoEntity(null, "user0000999@mdmdm.cidmn", "123456", null, null, 60000, "미미", 0, 1);
        mRepo.save(member);
        cate = new CategoryInfoEntity(null, "테스트용");
        cate2 = new CategoryInfoEntity(null, "테스트용2");
        cRepo.save(cate);
        cRepo.save(cate2);

        nowExpense = new ExpensesDetailEntity(null, member, "밥", cate, LocalDate.now().minusDays(1), 10000);
        nowExpense2 = new ExpensesDetailEntity(null, member, "간식", cate2, LocalDate.now(), 9000);
        nowExpense3 = new ExpensesDetailEntity(null, member, "교통", cate2, LocalDate.now(), 11000);
        pastExpense = new ExpensesDetailEntity(null, member, "밥", cate, LocalDate.now().minusDays(1).minusMonths(1), 5000);
        pastExpense2 = new ExpensesDetailEntity(null, member, "간식", cate2, LocalDate.now().minusMonths(1), 4000);
        pastExpense3 = new ExpensesDetailEntity(null, member, "교통", cate2, LocalDate.now().minusMonths(1), 6000);
        LastYearExpense = new ExpensesDetailEntity(null, member, "교통", cate2, LocalDate.now().minusYears(1), 6000);
        edRepo.save(nowExpense);
        edRepo.save(nowExpense2);
        edRepo.save(nowExpense3);
        edRepo.save(pastExpense);
        edRepo.save(pastExpense2);
        edRepo.save(pastExpense3);
        edRepo.save(LastYearExpense);
    }
    @Test
    public void 카테고리별합계_비율포함(){
        LocalDate start = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate last = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        DailyExpensesSearchVO search = new DailyExpensesSearchVO(start, last, member);
        List<CategoryExpensesVO> result = edRepo.CategoryExpenses(search);
        int total = 0;
        for(CategoryExpensesVO cate : result){
            total += cate.getPrice();
        }
        for(CategoryExpensesVO cate : result){
            cate.countRate(total);
        }
        //합계 검사
        assertThat(result).extracting("price")
                .containsExactly(nowExpense.getEdAmount(),nowExpense2.getEdAmount()+nowExpense3.getEdAmount());
        
        //비율 검사
        Double cateTotal = (double)(nowExpense.getEdAmount()+nowExpense2.getEdAmount()+nowExpense3.getEdAmount());
        assertThat(result).extracting("rate")
                .containsExactly(nowExpense.getEdAmount()/cateTotal*100, (nowExpense2.getEdAmount()+nowExpense3.getEdAmount())/cateTotal*100);
    }

    @Test
    public void 캘린더_일별합계_전월비교(){
        LocalDate start = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate last = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        DailyExpensesSearchVO search = new DailyExpensesSearchVO(start, last, member);
        List<DailyExpensesVO> list =edRepo.dailyExpenses(search);
        
        assertThat(list.get(1).getPrice()).isEqualTo(nowExpense2.getEdAmount()+nowExpense3.getEdAmount());
        
        DailyExpensesSearchVO pastSearch = new DailyExpensesSearchVO(start.minusMonths(1), last.minusMonths(1), member);
        MonthExpensesResponseVO month = new MonthExpensesResponseVO();
        month.setting(list);
        Integer lastTotal = edRepo.totalSum(pastSearch);
        month.changeRate(lastTotal);

        assertThat(month.getMaxDay().getDate()).isEqualTo(LocalDate.now());
        assertThat(month.getMinDay().getDate()).isEqualTo(LocalDate.now().minusDays(1));
        
        Integer total = nowExpense.getEdAmount()+nowExpense2.getEdAmount()+nowExpense3.getEdAmount();
        
        assertThat(month.getTotal()).isEqualTo(total);
        assertThat(month.getRate()).isEqualTo(month.getTotal()/lastTotal*100);
    }
    @Test
    public void 년조회_월별합계(){
        LocalDate start = LocalDate.now().with(TemporalAdjusters.firstDayOfYear());
        LocalDate last = LocalDate.now().with(TemporalAdjusters.lastDayOfYear());

        DailyExpensesSearchVO search = new DailyExpensesSearchVO(start, last, member);
        DailyExpensesSearchVO pastSearch = new DailyExpensesSearchVO(start.minusYears(1), last.minusYears(1), member);

        List<YearExpensesVO> list = edRepo.yearSum(search);
        List<YearExpensesVO> pastList = edRepo.yearSum(pastSearch);
        assertThat(list.size()).isEqualTo(2);
        assertThat(pastList.size()).isEqualTo(1);
    }

    @Test
    public void 목표금액사용비율_남은비율(){
        LocalDate start = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate last = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        DailyExpensesSearchVO search = new DailyExpensesSearchVO(start, last, member);

        Integer totalSum = edRepo.totalSum(search);
        
        TargetRateVO result = new TargetRateVO(search.getMember().getMiTargetAmount(), totalSum);

        assertThat(result.getTarget()).isEqualTo(member.getMiTargetAmount());

        Integer total = nowExpense.getEdAmount()+nowExpense2.getEdAmount()+nowExpense3.getEdAmount();
        assertThat(result.getUsed()).isEqualTo(total);
        assertThat(result.getRemaining()).isEqualTo(member.getMiTargetAmount()-total);

        assertThat(result.getRemainingRete()).isEqualTo((double)(member.getMiTargetAmount()-total)/member.getMiTargetAmount()*100);
        assertThat(result.getUsedRate()).isEqualTo((double)total/member.getMiTargetAmount()*100.0);
    }
    @Test
    public void 회원별비교(){
        LocalDate start = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate last = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        DailyExpensesSearchVO search = new DailyExpensesSearchVO(start, last, member);
        TargetAreaInfoEntity target = tRepo.findTarget(member.getMiTargetAmount());
        List<MemberInfoEntity> members = mRepo.findByMiTargetAmountBetween(target.getTaiMinCost(), target.getTaiMaxCost());

        List<UserCompare> result = edRepo.userCompareQuery(search, members);
        
        assertThat(result).extracting("mySum")
                .containsExactly(nowExpense.getEdAmount(),nowExpense2.getEdAmount()+nowExpense3.getEdAmount());
    }
}
