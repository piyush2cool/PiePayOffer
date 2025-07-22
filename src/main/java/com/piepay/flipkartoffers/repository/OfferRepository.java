package com.piepay.flipkartoffers.repository;

import com.piepay.flipkartoffers.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findByBankIgnoreCaseAndPaymentInstrumentsContaining(String bank, String instrument);
    boolean existsByTitleAndBank(String title, String bank);
}
