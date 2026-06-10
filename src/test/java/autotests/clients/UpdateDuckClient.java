package autotests.clients;

import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class UpdateDuckClient extends DuckClient {

    @Step("Меняем характеристики уточки")
    public void duckUpdate(TestCaseRunner runner, String color, String height,
                           String id, String material, String sound) {
        runner.$(http()
                .client(duckService)
                .send()
                .put("/api/duck/update")
                .queryParam("color", color)
                .queryParam("height", height)
                .queryParam("id", id)
                .queryParam("material", material)
                .queryParam("sound", sound));
    }

}
