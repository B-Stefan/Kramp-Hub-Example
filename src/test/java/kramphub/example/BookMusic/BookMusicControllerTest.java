package kramphub.example.BookMusic;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookMusicControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void controllerShouldReturn10Items() throws Exception {
        List<?> list = this.restTemplate.getForObject("http://localhost:" + port + "/entries?term=sido", List.class);
        assertThat(list.size()).isEqualTo(10);
    }

    @Test
    public void controllerShouldReturnZeroItems() throws Exception {
        List<?> list = this.restTemplate.getForObject("http://localhost:" + port + "/entries?term=adjasdjsdjasjdasdjasdhafhasdbasdasdhasdhasdhasdh", List.class);
        assertThat(list.size()).isEqualTo(0);
    }

    @Test
    public void controllerShouldReturnError() throws Exception {
        List<?> list = this.restTemplate.getForObject("http://localhost:" + port + "/entries?term=adjasdjsdjasjdasdjasdhafhasdbasdasdhasdhasdhasdh", List.class);
        assertThat(list.size()).isEqualTo(0);
    }




}