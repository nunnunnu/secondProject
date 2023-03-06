package com.secondproject.project.entity;

import com.secondproject.project.vo.PaymentListVO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment_info")
@Builder
public class PaymentInfoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pi_seq")
    private Long piSeq;

    @Column(name = "pi_name")
    private String piName;

    public PaymentInfoEntity(PaymentListVO vo) {
        this.piSeq = vo.getPiSeq();
        this.piName = vo.getPiName();
    }

}
