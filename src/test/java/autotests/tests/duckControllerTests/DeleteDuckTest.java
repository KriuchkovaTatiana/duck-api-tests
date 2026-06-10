package autotests.tests.duckControllerTests;

import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.TestCaseRunner;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import org.springframework.http.HttpStatus;
import autotests.clients.DeleteDuckClient;
import autotests.payloads.PostApiDuckCreate;

public class DeleteDuckTest extends DeleteDuckClient {

    @Test(description = "Удаление уточки")
    @CitrusTest
    public void deleteDuckTest(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner,
                new PostApiDuckCreate().color("yellow").height(10.0)
                        .material("rubber").sound("quack").wingsState("ACTIVE"));
        duckId(runner);
        duckDelete(runner, "${duckId}");
        validateResponseFromResources(runner, HttpStatus.OK, "responses/deleteDuck.json");
    }
}
