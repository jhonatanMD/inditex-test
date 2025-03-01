package com.inditex.infrastructure.repository;

import com.inditex.domain.model.PriceResponseDTO;
import com.inditex.domain.repository.IPriceRepository;
import com.inditex.infrastructure.mapper.IPriceMapper;
import com.inditex.infrastructure.repository.jpa.IPriceJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PriceRepositoryImpl implements IPriceRepository {

    private final IPriceJpaRepository priceJPARepository;
    private final IPriceMapper mapper;
    @Override
    public Optional<PriceResponseDTO> getPreferredPrice(LocalDateTime applicationDate, Integer productId, Integer brandId) {

        return priceJPARepository.findTopByProductIdAndBrandIdAndStartDateBeforeAndEndDateAfterOrderByPriorityDesc(
                productId, brandId, applicationDate, applicationDate
        ).map(mapper::toDomain);
    }
}
