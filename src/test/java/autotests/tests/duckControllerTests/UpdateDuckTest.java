package autotests.tests.duckControllerTests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import org.springframework.http.HttpStatus;
import autotests.clients.UpdateDuckClient;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

@Epic("Тесты на duck-controller")
@Feature("Изменение характеристик уточки")
@Story("Эндпоинт /api/duck/update")

//изменение характеристик уточки через БД
public class UpdateDuckTest extends UpdateDuckClient {

    @Test(description = "Изменение цвета и высоты уточки")
    @CitrusTest
    public void updateColorAndHeightOfDuck(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "1", "yellow", "10", "rubber", "quack", "ACTIVE");
        duckUpdate(runner, "red", "1.0", "1", "rubber", "quack");
        validateResponse(runner, HttpStatus.OK,
                "{\"message\": \"Duck with id = 1 is updated\"}");
        validateDuckInDatabase(runner, "1", "red", "100.0", "rubber", "quack", "ACTIVE");
        deleteDuckFromDatabase(runner, "1");
    }

    @Test(description = "Изменение цвета и звука уточки")
    @CitrusTest
    public void updateColorAndSoundOfDuck(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "2", "yellow", "10", "rubber", "quack", "ACTIVE");
        duckUpdate(runner, "blue", "10.0", "2", "rubber", "quack-quack");
        validateResponse(runner, HttpStatus.OK,
                "{\"message\": \"Duck with id = 2 is updated\"}");
        validateDuckInDatabase(runner, "2", "blue", "10.0", "rubber", "quack", "ACTIVE");
        deleteDuckFromDatabase(runner, "2");
    }
}
