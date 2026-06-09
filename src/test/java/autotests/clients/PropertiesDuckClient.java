package autotests.clients;

import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class PropertiesDuckClient extends DuckClient {

    @Step("Получаем характеристики уточки")
    public void duckProperties(TestCaseRunner runner, String id) {
        runner.$(http()
                .client(duckService)
                .send()
                .get("/api/duck/action/properties")
                .queryParam("id", id));
    }
}
