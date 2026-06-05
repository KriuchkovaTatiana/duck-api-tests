package autotests.tests.duckControllerTests;

import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.TestCaseRunner;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import org.springframework.http.HttpStatus;
import autotests.clients.CreateDuckClient;
import autotests.payloads.PostApiDuckCreate;


public class CreateDuckTest extends CreateDuckClient {

    @Test(description = "Создание уточки с material = rubber")
    @CitrusTest
    public void createRubberDuck(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner,
                new PostApiDuckCreate().color("yellow").height(10.0)
                        .material("rubber").sound("quack").wingsState("ACTIVE"));
        validateResponseFromResources(runner, HttpStatus.OK, "responses/createDuckWithMaterialRubber.json");
    }

    @Test(description = "Создание уточки с material = wood")
    @CitrusTest
    public void createWoodDuck(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner,
                new PostApiDuckCreate().color("yellow").height(10.0)
                        .material("wood").sound("quack").wingsState("ACTIVE"));
        validateResponseFromResources(runner, HttpStatus.OK, "responses/createDuckWithMaterialWood.json");
    }
}
