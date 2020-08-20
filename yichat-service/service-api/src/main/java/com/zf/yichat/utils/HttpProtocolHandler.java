package com.zf.yichat.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Objects;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 16:02 2019/8/14 2019
 */
public class HttpProtocolHandler {

    /**
     * 默认等待HttpConnectionManager返回连接超时（只有在达到最大连接数时起作用）：1秒
     */
    private static final long defaultHttpConnectionManagerTimeout = 3 * 1000;
    private static String DEFAULT_CHARSET = "utf-8";
    /**
     * http builder
     */
    private static HttpClientBuilder httpBulder = null;
    private static HttpProtocolHandler httpProtocolHandler = new HttpProtocolHandler();
    private static Logger logger = LoggerFactory.getLogger(HttpProtocolHandler.class);
    /**
     * 连接超时时间，由bean factory设置，缺省为8秒钟
     */
    private int defaultConnectionTimeout = 8000;
    /**
     * 回应超时时间, 由bean factory设置，缺省为30秒钟
     */
    private int defaultSoTimeout = 30000;
    /**
     * 闲置连接超时时间, 由bean factory设置，缺省为60秒钟
     */
    private int defaultIdleConnTimeout = 60000;
    private int defaultMaxConnPerHost = 30;
    private int defaultMaxTotalConn = 80;
    /**
     * HTTP连接管理器，该连接管理器必须是线程安全的.
     */
    private HttpClientConnectionManager connectionManager;
    /**
     * request config
     */
    private RequestConfig requestConfig = null;
    /**
     * response handler
     */
    private ResponseHandler<String> responseHandler;

    /**
     * 私有的构造方法
     */
    private HttpProtocolHandler() {
        try {
            //设置http的状态参数
            requestConfig = RequestConfig.custom()
                    .setSocketTimeout(defaultConnectionTimeout)
                    .setConnectTimeout(defaultConnectionTimeout)
                    .setConnectionRequestTimeout(defaultConnectionTimeout)
                    .build();

            // Create a custom response handler
            responseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity, DEFAULT_CHARSET) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };

            // 创建一个线程安全的HTTP连接池
            connectionManager = new PoolingHttpClientConnectionManager();
            ((PoolingHttpClientConnectionManager) connectionManager).setMaxTotal(defaultMaxConnPerHost);
            ((PoolingHttpClientConnectionManager) connectionManager).setDefaultMaxPerRoute(defaultMaxTotalConn);
            ((PoolingHttpClientConnectionManager) connectionManager).setValidateAfterInactivity(defaultIdleConnTimeout);

            httpBulder = HttpClients.custom();
            httpBulder.setConnectionManager(connectionManager);
        } catch (Exception e) {
            System.out.println("pay init exception");
            e.printStackTrace();
        }
    }

    /**
     * 工厂方法
     *
     * @return
     */
    public static HttpProtocolHandler getInstance() {
        return httpProtocolHandler;
    }

    public static CloseableHttpClient getConnection() {

        //SSL
        SSLContext sslContext = null;
        try {
            sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                //信任所有
                @Override
                public boolean isTrusted(X509Certificate[] chain,
                                         String authType) throws CertificateException {
                    return true;
                }
            }).build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);

        // get connection
        CloseableHttpClient httpClient = httpBulder.setSSLSocketFactory(sslsf).build();
        return httpClient;
    }

    public static String clientCustomSSL(String url, String requestXml, String charset, String mchid) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");

        InputStream instream = new ClassPathResource("weixin/" + mchid + ".p12").getInputStream();
        //FileInputStream instream = new FileInputStream(ResourceUtils.getFile("classpath:wexin/" + mchid + ".p12"));
        //FileInputStream instream = new FileInputStream(new File("C:\\Users\\yichat\\Desktop\\mysql data\\" + mchid + ".p12"));
        try {
            keyStore.load(instream, mchid.toCharArray());
        } finally {
            instream.close();
        }

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, mchid.toCharArray())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[]{"TLSv1"},
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        try {

            HttpPost httppost = new HttpPost(url);

            httppost.setEntity(new StringEntity(requestXml, charset));

            logger.info("executing request" + httppost.getRequestLine());

            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();

                logger.info("----------------------------------------");
                logger.info(Objects.toString(response.getStatusLine()));
                if (entity != null) {
                    logger.info("Response content length: " + entity.getContentLength());
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
                    StringBuffer sb = new StringBuffer();
                    String text = "";
                    while ((text = bufferedReader.readLine()) != null) {
                        sb.append(text);
                    }

                    return sb.toString();

                }
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
        return null;
    }

    public String doPostXml(String url, String params, String charset) {
        CloseableHttpClient httpClient = getConnection();
        HttpPost httpPost = null;
        String result = null;
        try {
            httpPost = new HttpPost(url);
            //设置参数
            StringEntity myEntity = new StringEntity(params, charset);
            httpPost.addHeader("Content-Type", "text/xml");
            httpPost.setConfig(requestConfig);
            httpPost.setEntity(myEntity);
            // 发送
            result = httpClient.execute(httpPost, responseHandler);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpPost != null) {
                httpPost.releaseConnection();
            }
        }
        return result;
    }

    public String doGet(String url) {
        CloseableHttpClient httpClient = getConnection();
        HttpGet httpGet = null;
        String result = null;
        try {
            httpGet = new HttpGet(url);

            // 发送
            result = httpClient.execute(httpGet, responseHandler);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpGet != null) {
                httpGet.releaseConnection();
            }
        }
        return result;
    }
}