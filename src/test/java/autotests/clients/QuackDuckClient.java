package autotests.clients;

import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;

public class QuackDuckClient extends DuckClient {
    @Step("Просим уточку покрякать")
    public void duckQuack(TestCaseRunner runner, String id,
                          String repetitionCount, String soundCount) {
        String path = "/api/duck/action/quack";
        sendGetRequest(runner, duckService, path,
                "id", id,
                "repetitionCount", repetitionCount,
                "soundCount", soundCount);
    }
}