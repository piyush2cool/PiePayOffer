package com.piepay.flipkartoffers.service;

import com.piepay.flipkartoffers.dto.OfferResponseDto;
import com.piepay.flipkartoffers.entity.Offer;
import com.piepay.flipkartoffers.repository.OfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OfferService {
    private final OfferRepository offerRepository;

    public OfferResponseDto processAndStoreOffers(Map<String, Object> apiResponse) {
        List<Map<String, Object>> offers = extractOffers(apiResponse);
        int identified = offers.size();
        int created = 0;

        for (Map<String, Object> offerMap : offers) {
            String title = (String) offerMap.get("title");
            String bank = (String) offerMap.get("bank");

            if (!offerRepository.existsByTitleAndBank(title, bank)) {
                Offer offer = Offer.builder()
                        .title(title)
                        .bank(bank)
                        .type((String) offerMap.get("type"))
                        .minAmount(Double.valueOf(offerMap.getOrDefault("minAmount", 0).toString()))
                        .maxDiscount(Double.valueOf(offerMap.getOrDefault("maxDiscount", 0).toString()))
                        .discountPercent(Double.valueOf(offerMap.getOrDefault("discountPercent", 0).toString()))
                        .paymentInstruments(new HashSet<>((List<String>) offerMap.get("paymentInstruments")))
                        .build();
                offerRepository.save(offer);
                created++;
            }
        }
        return new OfferResponseDto(identified, created);
    }

    public double getHighestDiscount(double amount, String bank, String instrument) {
        List<Offer> offers = offerRepository.findByBankIgnoreCaseAndPaymentInstrumentsContaining(bank, instrument);
        double maxDiscount = 0;
        for (Offer offer : offers) {
            if (amount >= offer.getMinAmount()) {
                double discount = Math.min((amount * offer.getDiscountPercent() / 100.0), offer.getMaxDiscount());
                maxDiscount = Math.max(maxDiscount, discount);
            }
        }
        return maxDiscount;
    }

    private List<Map<String, Object>> extractOffers(Map<String, Object> apiResponse) {
        List<Map<String, Object>> offers = new ArrayList<>();
        List<Map<String, Object>> allOffers = (List<Map<String, Object>>) apiResponse.getOrDefault("offers", List.of());

        for (Map<String, Object> raw : allOffers) {
            Map<String, Object> extracted = new HashMap<>();
            extracted.put("title", raw.get("title"));
            extracted.put("bank", raw.get("bankName"));
            extracted.put("type", raw.get("type"));
            extracted.put("minAmount", raw.get("minTxnAmount"));
            extracted.put("maxDiscount", raw.get("maxDiscountAmount"));
            extracted.put("discountPercent", raw.get("discountPercent"));
            extracted.put("paymentInstruments", raw.get("paymentOptions"));
            offers.add(extracted);
        }
        return offers;
    }
}
