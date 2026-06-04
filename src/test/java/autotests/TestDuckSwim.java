package autotests;

import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.TestCaseRunner;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import org.springframework.http.HttpStatus;

public class TestDuckSwim extends StartTestsForWorkWithDucks {

    @Test(description = "Плавание уточки: существующий id")
    @CitrusTest
    public void swimRealId(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 10.0, "rubber", "quack", "ACTIVE");
        duckId(runner);
        duckSwim(runner, "${duckId}");
        validateResponse(runner, HttpStatus.NOT_FOUND, "{\"message\": \"Paws are not found ((((\"}");
        duckDelete(runner, "${duckId}");
    }

    @Test(description = "Плавание уточки: несуществующий id")
    @CitrusTest
    public void swimNotRealId(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 10.0, "rubber", "quack", "ACTIVE");
        duckId(runner);
        duckDelete(runner, "${duckId}");
        duckSwim(runner, "${duckId}");
        validateResponse(runner, HttpStatus.NOT_FOUND, "{\"message\": \"Paws are not found ((((\"}");
    }
}