package fr.uga.miage.m1;

import fr.uga.miage.m1.exceptions.StrategyException;
import fr.uga.miage.m1.models.game.Game;
import fr.uga.miage.m1.models.player.Player;
import fr.uga.miage.m1.models.strategy.StrategyFactory;
import fr.uga.miage.m1.sharedstrategy.StrategyChoice;
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
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@Log
@SpringBootTest(classes = RestServer.class , webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class RequestGameTest {
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
    @DisplayName(" test end point /play/gameId={gameId}/playerId={playerId}/move={move}")
    void endPointPlayInGame(){
        mockServer.expect(requestTo(endsWith("/play/gameId=1/playerId=1/move=DEFECT")))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                        .body(String.valueOf(true)));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept","application/json");
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<Boolean> response = restTemplate.exchange("/play/gameId=1/playerId=1/move="+ StrategyChoice.DEFECT, HttpMethod.PUT,entity,Boolean.class);
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertInstanceOf(Boolean.class,response.getBody());
        Assertions.assertEquals(true,response.getBody());
        mockServer.verify();
    }

    @Test
    @DisplayName("test end point /allStrategies")
    void allStrategies(@Value("classpath:AllStrategies.json")Resource serverResponse){
        mockServer.expect(requestTo(endsWith("/allStrategies")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                        .body(serverResponse));

        ResponseEntity<Collection> response = restTemplate.getForEntity("/allStrategies",Collection.class);
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertInstanceOf(Collection.class,response.getBody());
        mockServer.verify();
    }


    @Test
    @DisplayName("test end point (set strategy) /{gameId}/{playerId}/{strategy}")
    void testSetStrategy() throws StrategyException {
        mockServer.expect(requestTo(endsWith("/1/1/PAVLOV")))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept","application/json");
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity response = restTemplate.exchange("/1/1/"+ StrategyFactory.getStrategyFromType("PAVLOV").getUniqueId(), HttpMethod.PUT,entity,void.class);
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        mockServer.verify();
    }

    @Test
    @DisplayName("test end point /allMoves")
    void testAllMoves(@Value("classpath:allMoves.json") Resource serverResponse){
        mockServer.expect(requestTo(endsWith("/allMoves")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                        .body(serverResponse));

        ResponseEntity<StrategyChoice[]> response = restTemplate.getForEntity("/allMoves",StrategyChoice[].class);
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertInstanceOf(StrategyChoice[].class,response.getBody());
        Assertions.assertArrayEquals(response.getBody(),StrategyChoice.values());
    }

    @Disabled
    @Test
    @DisplayName("test end point /initialState/gameId={gameId}")
    void testInitialGame(@Value("classpath:gameResponse.json") Resource serverResponse){
        mockServer.expect(requestTo(endsWith("initialState/gameId=1")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                        .body(serverResponse));
        ResponseEntity<Game> response = restTemplate.getForEntity("/initialState/gameId=1",Game.class);
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertInstanceOf(Game.class,response.getBody());
        Assertions.assertEquals(1,response.getBody().getId());
    }

    @Disabled
    @Test
    @DisplayName("test end point join/gameId={gameId}/playerName={playerName}")
    void testJoinGame(@Value("classpath:initialPlayer.json") Resource serverResponse){
        mockServer.expect(requestTo(endsWith("join/gameId=1/playerName=Nicolas")))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                        .body(serverResponse));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept","application/json");
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<Player> response = restTemplate.exchange("join/gameId=1/playerName=Nicolas",HttpMethod.PUT,entity,Player.class);
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertInstanceOf(Player.class,response.getBody());
        Assertions.assertEquals("Nicolas",response.getBody().getName());
    }

    @Disabled
    @Test
    @DisplayName("test end point {gameId}/player/{playerId}")
    void testGetPlayer(@Value("classpath:initialPlayer.json") Resource serverResponse){
        mockServer.expect(requestTo(endsWith("1/player/1")))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                        .body(serverResponse));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept","application/json");
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<Player> response = restTemplate.exchange("1/player/1",HttpMethod.PUT,entity,Player.class);
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertInstanceOf(Player.class,response.getBody());
        Assertions.assertEquals("Nicolas",response.getBody().getName());
    }

    @Nested
    class TestSseEmitter{


        @Disabled
        @Test
        @DisplayName("test end point /viewGame/{gameId}")
        void testViewGame(){}

        @Disabled
        @Test
        @DisplayName("test end point waitLastPlayer/gameId={gameId}")
        void testWaitLastPlayer(){

        }

        @Disabled
        @Test
        @DisplayName("test end point /waitPlayerPlay/gameId={gameId}")
        void testWaitPlayPlayer(){

        }
    }

}
