package autotests.tests.duckActionControllerTests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import org.springframework.http.HttpStatus;
import autotests.clients.PropertiesDuckClient;
import autotests.payloads.DuckPropertiesResponse;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import lombok.Setter;
import lombok.experimental.Accessors;

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

    //параметризированный тест
    @Setter
    @Accessors(fluent = true)
    public static class DuckProperties {
        String id;
        String color;
        String height;
        String material;
        String sound;
        String wingsState;
}

    DuckProperties duckProperties1 = new DuckProperties()
            .id("1")
            .color("yellow")
            .height("10")
            .material("rubber")
            .sound("quack")
            .wingsState("ACTIVE");

    DuckProperties duckProperties2 = new DuckProperties()
            .id("2")
            .color("red")
            .height("15")
            .material("wood")
            .sound("quack-quack")
            .wingsState("FIXED");

    DuckProperties duckProperties3 = new DuckProperties()
            .id("3")
            .color("blue")
            .height("5")
            .material("rubber")
            .sound("quack")
            .wingsState("UNDEFINED");

    DuckProperties duckProperties4 = new DuckProperties()
            .id("4")
            .color("green")
            .height("20")
            .material("wood")
            .sound("squeak")
            .wingsState("FIXED");

    DuckProperties duckProperties5 = new DuckProperties()
            .id("5")
            .color("orange")
            .height("12")
            .material("rubber")
            .sound("honk")
            .wingsState("ACTIVE");

    @Test(description = "Параметризированный тест на получение свойств уточки",
            dataProvider = "duckPropertiesTests")
    @CitrusTest
    @CitrusParameters({"data", "response", "runner"})
    public void getPropertiesTests(DuckProperties data, String response,
                                   @Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, data.id, data.color, data.height, data.material, data.sound, data.wingsState);
        duckProperties(runner, data.id);
        validateResponseFromResources(runner, HttpStatus.OK, response);
        deleteDuckFromDatabase(runner, data.id);
    }

    @DataProvider(name = "duckPropertiesTests")
    public Object[][] duckPropertiesProvider() {
        return new Object[][] {
                {duckProperties1, "responses/duckProperties1.json", null},
                {duckProperties2, "responses/duckProperties2.json", null},
                {duckProperties3, "responses/duckProperties3.json", null},
                {duckProperties4, "responses/duckProperties4.json", null},
                {duckProperties5, "responses/duckProperties5.json", null}
        };
    }

}
