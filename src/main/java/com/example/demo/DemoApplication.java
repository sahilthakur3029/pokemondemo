package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.*;
import org.json.*;


import java.io.IOException;

@SpringBootApplication
@RestController
public class DemoApplication {

	String baseURL;
	public DemoApplication() {
		baseURL = " https://pokeapi.co/api/v2/pokemon/";
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping()
	public String hello(@RequestParam(value = "name", defaultValue = "Brock") String name) throws IOException, JSONException {
		//Creating the corresponding number from name parameter and refactor name.
		name = name.substring(0, 1).toUpperCase() + name.substring(1);
		int sum = 0;
		for (int i = 0; i < name.length(); i++) {
			sum = sum + name.charAt(i);
		}
		//Appending to API Link
		StringBuilder newURL = new StringBuilder(baseURL);
		newURL.append(sum);

		//Converting to URL
		URL url = new URL(newURL.toString());

		//Opening API Connection and making it a GET Request
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");

		//Setting up feed to connection
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		//Actually passing in argument and getting back information and storing it
		String input;
		String output = "";
		while ((input = in.readLine()) != null) {
			output = input;
		}

		//Converting info from API call to JSON object and formatting.
		JSONObject response = new JSONObject(output);
		String pokemon = (String)response.get("name");
		pokemon = pokemon.substring(0, 1).toUpperCase() + pokemon.substring(1);
		return String.format("Hello %s! You are most like the pokemon %s.", name, pokemon);
	}
}