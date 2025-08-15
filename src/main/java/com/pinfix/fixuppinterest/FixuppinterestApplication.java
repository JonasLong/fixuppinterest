package com.pinfix.fixuppinterest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.Charset;
import java.util.regex.Pattern;

import org.apache.tomcat.util.buf.Utf8Encoder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

@RestController
@SpringBootApplication
public class FixuppinterestApplication {

	public static HttpClient client;

	@RequestMapping("/{id}")
	String home(@PathVariable String id) throws IOException, InterruptedException, URISyntaxException {
		HttpRequest request = HttpRequest.newBuilder(new URI("https://pin.it/"+id)).build();
		HttpResponse<String> response = client.send(request,BodyHandlers.ofString(Charset.defaultCharset()));
		String html = response.body();
		return makeResponse(html);
	}

	public String makeResponse(String html){
		//<link as="image" fetchpriority="high" href="https://i.pinimg.com/736x/3a/fd/ee/3afdee9e538d9ce08fbec51a36113b9f.jpg" id="pin-image-preload" nonce="" rel="preload">
		Document doc = Jsoup.parse(html);
		String imageurl = doc.getElementsByAttributeValueMatching("id", Pattern.compile("pin-image-preload")).attr("href");
		String title = doc.title();
		//<meta content="https://mx.pinterest.com/pin/83527768084542514/" data-app="true" name="og:url" property="og:url">
		String pintUrl = doc.getElementsByAttributeValueMatching("name", Pattern.compile("og:url")).attr("content");
		//<meta content="16 may. 2025 - 𝘊𝘳𝘦𝘥𝘪𝘵𝘴 𝘢𝘳𝘵𝘪𝘴𝘵 〖 3ambat 〗 𝘰𝘯 𝘛𝘸𝘪𝘵𝘵𝘦𝘳 ⇄ 𝕏  ∤ #SHINGEKINOKYOJIN #ATTACKONTITAN #AOT #SNK #HANGEZOE #FANART #ANIME #MANGA" data-app="true" name="og:description" property="og:description">
		String description = doc.getElementsByAttributeValueMatching("name", Pattern.compile("og:description")).attr("content");
		return String.format("""
		<!DOCTYPE html>
			<head>
				<meta property="og:title" content="%s">
				<meta property="og:description" content="%s">
				<meta property="og:image" content="%s">
				
				<title>
					%s
				</title>
			</head>
			<body>
				<h1>%s</h1>
				<h2>%s</h2>
				<p><a href="%s"><strong>Click here to be redirected to Pinterest</strong></a></p>

				<img src="%s" />
			<body>

		</html>
		
		""", title, description, imageurl, title, title, description, pintUrl, imageurl);
	}

	public static void main(String[] args) {
		client = HttpClient.newBuilder().followRedirects(Redirect.ALWAYS).build();
		SpringApplication.run(FixuppinterestApplication.class, args);
		
	}

}
