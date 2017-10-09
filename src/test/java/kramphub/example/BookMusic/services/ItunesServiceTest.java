package kramphub.example.bookmusic.services;

import kramphub.example.bookmusic.models.Album;
import org.assertj.core.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.*;
import org.springframework.web.client.AsyncRestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

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
    @Ignore
    /**
     * @Todo: Setup a test that mooks the basic response from the api...
     * More research for mocking with moskito necessary
     */
    public void test() throws Exception {
        Mockito.when(restTemplate.getForEntity(
                Mockito.anyString(),
                Matchers.any(Class.class)
        ))
                .thenAnswer(new Answer<ListenableFuture<List<Album>>>() {
                    @Override
                    public ListenableFuture<List<Album>> answer(InvocationOnMock invocation) throws InterruptedException {

                        return new ListenableFuture<List<Album>>() {
                            @Override
                            public boolean cancel(boolean mayInterruptIfRunning) {
                                return false;
                            }

                            @Override
                            public boolean isCancelled() {
                                return false;
                            }

                            @Override
                            public boolean isDone() {
                                return false;
                            }

                            @Override
                            public List<Album> get() throws InterruptedException, ExecutionException {
                                 Thread.sleep(1000*60);
                                 return null;
                            }

                            @Override
                            public List<Album> get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                                return null;
                            }

                            @Override
                            public void addCallback(ListenableFutureCallback<? super List<Album>> callback) {

                            }

                            @Override
                            public void addCallback(SuccessCallback<? super List<Album>> successCallback, FailureCallback failureCallback) {

                            }
                        };
                    }
                });
        List<Album> res = itunesServiceMock.getAlbumBySearchTerm("test").get();
        Assertions.shouldHaveThrown(TimeoutException.class);
    }

}
