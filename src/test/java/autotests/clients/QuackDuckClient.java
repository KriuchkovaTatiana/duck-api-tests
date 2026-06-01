package autotests.clients;

import autotests.StartTestsForWorkWithDucks;
import com.consol.citrus.TestCaseRunner;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class QuackDuckClient extends StartTestsForWorkWithDucks {

    public void duckQuack(TestCaseRunner runner, String id,
                          String repetitionCount, String soundCount) {
        runner.$(http()
                .client(duckService)
                .send()
                .get("/api/duck/action/quack")
                .queryParam("id", id)
                .queryParam("repetitionCount", repetitionCount)
                .queryParam("soundCount", soundCount));
    }
}
