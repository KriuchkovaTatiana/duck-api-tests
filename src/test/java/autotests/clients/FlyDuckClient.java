package autotests.clients;

import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;

public class FlyDuckClient extends DuckClient {
    @Step("Отправляем уточку в полёт")
    public void duckFly(TestCaseRunner runner, String id) {
       String path = "/api/duck/action/fly";
        sendGetRequest(runner, duckService, path, "id", id);
    }
}