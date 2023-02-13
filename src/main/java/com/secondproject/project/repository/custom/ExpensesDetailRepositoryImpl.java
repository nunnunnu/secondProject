package com.secondproject.project.repository.custom;

import static com.secondproject.project.entity.QCategoryInfoEntity.categoryInfoEntity;
import static com.secondproject.project.entity.QExpensesDetailEntity.expensesDetailEntity;

import java.util.List;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.secondproject.project.entity.QCategoryInfoEntity;
import com.secondproject.project.entity.QExpensesDetailEntity;
import com.secondproject.project.vo.DailyExpensesSearchVO;
import com.secondproject.project.vo.DailyExpensesVO;
import com.secondproject.project.vo.CategoryExpensesVO;

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
    
}
