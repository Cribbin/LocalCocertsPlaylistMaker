package http;

import java.util.Map;
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

    /**
     * Makes a GET HTTP request and returns the unmarshalled JSON body as responseClass.
     *
     * @param url              endpoint to make GET request on
     * @param responseClass    Class describing JSON body
     * @param headerParameters Header parameters
     * @return Optional.of(responseClass)
     */
    Optional<T> getWithHeader(String url, Class<T> responseClass,
                              Map<String, String> headerParameters);

    /**
     * Makes a POST HTTP request and returns the unmarshalled JSON body as responseClass.
     *
     * @param url              endpoint to make POST requests to
     * @param responseClass    Class describing JSON response body
     * @param bodyParameters   TODO
     * @param headerParameters TODO
     * @return Optional.of(responseClass)
     */
    Optional<T> post(String url, Class<T> responseClass,
                     Map<String, String> bodyParameters,
                     Map<String, String> headerParameters);
}
