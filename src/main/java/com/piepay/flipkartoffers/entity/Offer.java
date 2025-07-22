package com.piepay.flipkartoffers.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String bank;
    private String type;
    private Double minAmount;
    private Double maxDiscount;
    private Double discountPercent;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> paymentInstruments;
}
