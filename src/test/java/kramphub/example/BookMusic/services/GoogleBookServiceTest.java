package kramphub.example.bookmusic.services;

import kramphub.example.bookmusic.models.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GoogleBookServiceTest {

    @Autowired
    private GoogleBookService googleBookService;


    @Test
    public void getBookByTitleShouldReturn5Items() throws Exception {

        CompletableFuture<List<Book>> BookFututre = this.googleBookService.getBookBySearchTerm("Sido");
        List<Book> BookList = new ArrayList<>(BookFututre.get());

        assertThat(BookList.size()).isEqualTo(5);
    }

}
