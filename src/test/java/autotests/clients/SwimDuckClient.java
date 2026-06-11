package autotests.clients;

import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;

public class SwimDuckClient extends DuckClient {
    @Step("Отправляем уточку в плавание")
    public void duckSwim(TestCaseRunner runner, String id) {
        String path = "/api/duck/action/swim";
        sendGetRequest(runner, duckService, path, "id", id);
    }
}