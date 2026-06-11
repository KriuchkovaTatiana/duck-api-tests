package autotests.clients;

import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;

public class PropertiesDuckClient extends DuckClient {
    @Step("Получаем характеристики уточки")
    public void duckProperties(TestCaseRunner runner, String id) {
        String path = "/api/duck/action/properties";
        sendGetRequest(runner, duckService, path, "id", id);
    }
}