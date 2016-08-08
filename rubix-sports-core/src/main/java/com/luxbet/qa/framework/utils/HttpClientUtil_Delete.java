package com.luxbet.qa.framework.utils;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class HttpClientUtil_Delete {
    private static Logger logger = Logger.getLogger(HttpClientUtil_Delete.class);

    private String _cookies;
    private HttpClient client = HttpClientBuilder.create().build();
    //private static CloseableHttpClient _httpClient = HttpClients.createDefault();

    public HttpClientUtil_Delete() {}

    private void sendPost(String url, List<NameValuePair> postParams) throws Exception {

        HttpPost post = new HttpPost(url);

        // add header
        //post.setHeader("Host", "accounts.google.com");
        //post.setHeader("User-Agent", USER_AGENT);
        post.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        post.setHeader("Accept-Language", "en-US,en;q=0.5");
        post.setHeader("Cookie", get_cookies());
        post.setHeader("Connection", "keep-alive");
        //post.setHeader("Referer", "https://accounts.google.com/ServiceLoginAuth");
        post.setHeader("Content-Type", "application/x-www-form-urlencoded");

        post.setEntity(new UrlEncodedFormEntity(postParams));

        HttpResponse response = client.execute(post);

        int responseCode = response.getStatusLine().getStatusCode();

        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + postParams);
        System.out.println("Response Code : " + responseCode);

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        System.out.println(result.toString());
    }

    private String GetPageContent(String url) throws Exception {

        HttpGet request = new HttpGet(url);

        //request.setHeader("User-Agent", USER_AGENT);
        request.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        request.setHeader("Accept-Language", "en-US,en;q=0.5");

        HttpResponse response = client.execute(request);
        int responseCode = response.getStatusLine().getStatusCode();

        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        // set _cookies
        set_cookies(response.getFirstHeader("Set-Cookie") == null ? "" :
                response.getFirstHeader("Set-Cookie").toString());

        return result.toString();

    }

    //Revisit
    public List<NameValuePair> getFormParams(String html, String username, String password)
            throws UnsupportedEncodingException {

        System.out.println("Extracting form's data...");

        Document doc = Jsoup.parse(html);

        // Google form id
        Element loginform = doc.getElementById("gaia_loginform");
        Elements inputElements = loginform.getElementsByTag("input");

        List<NameValuePair> paramList = new ArrayList<NameValuePair>();

        for (Element inputElement : inputElements) {
            String key = inputElement.attr("name");
            String value = inputElement.attr("value");

            if (key.equals("Email"))
                value = username;
            else if (key.equals("Passwd"))
                value = password;

            paramList.add(new BasicNameValuePair(key, value));

        }

        return paramList;
    }

    public String get_cookies() {
        return this._cookies;
    }

    public void set_cookies(String cookies) {
        this._cookies = cookies;
    }
}
