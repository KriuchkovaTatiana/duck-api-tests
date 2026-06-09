package autotests.clients;

import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import io.qameta.allure.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import static com.consol.citrus.actions.ExecuteSQLAction.Builder.sql;
import static com.consol.citrus.actions.ExecuteSQLQueryAction.Builder.query;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;
//import static com.consol.citrus.validation.DelegatingPayloadVariableExtractor.Builder.fromBody;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckClient extends TestNGCitrusSpringSupport {
    @Autowired
    protected HttpClient duckService;

    @Autowired
    protected SingleConnectionDataSource testDb;

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

    /*@Step("Получаем ID уточки")
    public void duckId(TestCaseRunner runner) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId").build()));
    }*/

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
