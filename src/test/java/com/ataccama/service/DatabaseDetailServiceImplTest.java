package com.ataccama.service;

import com.ataccama.controller.DatabaseDetailController;
import com.ataccama.model.DatabaseDetail;
import com.ataccama.model.DatabaseDetailDto;
import com.ataccama.repository.DatabaseDetailRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

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
    @Autowired
    private DatabaseDetailMapper mapper;
    @Autowired
    private DatabaseDetailController detailController;

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

    @AfterEach
    public void cleanDB() {
        detailRepository.deleteAll();
    }

    @Test
    @DisplayName("test endpoint GET /api/connection/{uuid}")
    void testFindById() {
        DatabaseDetail databaseDetail = detailDtoList.get(0);
        String uuid = databaseDetail.getUuid();
        DatabaseDetailDto dtoById = this.restTemplate.getForObject(HTTP_LOCALHOST + port + "/api/connection/" + uuid, DatabaseDetailDto.class);
        assertThat(dtoById).isNotNull();
        compareEntityAndDto(databaseDetail, dtoById);
    }

    @Test
    @DisplayName("test endpoint GET /api/connection/all")
    void testSearchAllConnections() {
        List<LinkedHashMap<String, Object>> response = this.restTemplate.getForObject(HTTP_LOCALHOST + port + "/api/connection/all", List.class);
        assertThat(response).isNotNull();

        checkDataResponse(response, 0);
        checkDataResponse(response, 1);
    }

    @Test
    @DisplayName("test endpoint POST /api/create")
    void testCreateConnection() throws Exception {
        String thirdDatabaseName = "train_station";
        DatabaseDetailDto newConnection = DatabaseDetailDto.builder()
                                                           .name(thirdDatabaseName)
                                                           .hostName("thirdHostName")
                                                           .port(3333)
                                                           .databaseName("thirdDatabaseName")
                                                           .userName("thirdUserName")
                                                           .password("thirdPassword")
                                                           .build();

        detailController.createConnection(newConnection);

        DatabaseDetail foundDto = detailRepository.findByName(thirdDatabaseName)
                                                  .orElseThrow(() -> new Exception("Created entity not found"));
        compareEntityAndDto(foundDto, newConnection);
    }

    @Test
    @DisplayName("test endpoint PUT /api/connection/update")
    void testUpdateConnection() throws Exception {
        String firstDBName = "firstName";
        DatabaseDetail firstEntityVersion = detailRepository.findByName(firstDBName)
                                                            .orElseThrow(() -> new Exception("Created entity not found"));

        DatabaseDetailDto postedVersion = DatabaseDetailDto.builder()
                                                           .uuid(firstEntityVersion.getUuid())
                                                           .name("updatedName")
                                                           .hostName("updatedHostName")
                                                           .port(port)
                                                           .databaseName("updatedDatabaseName")
                                                           .userName("updatedUserName")
                                                           .password("updatedUserPassword")
                                                           .build();

        this.restTemplate.put(HTTP_LOCALHOST + port + "/api/connection/update", postedVersion);

        DatabaseDetail updatedEntity = detailRepository.findById(firstEntityVersion.getUuid())
                                                       .orElseThrow(() -> new Exception("updated entity not found"));

        compareEntityAndDto(updatedEntity, postedVersion);
    }

    @Test
    @DisplayName("test endpoint DELETE /api/connection/{uuid}")
    void testDeleteConnection() {
        Iterable<DatabaseDetail> start = detailRepository.findAll();
        long beforeDelete = iterableSize(start);

        DatabaseDetail secondDBName = detailRepository.findByName("secondName")
                                                      .orElseThrow();

        this.restTemplate.delete(HTTP_LOCALHOST + port + "/api/connection/" + secondDBName.getUuid(), Map.of("uuid", secondDBName.getUuid()));

        Iterable<DatabaseDetail> finish = detailRepository.findAll();
        long afterDelete = iterableSize(finish);

        assertThat(beforeDelete).isEqualTo(afterDelete + 1);
    }

    private long iterableSize(Iterable<?> collection) {
        return StreamSupport.stream(collection.spliterator(), false).count();
    }

    private void checkDataResponse(List<LinkedHashMap<String, Object>> response, int index) {
        LinkedHashMap<String, Object> rawDataResponse = response.get(index);
        compareEntityAndDto(detailDtoList.get(index), convertResponseToDto(rawDataResponse));
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

    private void compareEntityAndDto(DatabaseDetail entity, DatabaseDetailDto dto) {
        assertThat(dto.getName()).isEqualTo(entity.getName());
        assertThat(dto.getHostName()).isEqualTo(entity.getHostName());
        assertThat(dto.getPort()).isEqualTo(entity.getPort());
        assertThat(dto.getDatabaseName()).isEqualTo(entity.getDatabaseName());
        assertThat(dto.getUserName()).isEqualTo(entity.getUserName());
        assertThat(dto.getPassword()).isEqualTo(entity.getPassword());
    }

}