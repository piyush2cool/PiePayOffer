package com.piepay.flipkartoffers.dto;

import lombok.Data;
import java.util.Map;

@Data
public class OfferRequestDto {
    private Map<String, Object> flipkartOfferApiResponse;
}
