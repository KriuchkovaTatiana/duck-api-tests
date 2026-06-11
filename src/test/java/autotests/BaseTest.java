package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.ContextConfiguration;

import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;

import static com.consol.citrus.actions.ExecuteSQLAction.Builder.sql;
import static com.consol.citrus.actions.ExecuteSQLQueryAction.Builder.query;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class BaseTest extends TestNGCitrusSpringSupport {
    @Autowired
    protected HttpClient duckService;

    @Autowired
    protected SingleConnectionDataSource testDb;

    //универсальные методы отправки запросов

    /*так как некоторые методы имеют отличное от остальных количество входящих параметров
   (например, duckFly имеет один параметр, а duckQuack имеет три),
   обрабатываем их универсально через цикл, который перебирает параметры парами*/

    //отправляем GET запрос
    protected void sendGetRequest(TestCaseRunner runner, HttpClient httpClient, String path, String... keyValuePairs) {
        var getRequest = http()
                .client(httpClient)
                .send()
                .get(path);
        for (int i = 0; i < keyValuePairs.length; i += 2) {
            getRequest.queryParam(keyValuePairs[i], keyValuePairs[i + 1]);
        }
        runner.$(getRequest);
    }

    //отправляем PUT запрос
    protected void sendPutRequest(TestCaseRunner runner, HttpClient httpClient, String path, String... keyValuePairs) {
        var putRequest = http()
                .client(httpClient)
                .send()
                .put(path);

        for (int i = 0; i < keyValuePairs.length; i += 2) {
            putRequest.queryParam(keyValuePairs[i], keyValuePairs[i + 1]);
        }
        runner.$(putRequest);
    }

    @Step("Создаем уточку")
    public void createDuck(TestCaseRunner runner, String id, String color,
                           String height, String material, String sound,
                           String wingsState) {
        runner.$(sql(testDb)
                .statement("delete from DUCK where id = " + id));
        runner.$(sql(testDb)
                .statement("insert into DUCK (id, color, height, material, sound, wings_state) " +
                        "values (" + id + ", '" + color + "', " + height + ", '" +
                        material + "', '" + sound + "', '" + wingsState + "')"));
    }

    @Step("Удаляем уточку из БД")
    public void deleteDuckFromDatabase(TestCaseRunner runner, String id) {
        runner.$(sql(testDb)
                .statement("delete from DUCK where id = " + id));
    }

    //метод валидации с передачей ответа String’ой
    public void validateResponse(TestCaseRunner runner, HttpStatus status, String body) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(status)
                .message()
                .body(body));
    }

    //метод валидации с передачей ответа из папки Resources
    public void validateResponseFromResources(TestCaseRunner runner, HttpStatus status, String resourcePath) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(status)
                .message()
                .body(new ClassPathResource(resourcePath)));
    }

    //метод валидации с передачей ответа из папки Payload
    public void validateResponseFromPayload(TestCaseRunner runner, HttpStatus status, Object payload) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(status)
                .message()
                .body(new ObjectMappingPayloadBuilder(payload, new ObjectMapper())));
    }

    //метод валидации для проверки создания уточки в БД
    public void validateDuckInDatabase(TestCaseRunner runner, String id, String color,
                                       String height, String material, String sound,
                                       String wingsState) {
        runner.$(query(testDb)
                .statement("select * from DUCK where id = " + id)
                .validate("color", color)
                .validate("height", height)
                .validate("material", material)
                .validate("sound", sound)
                .validate("wings_state", wingsState));
    }

    //метод валидации для проверки удаления уточки из БД
    public void validateDuckDeletedFromDatabase(TestCaseRunner runner, String id) {
        runner.$(query(testDb)
                .statement("select count(*) as countDucks from DUCK where id = " + id)
                .validate("countDucks", "0"));
    }
}