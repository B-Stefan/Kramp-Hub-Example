package kramphub.example.BookMusic.services;


import kramphub.example.BookMusic.FutureUtils;
import kramphub.example.BookMusic.models.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;


import java.io.Serializable;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
public class ItunesService {


    private static final int DEFAULT_LIMIT = 5;

    private final AsyncRestTemplate asyncRestTemplate;

    @Autowired
    public ItunesService(AsyncRestTemplate asyncRestTemplate) {
        this.asyncRestTemplate = asyncRestTemplate;
    }

    private static class ItunesSearchResponse implements Serializable {
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

        String url = String.format("https://itunes.apple.com/search?term=%s&limit=%d&entity=album", title,limit);

        ListenableFuture<ResponseEntity<ItunesSearchResponse>> listenableFuture =  asyncRestTemplate.getForEntity(url,ItunesSearchResponse.class);

        CompletableFuture<ResponseEntity<ItunesSearchResponse>> completableFuture = FutureUtils.toCompletableFuture(listenableFuture);

        return completableFuture
                .thenApplyAsync(ResponseEntity::getBody)
                .thenApplyAsync(ItunesSearchResponse::getResults);

    }
}
