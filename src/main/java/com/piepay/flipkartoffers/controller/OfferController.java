package com.piepay.flipkartoffers.controller;

import com.piepay.flipkartoffers.dto.OfferRequestDto;
import com.piepay.flipkartoffers.dto.OfferResponseDto;
import com.piepay.flipkartoffers.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;

    @PostMapping("/offer")
    public OfferResponseDto storeOffers(@RequestBody OfferRequestDto request) {
        return offerService.processAndStoreOffers(request.getFlipkartOfferApiResponse());
    }

    @GetMapping("/highest-discount")
    public Map<String, Double> getHighestDiscount(
        @RequestParam double amountToPay,
        @RequestParam String bankName,
        @RequestParam String paymentInstrument
    ) {
        return Map.of("highestDiscountAmount",
            offerService.getHighestDiscount(amountToPay, bankName, paymentInstrument));
    }
}
