package autotests;

import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.TestCaseRunner;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import org.springframework.http.HttpStatus;

//перед началом работы с тестом необходимо создать уточек (через БД или Swagger):
/*пример через БД:
insert into DUCK values
(1, 'yellow', 10, 'rubber', 'quack', 'ACTIVE'),
(2, 'yellow', 10, 'wood', 'quack', 'FIXED');
обновлять создание после прохождений тестов*/

public class TestDuckProperties extends StartTestsForWorkWithDucks {

    @Test(description = "Получение свойств уточки с четным числом и material = wood")
    @CitrusTest
    public void getPropertiesFromDuckWithEvenIDAndWoodMaterial(@Optional @CitrusResource TestCaseRunner runner) {
        duckProperties(runner, "2");
        validateResponse(runner, HttpStatus.OK,
                "{}");
    }

    @Test(description = "Получение свойств уточки с нечетным ID и material = rubber")
    @CitrusTest
    public void getPropertiesFromDuckWithOddIDAndRubberMaterial(@Optional @CitrusResource TestCaseRunner runner) {
        duckProperties(runner, "1");
        validateResponse(runner, HttpStatus.OK,
                "{\"color\": \"yellow\", \"height\": 1000.0, \"material\": \"rubber\", " +
                        "\"sound\": \"quack\", \"wingsState\":\"ACTIVE\"}"); //почему "height": 1000.0 так и не поняла, в базе 10.0, а сваггер видит 1000.0
    }
}
