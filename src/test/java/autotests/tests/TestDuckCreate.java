package autotests.tests;

import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.TestCaseRunner;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import org.springframework.http.HttpStatus;
import autotests.clients.DuckActionClients;


public class TestDuckCreate extends DuckActionClients {

    @Test(description = "Создание уточки с material = rubber")
    @CitrusTest
    public void createRubberDuck(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 10.0, "rubber", "quack", "ACTIVE");
        validateResponse(runner, HttpStatus.OK,
                "{\"id\": \"@ignore@\", \"color\": \"yellow\", \"height\": 10.0," +
                        " \"material\": \"rubber\", \"sound\":\"quack\", \"wingsState\": \"ACTIVE\"}");
    }

    @Test(description = "Создание уточки с material = wood")
    @CitrusTest
    public void createWoodDuck(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 10.0, "wood", "quack", "ACTIVE");
        validateResponse(runner, HttpStatus.OK,
                "{\"id\": \"@ignore@\", \"color\": \"yellow\", \"height\": 10.0, " +
                        "\"material\": \"wood\", \"sound\":\"quack\", \"wingsState\": \"ACTIVE\"}");
    }
}
