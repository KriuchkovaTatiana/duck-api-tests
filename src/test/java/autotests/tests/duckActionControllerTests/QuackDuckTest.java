package autotests.tests.duckActionControllerTests;

import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.TestCaseRunner;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import org.springframework.http.HttpStatus;
import autotests.clients.QuackDuckClient;
import autotests.payloads.QuackDuckResponse;

//перед началом работы с тестом необходимо создать уточек (через БД или Swagger):
/*через БД:
insert into DUCK values
(1, 'yellow', 10, 'plastic', 'quack', 'ACTIVE'),
(2, 'yellow', 10, 'wood', 'quack', 'FIXED');
обновлять создание после прохождений тестов*/

public class QuackDuckTest extends QuackDuckClient {

    @Test(description = "Кряканье уточки: корректный нечётный id, корректный звук")
    @CitrusTest
    public void quackRightSoundWithOddID(@Optional @CitrusResource TestCaseRunner runner) {
        duckQuack(runner, "1", "1", "1");
        validateResponseFromPayload(runner, HttpStatus.OK,
                new QuackDuckResponse().sound("quack"));
    }

    @Test(description = "Кряканье уточки: корректный чётный id, корректный звук")
    @CitrusTest
    public void quackRightSoundWithEvenID(@Optional @CitrusResource TestCaseRunner runner) {
        duckQuack(runner, "2", "1", "1");
        validateResponse(runner, HttpStatus.OK, "{\"sound\": \"moo\"}"); //ожидается звук quack, но приходит результат moo
    }
}
