package fr.uga.miage.m1;

import lombok.extern.java.Log;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

import static org.hamcrest.Matchers.endsWith;
import static org.springframework.test.util.AssertionErrors.fail;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
@Log
@SpringBootTest(classes = RestServer.class , webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class RequestBaseTest {
    private RestTemplate restTemplate;
    private MockRestServiceServer mockServer;


    @BeforeEach
    public void initTest(){
        restTemplate = new RestTemplate();
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @AfterEach
    public void after(){
      mockServer.reset();
    }

    @Test
    @DisplayName("test endpoint createGame/maxTurnCount={maxTurnCount}")
    void createGameEndPoint(){
        //simulation server
        mockServer.expect(requestTo(endsWith("createGame/maxTurnCount=20")))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                        .body(String.valueOf(true)));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept","application/json");
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<Boolean> response = restTemplate.exchange("/createGame/maxTurnCount=20", HttpMethod.PUT,entity,Boolean.class);
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertInstanceOf(Boolean.class,response.getBody());
        mockServer.verify();
    }


    @Test
    @DisplayName("test endpoint /initialState with gamepool is empty")
    void testEndPointInitualGameWithGamePoolEmpty(@Value("classpath:initialGamePool.json") Resource serverResponse) {
        mockServer.expect(requestTo(endsWith("/initialState")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(serverResponse));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<Collection> games = restTemplate.getForEntity("/initialState",Collection.class);
        Assertions.assertEquals(HttpStatus.OK,games.getStatusCode());
        Assertions.assertNotNull(games.getBody());
        Assertions.assertEquals(2, games.getBody().size());
        mockServer.verify();
    }

}
