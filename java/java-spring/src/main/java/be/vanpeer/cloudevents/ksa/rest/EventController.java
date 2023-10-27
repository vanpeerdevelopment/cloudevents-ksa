package be.vanpeer.cloudevents.ksa.rest;

import com.github.javafaker.Faker;
import io.cloudevents.CloudEvent;
import io.cloudevents.core.builder.CloudEventBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static io.cloudevents.SpecVersion.V1;
import static java.time.Clock.systemUTC;
import static java.time.OffsetDateTime.now;
import static java.util.UUID.randomUUID;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/events")
public class EventController {

    private static final Faker FAKER = new Faker();

    @GetMapping("/latest")
    public CloudEvent getLatestEvent() {
        System.out.println("Received latest event request");
        ProductUpdated productUpdated = new ProductUpdated(FAKER.code().isbn13(), FAKER.book().title(), FAKER.howIMetYourMother().catchPhrase(), FAKER.number().randomDouble(2, 1, 10));
        System.out.println("Responding with product update:" + productUpdated);
        return CloudEventBuilder

                // Required attributes
                .fromSpecVersion(V1)
                .withSource(URI.create("https://vanpeer.be/cloudevents/ksa"))
                .withId(randomUUID().toString())
                .withType("be.vanpeer.cloudevents.ksa.ProductUpdated")

                // Optional attributes
                .withSubject(productUpdated.isbn())
                .withTime(now(systemUTC()))
                .withDataContentType(APPLICATION_JSON_VALUE)

                // Event data
                .withData(productUpdated.toJson())
                .build();
    }
}