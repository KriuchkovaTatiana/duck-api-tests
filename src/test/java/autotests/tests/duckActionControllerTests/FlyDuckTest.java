package autotests.tests.duckActionControllerTests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import org.springframework.http.HttpStatus;
import autotests.clients.FlyDuckClient;
import autotests.payloads.MessageAboutDuckResponse;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

@Epic("Тесты на duck-action-controller")
@Feature("Полёт уточки")
@Story("Эндпоинт /api/duck/action/fly")
public class FlyDuckTest extends FlyDuckClient {

    @Test(description = "Полет уточки: существующий id с активными крыльями")
    @CitrusTest
    public void flyWithActiveWings(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "1", "yellow", "10", "rubber", "quack", "ACTIVE");
        duckFly(runner, "1");
        validateResponseFromResources(runner, HttpStatus.OK,
                "responses/flyWithActiveWings.json");
        deleteDuckFromDatabase(runner, "1");
    }

    @Test(description = "Полет уточки: существующий id со связанными крыльями")
    @CitrusTest
    public void flyWithFixedWings(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "2", "yellow", "10", "rubber", "quack", "FIXED");
        duckFly(runner, "2");
        validateResponseFromPayload(runner, HttpStatus.OK,
                new MessageAboutDuckResponse().message("I can not fly :C"));
        deleteDuckFromDatabase(runner, "2");
    }

    @Test(description = "Полет уточки: существующий id c крыльями в неопределенном состоянии")
    @CitrusTest
    public void flyWithUndefinedWings(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "3", "yellow", "10", "rubber", "quack", "UNDEFINED");
        duckFly(runner, "3");
        validateResponse(runner, HttpStatus.OK, "{\"message\": \"Wings are not detected :(\"}");
        deleteDuckFromDatabase(runner, "3");
    }

}
