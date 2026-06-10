package autotests.tests.duckActionControllerTests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import org.springframework.http.HttpStatus;
import autotests.clients.PropertiesDuckClient;
import autotests.payloads.DuckPropertiesResponse;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

@Epic("Тесты на duck-action-controller")
@Feature("Характеристики уточки")
@Story("Эндпоинт /api/duck/action/properties")
public class PropertiesDuckTest extends PropertiesDuckClient {

    @Test(description = "Получение свойств уточки с четным числом и material = wood")
    @CitrusTest
    public void getPropertiesFromDuckWithEvenIDAndWoodMaterial(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "2", "yellow", "10", "wood", "quack", "FIXED");
        duckProperties(runner, "2");
        validateResponseFromResources(runner, HttpStatus.OK,
                "responses/duckPropertiesWhereIDIsEven.json");
        deleteDuckFromDatabase(runner, "2");
    }

    @Test(description = "Получение свойств уточки с нечетным ID и material = rubber")
    @CitrusTest
    public void getPropertiesFromDuckWithOddIDAndRubberMaterial(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "1", "yellow", "10", "rubber", "quack", "ACTIVE");
        duckProperties(runner, "1");
        validateResponseFromPayload(runner, HttpStatus.OK,
                new DuckPropertiesResponse().color("yellow").height(10.0)
                        .material("rubber").sound("quack").wingsState("ACTIVE"));
        deleteDuckFromDatabase(runner, "1");
    }
}
