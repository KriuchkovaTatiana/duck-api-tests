package autotests.tests.duckControllerTests;

import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.TestCaseRunner;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import autotests.clients.CreateDuckClient;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

@Epic("Тесты на duck-controller")
@Feature("Создание уточки")
@Story("Эндпоинт /api/duck/create")
public class CreateDuckTest extends CreateDuckClient {

    @Test(description = "Создание уточки с material = rubber")
    @CitrusTest
    public void createRubberDuck(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "1", "yellow", "10", "rubber", "quack", "ACTIVE");
        validateDuckInDatabase(runner, "1", "yellow", "10.0", "rubber", "quack", "ACTIVE");
        deleteDuckFromDatabase(runner, "1");
    }

    @Test(description = "Создание уточки с material = wood")
    @CitrusTest
    public void createWoodDuck(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "2", "yellow", "10", "wood", "quack", "ACTIVE");
        validateDuckInDatabase(runner, "2", "yellow", "10.0", "wood", "quack", "ACTIVE");
        deleteDuckFromDatabase(runner, "2");
    }
}
