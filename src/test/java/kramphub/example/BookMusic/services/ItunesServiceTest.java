package kramphub.example.BookMusic.services;

import kramphub.example.BookMusic.models.Album;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItunesServiceTest {

    @Autowired
    private ItunesService itunesService;

    @Test
    public void getAlbumByTitleShouldReturn5Items() throws Exception {

        CompletableFuture<List<Album>> albumFututre = this.itunesService.getAlbumBySearchTerm("my search term");
        List<Album> albumList = new ArrayList<>(albumFututre.get());

        assertThat(albumList.size()).isEqualTo(5);
    }

}
