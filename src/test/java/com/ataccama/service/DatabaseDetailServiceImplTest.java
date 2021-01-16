package com.ataccama.service;

import com.ataccama.model.DatabaseDetail;
import com.ataccama.model.DatabaseDetailDto;
import com.ataccama.repository.DatabaseDetailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration(exclude = LiquibaseAutoConfiguration.class)
class DatabaseDetailServiceImplTest {

    private static final String HTTP_LOCALHOST = "http://localhost:";
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private DatabaseDetailRepository detailRepository;

    private List<DatabaseDetail> detailDtoList;

    @BeforeEach
    public void populateDB() {
        DatabaseDetail databaseDetailFirst = new DatabaseDetail();
        databaseDetailFirst.setName("firstName");
        databaseDetailFirst.setHostName("111.1.1.1");
        databaseDetailFirst.setPort(1111);
        databaseDetailFirst.setDatabaseName("firstDBName");
        databaseDetailFirst.setUserName("firstUserName");
        databaseDetailFirst.setPassword("firstUserPassword");

        DatabaseDetail databaseDetailSecond = new DatabaseDetail();
        databaseDetailSecond.setName("secondName");
        databaseDetailSecond.setHostName("222.2.2.2");
        databaseDetailSecond.setPort(222);
        databaseDetailSecond.setDatabaseName("secondDBName");
        databaseDetailSecond.setUserName("secondUserName");
        databaseDetailSecond.setPassword("secondUserPassword");

        detailDtoList = Arrays.asList(databaseDetailFirst, databaseDetailSecond);

        detailRepository.saveAll(detailDtoList);
    }

    @Test
    void testFindById() {
        DatabaseDetail databaseDetail = detailDtoList.get(0);
        String uuid = databaseDetail.getUuid();
        DatabaseDetailDto dtoById = this.restTemplate.getForObject(HTTP_LOCALHOST + port + "/api/connection/" + uuid, DatabaseDetailDto.class);
        assertThat(dtoById).isNotNull();
        compareDtoAndEntity(databaseDetail, dtoById);
    }

    @Test
    void testSearchAllConnections() {
        List<LinkedHashMap<String, Object>> response = this.restTemplate.getForObject(HTTP_LOCALHOST + port + "/api/allConnections", List.class);
        assertThat(response).isNotNull();

        checkDataPesponse(response, 0);
        checkDataPesponse(response, 1);
    }

    private void checkDataPesponse(List<LinkedHashMap<String, Object>> response, int index) {
        LinkedHashMap<String, Object> rawDataResponse = response.get(index);
        compareDtoAndEntity(detailDtoList.get(index), convertResponseToDto(rawDataResponse));
    }

    private DatabaseDetailDto convertResponseToDto(LinkedHashMap<String, Object> stringStringLinkedHashMap) {
        return DatabaseDetailDto.builder()
                                .name((String) stringStringLinkedHashMap.get("name"))
                                .hostName((String) stringStringLinkedHashMap.get("hostName"))
                                .port((Integer) stringStringLinkedHashMap.get("port"))
                                .databaseName((String) stringStringLinkedHashMap.get("databaseName"))
                                .userName((String) stringStringLinkedHashMap.get("userName"))
                                .password((String) stringStringLinkedHashMap.get("password"))
                                .build();
    }

    private void compareDtoAndEntity(DatabaseDetail entity, DatabaseDetailDto dto) {
        assertThat(dto.getName()).isEqualTo(entity.getName());
        assertThat(dto.getHostName()).isEqualTo(entity.getHostName());
        assertThat(dto.getPort()).isEqualTo(entity.getPort());
        assertThat(dto.getDatabaseName()).isEqualTo(entity.getDatabaseName());
        assertThat(dto.getUserName()).isEqualTo(entity.getUserName());
        assertThat(dto.getPassword()).isEqualTo(entity.getPassword());
    }

}