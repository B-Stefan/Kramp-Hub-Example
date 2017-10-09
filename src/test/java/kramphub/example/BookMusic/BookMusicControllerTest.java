package kramphub.example.bookmusic;

import kramphub.example.bookmusic.models.Album;
import kramphub.example.bookmusic.models.Book;
import kramphub.example.bookmusic.models.IBookMusicEntry;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookMusicControllerTest {

    @Autowired
    private BookMusicController controller;

    @Test
    public void getEntiresShouldReturn10()
            throws Exception {

        List<IBookMusicEntry> result = controller.getBooksAndMusic("Test");
        Assert.assertEquals(10,result.size());

    }

    @Test
    public void getBooksAndMusicShouldReturn5Albums()
            throws Exception {

        List<IBookMusicEntry> result = controller.getBooksAndMusic("Test");

        Assert.assertEquals(5,result.stream()
                .filter(e -> e.getType().equals(Album.class.getSimpleName()))
                .count());

    }

    @Test
    public void getBooksAndMusicShouldReturn5Books()
            throws Exception {

        List<IBookMusicEntry> result = controller.getBooksAndMusic("Test");

        Assert.assertEquals(5, result.stream()
                .filter(e -> e.getType().equals(Book.class.getSimpleName()))
                .count());

    }

    @Test
    public void getBooksAndMusicShouldZeroBooksAndMusic()
            throws Exception {

        List<IBookMusicEntry> result = controller.getBooksAndMusic("asdjasdhkasdhasdhs");

        System.out.print("YEEEEEHA" + result.toString());
        Assert.assertEquals(0, result.size());

    }
}
