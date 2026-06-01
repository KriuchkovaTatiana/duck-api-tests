package autotests.tests;

import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.TestCaseRunner;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import org.springframework.http.HttpStatus;
import autotests.clients.DuckActionClients;

public class FlyDuckTest extends DuckActionClients {

    @Test(description = "Полет уточки: существующий id с активными крыльями")
    @CitrusTest
    public void flyWithActiveWings(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 10.0, "rubber",
                "quack", "ACTIVE");
        duckId(runner);
        duckFly(runner, "${duckId}");
        validateResponse(runner, HttpStatus.OK, "{\"message\": \"I am flying :)\"}");
        duckDelete(runner, "${duckId}");
        validateResponse(runner, HttpStatus.OK, "{\"message\": \"Duck is deleted\"}");
    }

    @Test(description = "Полет уточки: существующий id со связанными крыльями")
    @CitrusTest
    public void flyWithFixedWings(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 10.0, "rubber",
                "quack", "FIXED");
        duckId(runner);
        duckFly(runner, "${duckId}");
        validateResponse(runner, HttpStatus.OK, "{\"message\": \"I can not fly :C\"}");
        duckDelete(runner, "${duckId}");
        validateResponse(runner, HttpStatus.OK, "{\"message\": \"Duck is deleted\"}");
    }

    @Test(description = "Полет уточки: существующий id c крыльями в неопределенном состоянии")
    @CitrusTest
    public void flyWithUndefinedWings(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 10.0, "rubber",
                "quack", "UNDEFINED");
        duckId(runner);
        duckFly(runner, "${duckId}");
        validateResponse(runner, HttpStatus.OK, "{\"message\": \"Wings are not detected :(\"}");
        duckDelete(runner, "${duckId}");
        validateResponse(runner, HttpStatus.OK, "{\"message\": \"Duck is deleted\"}");
    }

}
