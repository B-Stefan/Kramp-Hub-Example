package kramphub.example.bookmusic;

import com.codahale.metrics.annotation.Metered;
import kramphub.example.bookmusic.services.GoogleBookService;
import kramphub.example.bookmusic.services.ItunesService;
import kramphub.example.bookmusic.models.Book;
import kramphub.example.bookmusic.models.IBookMusicEntry;
import org.apache.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.rmi.runtime.Log;
import sun.security.pkcs11.wrapper.Functions;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Controller
public class BookMusicController {

    private final ItunesService itunesService;

    private final GoogleBookService googleBookService;

    private final Logger logger;

    @Autowired
    public BookMusicController(ItunesService itunesService, GoogleBookService googleBookService) {
        this.itunesService = itunesService;
        this.googleBookService = googleBookService;
        this.logger = Logger.getLogger(BookMusicController.class.getSimpleName());
    }

    /**
     * This method gets called if an timeout occur.
     * Automatic measurement of error is activated by the @Metered annotation
     * Remove logger if the monitoring work peppery
     * @param e
     * @return
     */
    @Metered
    private List<IBookMusicEntry> onTimeoutOfExternalService(Throwable e){
        logger.info(e.getMessage());
        return new ArrayList<>();
    }


    @RequestMapping("/entries")
    @ResponseBody
    @Metered
    public List<IBookMusicEntry> getBooksAndMusic(@NotNull @RequestParam String query ) throws ExecutionException, InterruptedException {

        List<CompletableFuture<?>> futures = new ArrayList<>();

        futures.add(itunesService.getAlbumBySearchTerm(query));
        futures.add(googleBookService.getBookBySearchTerm(query));



        CompletableFuture<List<?>> listCompletableFuture = CompletableFuture
                .allOf(futures.toArray(new CompletableFuture[futures.size()]))
                .thenApply(v -> {
                    return futures.stream().
                            map(CompletableFuture::join).
                            collect(Collectors.toList());
                    });



        return listCompletableFuture
                .exceptionally(this::onTimeoutOfExternalService)
                .get()
                .stream()
                .map((item)-> (List<IBookMusicEntry>)item) //
                // Bad casting because Java 8 don't understand nested generics...
                // Find a better solution for this would be the first thing I would do.
                .flatMap(List::stream)
                .sorted(Comparator.comparing(IBookMusicEntry::getName))
                .collect(Collectors.toList());
    }

}
