package com.secondproject.project.repository.custom;

import java.util.List;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.secondproject.project.entity.QExpensesDetailEntity;
import com.secondproject.project.vo.DailyExpensesSearchVO;
import com.secondproject.project.vo.DailyExpensesVO;

import jakarta.persistence.EntityManager;

public class ExpensesDetailRepositoryImpl implements ExpensesDetailRepositoryCustom{

    private final JPAQueryFactory queryfactory;

    public ExpensesDetailRepositoryImpl(EntityManager em) {
        this.queryfactory = new JPAQueryFactory(em);
    }

    @Override
    public List<DailyExpensesVO> dailyExpenses(DailyExpensesSearchVO search) {
        return queryfactory.select(Projections.constructor(DailyExpensesVO.class, QExpensesDetailEntity.expensesDetailEntity.edDate, QExpensesDetailEntity.expensesDetailEntity.edAmount.sum()))
                    .from(QExpensesDetailEntity.expensesDetailEntity)
                    .where(
                        QExpensesDetailEntity.expensesDetailEntity.edMiSeq.eq(search.getMember()),
                        QExpensesDetailEntity.expensesDetailEntity.edDate.between(search.getStartDay(), search.getLastDay())
                    )
                    .groupBy(QExpensesDetailEntity.expensesDetailEntity.edDate)
                    .fetch();
    }
    
}
