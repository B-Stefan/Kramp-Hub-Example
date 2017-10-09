package kramphub.example.BookMusic.services;

import kramphub.example.BookMusic.models.Album;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.AsyncRestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItunesServiceTest {

    @Autowired
    private ItunesService itunesService;

    @Test
    public void getAlbumByTitleShouldReturn5Items() throws Exception {

        CompletableFuture<List<Album>> albumFututre = this.itunesService.getAlbumBySearchTerm("Sido");
        List<Album> albumList = new ArrayList<>(albumFututre.get());

        assertThat(albumList.size()).isEqualTo(5);
    }

    @Mock
    AsyncRestTemplate restTemplate;
    @InjectMocks
    @Spy
    private ItunesService itunesServiceMock;


    @Test
    public void test() throws Exception {
        Mockito.when(restTemplate.getForEntity(
                Mockito.anyString(),
                Matchers.any(Class.class)
        ))
                .thenAnswer(new Answer<Void>() {
                    @Override
                    public Void answer(InvocationOnMock invocation) throws InterruptedException {
                        Thread.sleep(1000 * 60 );

                        //should cancel before the api respond with anything else to we just need to return something
                        return null;
                    }
                });
        List<Album> res = itunesServiceMock.getAlbumBySearchTerm("test").get();
        Assertions.shouldHaveThrown(TimeoutException.class);
    }

}
