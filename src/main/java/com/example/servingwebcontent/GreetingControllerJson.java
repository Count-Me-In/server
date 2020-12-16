package com.example.servingwebcontent;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingControllerJson {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/greetingjson")
	public com.example.restservice.Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new com.example.restservice.Greeting(counter.incrementAndGet(), String.format(template, name));
	}
}
