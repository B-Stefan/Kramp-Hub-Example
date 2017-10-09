package kramphub.example.bookmusic.services;


import kramphub.example.bookmusic.FutureUtils;
import kramphub.example.bookmusic.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class GoogleBookService {


    @Value("${GoogleResultLimit}")
    private static int DEFAULT_LIMIT = 5;

    private static final String GOOGLE_API_URL = "https://www.googleapis.com/books/v1/volumes";

    private final AsyncRestTemplate asyncRestTemplate;

    private final ScheduledThreadPoolExecutor dealer;

    @Autowired
    public GoogleBookService(AsyncRestTemplate asyncRestTemplate, ScheduledThreadPoolExecutor dealer) {
        this.asyncRestTemplate = asyncRestTemplate;
        this.dealer = dealer;
    }



    static class GoogleBookResponse implements Serializable {

        public String getKind() {
            return kind;
        }

        public void setKind(String kind) {
            this.kind = kind;
        }

        public int getTotalItems() {
            return totalItems;
        }

        public void setTotalItems(int totalItems) {
            this.totalItems = totalItems;
        }

        public List<Book> getItems() {
            return items;
        }

        public void setItems(List<Book> items) {
            this.items = items;
        }

        private String kind;
       private int totalItems;
       private List<Book> items;

    }

    public CompletableFuture<List<Book>> getBookBySearchTerm(String title) {
        return this.getBookBySearchTerm(title,DEFAULT_LIMIT);
    }

    public CompletableFuture<List<Book>> getBookBySearchTerm(String title, int limit) {

        String url = String.format("%s?q=%s&maxResults=%d&orderBy=relevance", GOOGLE_API_URL, title,limit);

        ListenableFuture<ResponseEntity<GoogleBookResponse>> listenableFuture =  asyncRestTemplate.getForEntity(url,GoogleBookResponse.class);

        CompletableFuture<ResponseEntity<GoogleBookResponse>> completableFuture = FutureUtils.toCompletableFuture(listenableFuture);

        return FutureUtils.withTimeoutException(completableFuture,30, TimeUnit.SECONDS,dealer)
                .thenApplyAsync(ResponseEntity::getBody)
                .thenApplyAsync(GoogleBookResponse::getItems);

    }
}
