package com.wanda.network;

import android.content.Context;

import com.wanda.R;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.security.KeyStore;

import javax.net.ssl.HostnameVerifier;

/**
 * class that manages the the https connection
 *
 */

public class HttpsClient {

    /**
     * context the access the ssl keystore-file (produced by the bouncycastle lib)
     */
    private static Context context;

    /**
     * simple constructor defining the context
     * @param context
     */
    public HttpsClient(Context context) {
        this.context = context;
    }

    /**
     * the used apache http-client
     */
    private static HttpClient httpClient;


    /**
     * Method to build the http-client
     * @return
     */
    private static HttpClient getHttpClient() {
        if (httpClient == null){
            //sets up parameters
            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, "utf-8");

            params.setBooleanParameter("http.protocol.expect-continue", false);

            SchemeRegistry registry = new SchemeRegistry();
            //registry.register(
            //        new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", newSslSocketFactory(), 443));

            ClientConnectionManager clientConnectionManager =
                    new ThreadSafeClientConnManager(params, registry);
            httpClient = new DefaultHttpClient(clientConnectionManager,params);
        }
        return httpClient;
    }

    /**
     * Method to define the ssl socket, using the keystore file res/raw/keystore.bks
     * @return
     */
    private static SSLSocketFactory newSslSocketFactory() {
        try {
            //defining the bks format
            KeyStore trusted = KeyStore.getInstance("BKS");
            InputStream in = context.getResources().openRawResource(R.raw.keystore);
            try {
                //using the mostly random storage password
                trusted.load(in, "Pftsawa1337".toCharArray());
            } finally {
                in.close();
            }

            //allowing the miss match of hostname and certificate owner, since the hostname
            //isn't clear for now!
            HostnameVerifier hostnameVerifier =
                    org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
            SSLSocketFactory socketFactory = new SSLSocketFactory(trusted);
            socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
            return socketFactory;

        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    /**
     * Method to make an https GET request, based on the predefined connection
     * @param url
     * @return
     * @throws Exception
     */
    public static String executeHttpGet(String url) throws Exception {
        BufferedReader in = null;
        try {
            HttpClient client = getHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(url));
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();

            String result = sb.toString();
            return result;
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Method to make an https POST request, based on the predefined connection
     * @param url
     * @return
     * @throws Exception
     */
    public static String executeHttpPost(String url, String jsonData) throws Exception {
        BufferedReader in = null;
        try {
            HttpClient client = getHttpClient();
            HttpPost request = new HttpPost();
            request.setURI(new URI(url));
            StringEntity entity = new StringEntity(jsonData, HTTP.UTF_8);
            entity.setContentType("application/json");
            request.setEntity(entity);
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();

            String result = sb.toString();
            return result;
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}