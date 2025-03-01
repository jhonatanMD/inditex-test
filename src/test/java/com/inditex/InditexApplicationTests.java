package com.inditex;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

@SpringBootTest
@AutoConfigureMockMvc
class InditexApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    private void testCase(BigDecimal price, Object... params) throws Exception {

         mockMvc.perform(MockMvcRequestBuilders.get("/prices?applicationDate={date}&productId={productId}" +
                                "&brandId={brandId}",params)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("price").value(price));
    }

    @Test
    void test1() throws Exception {

        testCase(BigDecimal.valueOf(35.50),"2025-06-14T10:00:00.000",35455,1);
    }

    @Test
    void test2() throws Exception {

        testCase(BigDecimal.valueOf(25.45),"2025-06-14T16:00:00.000",35455,1);
    }

    @Test
    void test3() throws Exception {

        testCase(BigDecimal.valueOf(35.50), "2025-06-14T21:00:00.000",35455,1);
    }

    @Test
    void test4() throws Exception {

        testCase(BigDecimal.valueOf(30.50), "2025-06-15T10:00:00.000",35455,1);
    }

    @Test
    void test5() throws Exception {

        testCase(BigDecimal.valueOf(38.95), "2025-06-16T21:00:00.000",35455,1);
    }

}