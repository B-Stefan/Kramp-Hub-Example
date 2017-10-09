package kramphub.example.BookMusic.services;


import kramphub.example.BookMusic.FutureUtils;
import kramphub.example.BookMusic.models.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;


import java.io.Serializable;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class ItunesService {


    @Value("${ItunesResultLimit}")
    private static int DEFAULT_LIMIT = 5;

    private static final String ITUNES_API_URL = "https://itunes.apple.com/search";

    private final AsyncRestTemplate asyncRestTemplate;

    private final ScheduledThreadPoolExecutor dealer;

    @Autowired
    public ItunesService(AsyncRestTemplate asyncRestTemplate, ScheduledThreadPoolExecutor dealer) {
        this.asyncRestTemplate = asyncRestTemplate;
        this.dealer = dealer;
    }



    static class ItunesSearchResponse implements Serializable {
        private int resultCount;

        public List<Album> getResults() {
            return results;
        }

        private List<Album> results;

        public int getResultCount() {
            return resultCount;
        }

        public void setResultCount(int resultCount) {
            this.resultCount = resultCount;
        }

        public void setResults(List<Album> results) {
            this.results = results;
        }
    }

    public CompletableFuture<List<Album>> getAlbumBySearchTerm(String title) {
        return this.getAlbumBySearchTerm(title,DEFAULT_LIMIT);
    }

    public CompletableFuture<List<Album>> getAlbumBySearchTerm(String title, int limit) {

        String url = String.format("%s?term=%s&limit=%d&entity=album",ITUNES_API_URL, title,limit);

        ListenableFuture<ResponseEntity<ItunesSearchResponse>> listenableFuture =  asyncRestTemplate.getForEntity(url,ItunesSearchResponse.class);

        CompletableFuture<ResponseEntity<ItunesSearchResponse>> completableFuture = FutureUtils.toCompletableFuture(listenableFuture);

        return FutureUtils.withTimeoutException(completableFuture,30, TimeUnit.SECONDS,dealer)
                .thenApplyAsync(ResponseEntity::getBody)
                .thenApplyAsync(ItunesSearchResponse::getResults);

    }
}
