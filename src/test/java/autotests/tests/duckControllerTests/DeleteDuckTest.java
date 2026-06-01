package autotests.tests.duckControllerTests;

import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.TestCaseRunner;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import org.springframework.http.HttpStatus;
import autotests.clients.DeleteDuckClient;

public class DeleteDuckTest extends DeleteDuckClient {

    @Test(description = "Удаление уточки")
    @CitrusTest
    public void deleteDuckTest(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 10.0, "rubber", "quack",
                "ACTIVE");
        duckId(runner);
        duckDelete(runner, "${duckId}");
        validateResponse(runner, HttpStatus.OK, "{\"message\": \"Duck is deleted\"}");
    }
}
