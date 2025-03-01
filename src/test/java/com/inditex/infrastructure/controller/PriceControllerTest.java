package com.inditex.infrastructure.controller;

import com.inditex.application.port.IGetPriceUseCase;
import com.inditex.domain.model.PriceResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceControllerTest {

    @Mock
    private IGetPriceUseCase getPriceUseCase;

    @InjectMocks
    private PriceController priceController;

    @Test
    void getPrices_ShouldReturnResponseEntity_WhenPriceExists() {
        LocalDateTime applicationDate = LocalDateTime.now();
        Integer productId = 1;
        Integer brandId = 1;
        PriceResponseDTO mockDto = new PriceResponseDTO();

        when(getPriceUseCase.getPreferredPrice(applicationDate, productId, brandId)).thenReturn(mockDto);

        ResponseEntity<PriceResponseDTO> response = priceController.getPrices(applicationDate, productId, brandId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockDto, response.getBody());
    }
}
