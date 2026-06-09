package autotests.tests.duckActionControllerTests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import org.springframework.http.HttpStatus;
import autotests.clients.SwimDuckClient;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

@Epic("Тесты на duck-action-controller")
@Feature("Плавание уточки")
@Story("Эндпоинт /api/duck/action/swim")
public class SwimDuckTest extends SwimDuckClient {

    @Test(description = "Плавание уточки: существующий id")
    @CitrusTest
    public void swimRealId(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "1", "yellow", "10", "rubber", "quack", "ACTIVE");
        duckSwim(runner, "1");
        validateResponseFromResources(runner, HttpStatus.NOT_FOUND,
                "responses/swimWhenPawsNotFound.json");
        deleteDuckFromDatabase(runner, "1");
    }

    @Test(description = "Плавание уточки: несуществующий id")
    @CitrusTest
    public void swimNotRealId(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "2", "yellow", "10", "rubber", "quack", "ACTIVE");
        deleteDuckFromDatabase(runner, "2");
        duckSwim(runner, "2");
        validateResponse(runner, HttpStatus.NOT_FOUND, "{\"message\": \"Paws are not found ((((\"}");
    }
}