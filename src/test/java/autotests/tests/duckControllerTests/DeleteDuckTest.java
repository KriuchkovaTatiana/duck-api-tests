package autotests.tests.duckControllerTests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import autotests.clients.DeleteDuckClient;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

@Epic("Тесты на duck-controller")
@Feature("Удаление уточки")
@Story("Эндпоинт /api/duck/delete")
public class DeleteDuckTest extends DeleteDuckClient {

    @Test(description = "Удаление уточки")
    @CitrusTest
    public void deleteDuckTest(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "1", "yellow", "10", "rubber", "quack", "ACTIVE");
        validateDuckInDatabase(runner, "1", "yellow", "10.0", "rubber", "quack", "ACTIVE");
        deleteDuckFromDatabase(runner, "1");
        validateDuckDeletedFromDatabase(runner, "1");
    }
}
