package com.inditex.infrastructure.repository;

import com.inditex.domain.model.PriceResponseDTO;
import com.inditex.infrastructure.mapper.IPriceMapper;
import com.inditex.infrastructure.repository.entity.PriceEntity;
import com.inditex.infrastructure.repository.jpa.IPriceJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceRepositoryImplTest {

    @Mock
    private IPriceJpaRepository priceJPARepository;
    @Mock
    private IPriceMapper mapper;

    @InjectMocks
    private PriceRepositoryImpl IPriceRepository;

    @Test
    void getPreferredPrice_ShouldReturnMappedPrice_WhenPriceExists() {
        LocalDateTime applicationDate = LocalDateTime.now();
        Integer productId = 1;
        Integer brandId = 1;

        PriceEntity mockEntity = new PriceEntity();
        PriceResponseDTO mockDto = new PriceResponseDTO();

        when(priceJPARepository.findTopByProductIdAndBrandIdAndStartDateBeforeAndEndDateAfterOrderByPriorityDesc(
                productId, brandId, applicationDate, applicationDate)).thenReturn(Optional.of(mockEntity));
        when(mapper.toDomain(mockEntity)).thenReturn(mockDto);

        Optional<PriceResponseDTO> result = IPriceRepository.getPreferredPrice(applicationDate, productId, brandId);

        assertTrue(result.isPresent());
        assertEquals(mockDto, result.get());
    }

    @Test
    void getPreferredPrice_ShouldReturnEmpty_WhenPriceDoesNotExist() {
        LocalDateTime applicationDate = LocalDateTime.now();
        Integer productId = 1;
        Integer brandId = 1;

        when(priceJPARepository.findTopByProductIdAndBrandIdAndStartDateBeforeAndEndDateAfterOrderByPriorityDesc(
                productId, brandId, applicationDate, applicationDate)).thenReturn(Optional.empty());

        Optional<PriceResponseDTO> result = IPriceRepository.getPreferredPrice(applicationDate, productId, brandId);

        assertFalse(result.isPresent());
    }
}
