package autotests.clients;

import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;

public class UpdateDuckClient extends DuckClient {
    @Step("Меняем характеристики уточки")
    public void duckUpdate(TestCaseRunner runner, String color, String height,
                           String id, String material, String sound) {
        String path = "/api/duck/update";
        sendPutRequest(runner, duckService,
                path,
                "color", color,
                "height", height,
                "id", id,
                "material", material,
                "sound", sound);
    }
}