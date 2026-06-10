package autotests.tests.duckActionControllerTests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import org.springframework.http.HttpStatus;
import autotests.clients.QuackDuckClient;
import autotests.payloads.QuackDuckResponse;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

@Epic("Тесты на duck-action-controller")
@Feature("Кряканье уточки")
@Story("Эндпоинт /api/duck/action/quack")
public class QuackDuckTest extends QuackDuckClient {

    @Test(description = "Кряканье уточки: корректный нечётный id, корректный звук")
    @CitrusTest
    public void quackRightSoundWithOddID(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "1", "yellow", "10", "plastic", "quack", "ACTIVE");
        duckQuack(runner, "1", "1", "1");
        validateResponseFromPayload(runner, HttpStatus.OK,
                new QuackDuckResponse().duckSpeech("quack"));
        deleteDuckFromDatabase(runner, "1");
    }

    @Test(description = "Кряканье уточки: корректный чётный id, корректный звук")
    @CitrusTest
    public void quackRightSoundWithEvenID(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "2", "yellow", "10", "wood", "quack", "FIXED");
        duckQuack(runner, "2", "1", "1");
        validateResponse(runner, HttpStatus.OK, "{\"duckSpeech\": \"quack\"}"); //ожидается звук quack, но приходит результат moo
        deleteDuckFromDatabase(runner, "2");
    }
}
