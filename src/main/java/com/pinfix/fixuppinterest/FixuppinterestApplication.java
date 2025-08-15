package com.pinfix.fixuppinterest;

import java.io.IOException;
import java.net.URISyntaxException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class FixuppinterestApplication {

	@GetMapping("/")
	String home() {
		return FormatUtils.getHomepage();
	}

	@GetMapping("/{id}")
	ResponseEntity<String> post(@PathVariable String id) {
		String html;
		HttpStatusCode statusCode;

		try {
			html = Pinterest.toPage(Pinterest.getPost(id));
			statusCode = HttpStatus.OK;
		} catch (URISyntaxException e) {
			html = Pinterest.errorPage("The provided post ID is invalid");
			statusCode = HttpStatus.NOT_ACCEPTABLE;
		} catch (IOException | InterruptedException e){
			html = Pinterest.errorPage("An internal server error has occurred");
			statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<String>(html, statusCode);
	}

	public static void main(String[] args) {
		SpringApplication.run(FixuppinterestApplication.class, args);
		
	}

}
