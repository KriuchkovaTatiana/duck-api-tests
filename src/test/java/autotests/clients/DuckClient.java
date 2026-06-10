package autotests.clients;

import autotests.EndpointConfig;
import autotests.payloads.PostApiDuckCreate;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.validation.DelegatingPayloadVariableExtractor.Builder.fromBody;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckClient extends TestNGCitrusSpringSupport {
    @Autowired
    protected HttpClient duckService;

    public void createDuck(TestCaseRunner runner, PostApiDuckCreate duck) {
        runner.$(http()
                .client(duckService)
                .send()
                .post("/api/duck/create")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMappingPayloadBuilder(duck, new ObjectMapper())));
    }

    public void duckDelete(TestCaseRunner runner, String id) {
        runner.$(http()
                .client(duckService)
                .send()
                .delete("/api/duck/delete")
                .queryParam("id", id));
    }

    public void duckId(TestCaseRunner runner) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId").build()));
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
}
