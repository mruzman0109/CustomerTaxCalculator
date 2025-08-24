package hr.ent.integrations;

import hr.ent.ApplicationStart;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        classes = ApplicationStart.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class TaxControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void calculateTax_shouldReturnExpectedFee() {
        // Arrange
        String json = """
            [
              {
                "vehicleType": "Car",
                "passTime": [
                  "2025-08-25T06:15:00",
                  "2025-08-25T07:10:00"
                ]
              }
            ]
            """;

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

        HttpEntity<String> entity = new HttpEntity<>(json, headers);

        // Act
        ResponseEntity<Float> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/tax/calculate",
                HttpMethod.POST,
                entity,
                Float.class
        );

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(18); // since within 60 min → only max fee
    }
}
