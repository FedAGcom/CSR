package com.fedag.CSR.service;

import com.fedag.CSR.dto.response.ItemResponse;
import com.fedag.CSR.mapper.mapperImpl.ItemMapperImpl;
import com.fedag.CSR.repository.ItemRepository;
import com.fedag.CSR.service.impl.ItemServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith({MockitoExtension.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ItemServiceTest {

    @InjectMocks
    ItemServiceImpl itemService;
    @Mock
    ItemRepository itemRepository;
    @Mock
    ItemMapperImpl itemMapper;
    @Mock
    Pageable pageable;
    ItemResponse itemResponse;

    @BeforeEach
    void init() {

        itemResponse = ItemResponse.builder()
                .id(BigDecimal.valueOf(1))
                .type("type")
                .title("title")
                .rare("rare")
                .quality("quality")
                .price(10.00)
                .iconItemId("iconItemId")
                .winchance(10.00)
                .build();
    }

    @Test
    @Order(1)
    public void getAllItemsTest() {
        List<ItemResponse> items = new ArrayList<>();
        items.add(itemResponse);
        Page<ItemResponse> itemPage = new PageImpl<>(items);

        when(itemMapper.modelToDto(itemRepository.findAll(pageable))).thenReturn(itemPage);

        Page<ItemResponse> result = itemService.getAllItems(pageable);

        assertNotNull(result);
        assertEquals("title", result.getContent().get(0).getTitle());
        verify(itemRepository, times(2)).findAll(any(Pageable.class));
    }
}
