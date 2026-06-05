package autotests.tests.duckActionControllerTests;

import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.TestCaseRunner;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import org.springframework.http.HttpStatus;
import autotests.clients.PropertiesDuckClient;
import autotests.payloads.DuckPropertiesResponse;

//перед началом работы с тестом необходимо создать уточек (через БД или Swagger):
/*пример через БД:
insert into DUCK values
(1, 'yellow', 10, 'rubber', 'quack', 'ACTIVE'),
(2, 'yellow', 10, 'wood', 'quack', 'FIXED');
обновлять создание после прохождений тестов*/

public class PropertiesDuckTest extends PropertiesDuckClient {

    @Test(description = "Получение свойств уточки с четным числом и material = wood")
    @CitrusTest
    public void getPropertiesFromDuckWithEvenIDAndWoodMaterial(@Optional @CitrusResource TestCaseRunner runner) {
        duckProperties(runner, "2");
        validateResponseFromResources(runner, HttpStatus.OK,
                "responses/duckPropertiesWhereIDIsEven.json"); //ожидается получение свойств уточки, но приходит пустой результат
    }

    @Test(description = "Получение свойств уточки с нечетным ID и material = rubber")
    @CitrusTest
    public void getPropertiesFromDuckWithOddIDAndRubberMaterial(@Optional @CitrusResource TestCaseRunner runner) {
        duckProperties(runner, "1");
        validateResponseFromPayload(runner, HttpStatus.OK,
                new DuckPropertiesResponse().color("yellow").height(1000.0)
                        .material("rubber").sound("quack").wingsState("ACTIVE")); //"height": 1000.0 - в базе 10.0, из АПИ приходит 1000.0
    }
}
