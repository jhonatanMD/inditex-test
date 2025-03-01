package com.inditex.application.port;

import com.inditex.domain.model.PriceResponseDTO;

import java.time.LocalDateTime;

public interface IGetPriceUseCase {
    PriceResponseDTO getPreferredPrice(LocalDateTime applicationDate, Integer productId, Integer brandId);
}
