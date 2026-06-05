package autotests.tests.duckControllerTests;

import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.TestCaseRunner;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import org.springframework.http.HttpStatus;
import autotests.clients.UpdateDuckClient;
import autotests.payloads.PostApiDuckCreate;

public class UpdateDuckTest extends UpdateDuckClient {

    @Test(description = "Изменение цвета и высоты уточки")
    @CitrusTest
    public void updateColorAndHeightOfDuck(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner,
                new PostApiDuckCreate().color("yellow").height(10.0).material("rubber")
                .sound("quack").wingsState("ACTIVE"));
        duckId(runner);
        duckUpdate(runner, "red", "1.0", "${duckId}", "rubber", "quack");
        validateResponse(runner, HttpStatus.OK,
                "{\"message\": \"Duck with id = ${duckId} is updated\"}");
        duckDelete(runner, "${duckId}");
        validateResponseFromResources(runner, HttpStatus.OK, "responses/deleteDuck.json");
    }

    @Test(description = "Изменение цвета и звука уточки")
    @CitrusTest
    public void updateColorAndSoundOfDuck(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, new PostApiDuckCreate().color("yellow").height(10.0).material("rubber")
                .sound("quack").wingsState("ACTIVE"));
        duckId(runner);
        duckUpdate(runner, "blue", "10.0", "${duckId}", "rubber", "quack-quack");
        validateResponse(runner, HttpStatus.OK,
                "{\"message\": \"Duck with id = ${duckId} is updated\"}");
        duckDelete(runner, "${duckId}");
        validateResponseFromResources(runner, HttpStatus.OK, "responses/deleteDuck.json");
    }
}
