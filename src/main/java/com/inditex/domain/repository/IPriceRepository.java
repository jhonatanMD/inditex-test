package com.inditex.domain.repository;

import com.inditex.domain.model.PriceResponseDTO;

import java.time.LocalDateTime;
import java.util.Optional;

public interface IPriceRepository {
    Optional<PriceResponseDTO> getPreferredPrice(LocalDateTime applicationDate, Integer productId, Integer brandId);
}
