package org.moera.lib.http;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * HTTP transport implementation backed by the OkHttp client.
 * <p>
 * The returned response keeps the underlying OkHttp response open until
 * {@link Response#close()} is called.
 */
public class OkHttpTransport implements HttpTransport {

    private final OkHttpClient client = new OkHttpClient();

    /**
     * {@inheritDoc}
     */
    @Override
    public Response call(
        String location,
        String method,
        Collection<Header> headers,
        String body,
        File file,
        String contentType
    ) throws IOException {
        var requestBuilder = new Request.Builder();
        var mediaType = MediaType.parse(contentType);
        RequestBody requestBody = null;
        if (file != null) {
            requestBody = RequestBody.create(file, mediaType);
        } else if (body != null) {
            requestBody = RequestBody.create(body, mediaType);
        }
        requestBuilder.method(method, requestBody);
        requestBuilder.url(location);
        if (headers != null) {
            headers.forEach(header -> requestBuilder.addHeader(header.name(), header.value()));
        }
        var response = client.newCall(requestBuilder.build()).execute();

        return new Response() {

            @Override
            public void close() {
                response.close();
            }

            @Override
            public int code() {
                return response.code();
            }

            @Override
            public String body() throws IOException {
                return response.body() != null ? response.body().string() : null;
            }

            @Override
            public InputStream bodyStream() {
                return response.body() != null ? response.body().byteStream() : null;
            }

            @Override
            public String contentType() {
                return response.body() != null ? Objects.toString(response.body().contentType(), null) : null;
            }

            @Override
            public long contentLength() {
                return response.body() != null ? response.body().contentLength() : -1;
            }
        };

    }

}
