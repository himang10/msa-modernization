package com.skcc.data.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name="orders")
public class Order {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "accountId")
    private long accountId;

    @Column(name = "paymentId")
    private long paymentId;

    @Column(name = "accountInfo", length = Integer.MAX_VALUE, nullable = false)
    private String accountInfo;

    @Column(name = "productsInfo", length = Integer.MAX_VALUE, nullable = false)
    private String productsInfo;

    @Column(name = "paymentInfo", length = Integer.MAX_VALUE, nullable = false)
    private String paymentInfo;

    @Column(name = "paid", length = 255, nullable = false)
    private String paid;

    @Column(name = "status", length = 255, nullable = false)
    private String status;

    @Column(name = "createAt", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    private Timestamp createdAt;
}
