package com.pinfix.fixuppinterest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.Charset;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpStatusCodeException;

public abstract class Pinterest {
    
    public static final String PINTEREST_URL = "https://pin.it/";
	public static HttpClient client = HttpClient.newBuilder().followRedirects(Redirect.ALWAYS).build();

    public static Post getPost(String postID) throws URISyntaxException, IOException, InterruptedException{
        String html;
        html = fetchDetails(postID);
        return Pinterest.toPost(html);
    }

    public static String toPage(Post post){
        return FormatUtils.formatPage(post);
    }

    private static String fetchDetails(String postID) throws URISyntaxException, IOException, InterruptedException{
        HttpRequest request = HttpRequest.newBuilder(new URI(Pinterest.PINTEREST_URL+postID)).build();
        HttpResponse<String> response = client.send(request,BodyHandlers.ofString(Charset.defaultCharset()));
		String html = response.body();
        return html;
    }

    public static String errorPage(String reason){
        return FormatUtils.errorPage(reason);
    }

    private static Post toPost(String html){
        Document doc = Jsoup.parse(html);
		String imageurl = doc.getElementsByAttributeValueMatching("id", Pattern.compile("pin-image-preload")).attr("href");
		String title = doc.title();
		String pintUrl = doc.getElementsByAttributeValueMatching("name", Pattern.compile("og:url")).attr("content");
		String description = doc.getElementsByAttributeValueMatching("name", Pattern.compile("og:description")).attr("content");
		Post post = new Post(imageurl, title, pintUrl, description);
        return post;
    }

    
}

