package kramphub.example.bookmusic;

import java.util.concurrent.*;
import java.util.function.Function;

public class FutureUtils {

    public static <T> CompletableFuture<T> toCompletableFuture(Future<T> future) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return future.get();
            } catch (InterruptedException|ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public static  <T> CompletableFuture<T> withTimeoutException(CompletableFuture<T> future, long timeout, TimeUnit unit, ScheduledThreadPoolExecutor delayer){
        return future.applyToEither(
                FutureUtils.withTimeoutException(timeout, unit,delayer),
                Function.<T>identity());
    }
    public static  <T> CompletableFuture<T>  withTimeoutException(long timeout, TimeUnit unit,ScheduledThreadPoolExecutor delayer){
        CompletableFuture<T> result = new CompletableFuture<T>();
        delayer.schedule(() -> result.completeExceptionally(new TimeoutException()), timeout, unit);
        return result;
    }


}
