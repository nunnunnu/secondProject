package com.secondproject.project.repository.custom;

import static com.secondproject.project.entity.QCategoryInfoEntity.categoryInfoEntity;
import static com.secondproject.project.entity.QExpensesDetailEntity.expensesDetailEntity;

import java.util.List;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.secondproject.project.entity.MemberInfoEntity;
import com.secondproject.project.entity.QExpensesDetailEntity;
import com.secondproject.project.vo.CategoryExpensesVO;
import com.secondproject.project.vo.DailyExpensesSearchVO;
import com.secondproject.project.vo.DailyExpensesVO;
import com.secondproject.project.vo.YearExpensesVO;
import com.secondproject.project.vo.expenses.UserCompare;

import jakarta.persistence.EntityManager;

public class ExpensesDetailRepositoryImpl implements ExpensesDetailRepositoryCustom{

    private final JPAQueryFactory queryfactory;

    public ExpensesDetailRepositoryImpl(EntityManager em) {
        this.queryfactory = new JPAQueryFactory(em);
    }

    @Override
    public List<DailyExpensesVO> dailyExpenses(DailyExpensesSearchVO search) {
        return queryfactory.select(Projections.constructor(DailyExpensesVO.class, expensesDetailEntity.edDate, expensesDetailEntity.edAmount.sum()))
                    .from(expensesDetailEntity)
                    .where(
                        expensesDetailEntity.edMiSeq.eq(search.getMember()),
                        expensesDetailEntity.edDate.between(search.getStartDay(), search.getLastDay())
                    )
                    .groupBy(expensesDetailEntity.edDate)
                    .fetch();
    }

    @Override
    public List<CategoryExpensesVO> CategoryExpenses(DailyExpensesSearchVO search){
        return queryfactory.select(Projections.fields(
                                        CategoryExpensesVO.class,
                                        categoryInfoEntity.cateName.as("cate"),
                                        expensesDetailEntity.edAmount.sum().as("price")
                                    ))
                            .from(expensesDetailEntity)
                            .join(expensesDetailEntity.edCateSeq, categoryInfoEntity)
                            .where(
                                expensesDetailEntity.edMiSeq.eq(search.getMember()),
                                expensesDetailEntity.edDate.between(search.getStartDay(), search.getLastDay())
                            )
                            .groupBy(expensesDetailEntity.edCateSeq)
                            .fetch();

    }
    @Override
    public Integer totalSum(DailyExpensesSearchVO search){
        return queryfactory.select(expensesDetailEntity.edAmount.sum())
                            .from(expensesDetailEntity)
                            .where(
                                expensesDetailEntity.edMiSeq.eq(search.getMember()),
                                expensesDetailEntity.edDate.between(search.getStartDay(), search.getLastDay())
                            )
                            .groupBy(expensesDetailEntity.edDate.year())
                            .fetchFirst();              
    }
    @Override
    public List<YearExpensesVO> yearSum(DailyExpensesSearchVO search){
        return queryfactory.select(Projections.fields(
                                YearExpensesVO.class,
                                expensesDetailEntity.edDate.month().as("month"),
                                expensesDetailEntity.edAmount.sum().as("sum")
                            ))
                            .from(expensesDetailEntity)
                            .where(
                                expensesDetailEntity.edMiSeq.eq(search.getMember()),
                                expensesDetailEntity.edDate.between(search.getStartDay(), search.getLastDay())
                            )
                            .groupBy(expensesDetailEntity.edDate.month())
                            .fetch();              
    }

//     select avg(ed.ed_amount), ed.ed_cate_seq
// , (select avg(ed2.ed_amount) from expenses_detail ed2 where ed2.ed_cate_seq = ed.ed_cate_seq and ed2.ed_date between '2023-01-01' and '2023-01-31' and ed2.ed_mi_seq in (1,2,3) group by ed2.ed_cate_seq)  
// from expenses_detail ed where ed.ed_date between '2023-01-01' and '2023-01-31' and ed.ed_mi_seq =1 group by ed.ed_cate_seq ;
    @Override
    public List<UserCompare> userCompareQuery(DailyExpensesSearchVO search, List<MemberInfoEntity> members){
        QExpensesDetailEntity expensesSub = new QExpensesDetailEntity("expensesSub");
        // return queryfactory.select(Projections.fields(
        //                                 expensesDetailEntity.edAmount.avg().as("userAvg"),
        //                                 categoryInfoEntity.cateSeq.as("seq"),
        //                                 categoryInfoEntity.cateName.as("cate"),
        //                                 JPAExpressions
        //                                     .select(expensesSub.edAmount.avg())
        //                                     .from(expensesSub)
        //                                     .where(
        //                                         expensesSub.edCateSeq.eq(expensesDetailEntity.edCateSeq),
        //                                         expensesSub.edMiSeq.in(members),
        //                                         expensesSub.edDate.between(search.getStartDay(), search.getLastDay())
        //                                     )
        //                                     .groupBy(expensesDetailEntity.edCateSeq)
        //                                     .fetch()
        //                                 )
                                        
        //                     )        
        //                     .from(expensesDetailEntity)
        //                     .join(expensesDetailEntity.edCateSeq, categoryInfoEntity)
        //                     .where(
        //                         expensesDetailEntity.edMiSeq.eq(search.getMember()),
        //                         expensesDetailEntity.edDate.between(search.getStartDay(), search.getLastDay())
        //                     )
        //                     .groupBy(expensesDetailEntity.edCateSeq)
        //                     .fetch(); 
        return null;
    }
    
}
