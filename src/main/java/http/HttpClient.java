package http;

import java.util.Optional;

public interface HttpClient<T> {
    /**
     * Makes a GET HTTP request and returns the unmarshalled JSON body as responseClass.
     *
     * @param url           endpoint to make GET request on
     * @param responseClass Class describing JSON body
     * @return Optional.of(responseClass)
     */
    Optional<T> get(String url, Class<T> responseClass);
}
