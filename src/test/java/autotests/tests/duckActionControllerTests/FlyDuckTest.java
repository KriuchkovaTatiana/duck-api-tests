package autotests.tests.duckActionControllerTests;

import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.TestCaseRunner;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import org.springframework.http.HttpStatus;
import autotests.clients.FlyDuckClient;
import autotests.payloads.PostApiDuckCreate;
import autotests.payloads.MessageAboutDuckResponse;

public class FlyDuckTest extends FlyDuckClient {

    @Test(description = "Полет уточки: существующий id с активными крыльями")
    @CitrusTest
    public void flyWithActiveWings(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner,
                new PostApiDuckCreate().color("yellow").height(10.0).material("rubber")
                .sound("quack").wingsState("ACTIVE"));
        duckId(runner);
        duckFly(runner, "${duckId}");
        validateResponseFromResources(runner, HttpStatus.OK,
                "responses/flyWithActiveWings.json");
        duckDelete(runner, "${duckId}");
    }

    @Test(description = "Полет уточки: существующий id со связанными крыльями")
    @CitrusTest
    public void flyWithFixedWings(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, new PostApiDuckCreate().color("yellow").height(10.0).material("rubber")
                .sound("quack").wingsState("FIXED"));
        duckId(runner);
        duckFly(runner, "${duckId}");
        validateResponseFromPayload(runner, HttpStatus.OK,
                new MessageAboutDuckResponse().message("I can not fly :C"));
        duckDelete(runner, "${duckId}");
    }

    @Test(description = "Полет уточки: существующий id c крыльями в неопределенном состоянии")
    @CitrusTest
    public void flyWithUndefinedWings(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner,new PostApiDuckCreate().color("yellow").height(10.0).material("rubber")
                .sound("quack").wingsState("UNDEFINED"));
        duckId(runner);
        duckFly(runner, "${duckId}");
        validateResponse(runner, HttpStatus.OK, "{\"message\": \"Wings are not detected :(\"}");
        duckDelete(runner, "${duckId}");
    }

}
