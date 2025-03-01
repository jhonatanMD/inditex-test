package com.inditex.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.inditex.domain.exception.NotFoundException;
import com.inditex.domain.model.PriceResponseDTO;
import com.inditex.domain.repository.IPriceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PriceServiceTest {

    @Mock
    private IPriceRepository IPriceRepository;

    @InjectMocks
    private PriceService priceService;

    @Test
    void getPreferredPrice_ShouldReturnPrice_WhenPriceExists() {
        LocalDateTime applicationDate = LocalDateTime.now();
        Integer productId = 1;
        Integer brandId = 1;
        PriceResponseDTO mockDto = new PriceResponseDTO();

        when(IPriceRepository.getPreferredPrice(applicationDate, productId, brandId)).thenReturn(Optional.of(mockDto));

        PriceResponseDTO result = priceService.getPreferredPrice(applicationDate, productId, brandId);

        assertNotNull(result);
        assertEquals(mockDto, result);
    }

    @Test
    void getPreferredPrice_ShouldThrowException_WhenPriceDoesNotExist() {
        LocalDateTime applicationDate = LocalDateTime.now();
        Integer productId = 1;
        Integer brandId = 1;

        when(IPriceRepository.getPreferredPrice(applicationDate, productId, brandId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->
                priceService.getPreferredPrice(applicationDate, productId, brandId)
        );
    }
}
