package com.piepay.flipkartoffers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OfferResponseDto {
    private int noOfOffersIdentified;
    private int noOfNewOffersCreated;
}
