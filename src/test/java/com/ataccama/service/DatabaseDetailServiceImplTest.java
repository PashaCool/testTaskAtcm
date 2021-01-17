package com.ataccama.service;

import com.ataccama.exception.NotUniqueInnerDbNameException;
import com.ataccama.model.DatabaseDetail;
import com.ataccama.model.DatabaseDetailDto;
import com.ataccama.repository.DatabaseDetailRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DatabaseDetailServiceImplTest {

    @Mock
    private DatabaseDetailRepository dbDetailRepository;
    @Mock
    private DatabaseDetailMapper databaseDetailMapper;
    @InjectMocks
    private DatabaseDetailServiceImpl databaseDetailService;

    @Test
    void testSaveNotUniqueDbName() {
        DatabaseDetailDto newConnection = DatabaseDetailDto.builder()
                                                           .name("1")
                                                           .hostName("hostName")
                                                           .port(1111)
                                                           .databaseName("databaseName")
                                                           .userName("userName")
                                                           .password("password")
                                                           .build();
        DatabaseDetail predicate = new DatabaseDetail();
        predicate.setName(newConnection.getName());

        when(dbDetailRepository.exists(Example.of(predicate))).thenReturn(true);

        Assertions.assertThrows(NotUniqueInnerDbNameException.class, () -> databaseDetailService.createDbConnection(newConnection));
    }

}