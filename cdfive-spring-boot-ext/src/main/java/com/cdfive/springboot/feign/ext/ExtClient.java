package com.cdfive.springboot.feign.ext;

import feign.Client;
import feign.Request;
import feign.Response;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;

/**
 * @author cdfive
 */
@Slf4j
public class ExtClient extends Client.Default {

    public ExtClient(SSLSocketFactory sslContextFactory, HostnameVerifier hostnameVerifier) {
        super(sslContextFactory, hostnameVerifier);
    }

    public ExtClient(SSLSocketFactory sslContextFactory, HostnameVerifier hostnameVerifier, boolean disableRequestBuffering) {
        super(sslContextFactory, hostnameVerifier, disableRequestBuffering);
    }

    @Override
    public Response execute(Request request, Request.Options options) throws IOException {
        log.info("feign execute start=>request={},options={}", request, options);
        try {
            Response response = super.execute(request, options);
            return response;
        } catch (Exception e) {
            log.error("feign execute error=>request={},options={}", request, options, e);
            throw e;
        }
    }
}
