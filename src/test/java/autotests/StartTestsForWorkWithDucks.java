package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.testng.TestNGCitrusSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.validation.DelegatingPayloadVariableExtractor.Builder.fromBody;

public class StartTestsForWorkWithDucks extends TestNGCitrusSupport {

    private static final String URL = "http://localhost:2222";

    protected void createDuck(TestCaseRunner runner, String color, double height,
                              String material, String sound, String wingsState) {
        runner.$(http()
                .client(URL)
                .send()
                .post("/api/duck/create")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\n" +
                        "  \"color\": \"" + color + "\",\n" +
                        "  \"height\": " + height + ",\n" +
                        "  \"material\": \"" + material + "\",\n" +
                        "  \"sound\": \"" + sound + "\",\n" +
                        "  \"wingsState\": \"" + wingsState + "\"\n" +
                        "}"));
    }

    // хелперы для тестов

    //метод для извелечения переменной ID
    protected void duckId(TestCaseRunner runner) {
        runner.$(http()
                .client(URL)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId").build()));
    }

    protected void duckSwim(TestCaseRunner runner, String id) {
        runner.$(http()
                .client(URL)
                .send()
                .get("/api/duck/action/swim")
                .queryParam("id", id));
    }

    protected void duckFly(TestCaseRunner runner, String id) {
        runner.$(http()
                .client(URL)
                .send()
                .get("/api/duck/action/fly")
                .queryParam("id", id));
    }

    protected void duckUpdate(TestCaseRunner runner, String color, String height,
                              String id, String material, String sound) {
        runner.$(http()
                .client(URL)
                .send()
                .put("/api/duck/update")
                .queryParam("color", color)
                .queryParam("height", height)
                .queryParam("id", id)
                .queryParam("material", material)
                .queryParam("sound", sound));
    }

    protected void duckDelete(TestCaseRunner runner, String id) {
        runner.$(http()
                .client(URL)
                .send()
                .delete("/api/duck/delete")
                .queryParam("id", id));
    }

    protected void duckProperties(TestCaseRunner runner, String id) {
        runner.$(http()
                .client(URL)
                .send()
                .get("/api/duck/action/properties")
                .queryParam("id", id));
    }

    protected void duckQuack(TestCaseRunner runner, String id,
                             String repetitionCount, String soundCount) {
        runner.$(http()
                .client(URL)
                .send()
                .get("/api/duck/action/quack")
                .queryParam("id", id)
                .queryParam("repetitionCount", repetitionCount)
                .queryParam("soundCount", soundCount));
    }

    protected void validateResponse(TestCaseRunner runner, HttpStatus status, String body) {
        runner.$(http()
                .client(URL)
                .receive()
                .response(status)
                .message()
                .body(body));
    }
}
