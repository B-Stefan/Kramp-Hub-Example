package kramphub.example.BookMusic;

import com.codahale.metrics.annotation.Metered;
import javafx.beans.binding.ObjectBinding;
import kramphub.example.BookMusic.services.ItunesService;
import kramphub.example.BookMusic.models.Album;
import kramphub.example.BookMusic.models.Book;
import kramphub.example.BookMusic.models.IBookMusicEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class BookMusicController {

    private final ItunesService itunesService;

    @Autowired
    public BookMusicController(ItunesService itunesService) {
        this.itunesService = itunesService;
    }

    private List<Book> getBooks(){

        return new ArrayList<Book>();
    }


    @Metered
    private List<Album> onTimeoutOfExternalService(Throwable e){
        return new ArrayList<>();
    }


    @RequestMapping("/entries")
    @ResponseBody
    @Metered
    public List<IBookMusicEntry> getBooksAndMusic(@NotNull @RequestParam String query ) throws ExecutionException, InterruptedException {

        final List<Book> books = this.getBooks();
        final CompletableFuture<List<Album>> albumsFuture = itunesService.getAlbumBySearchTerm(query);


        final List<Album> albums = albumsFuture
                .exceptionally(this::onTimeoutOfExternalService)
                .get();

        return  Stream
                .concat(books.stream(), albums.stream())
                .sorted(Comparator.comparing(IBookMusicEntry::getName))
                .collect(Collectors.toList());
    }

}
