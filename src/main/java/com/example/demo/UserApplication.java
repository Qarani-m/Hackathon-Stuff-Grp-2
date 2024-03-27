package com.example.demo;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;

@SpringBootApplication
@EnableScheduling
public class UserApplication {

	private static final Logger logger = LoggerFactory.getLogger(UserApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);

	}
	@Scheduled(fixedDelay = 2000)
	public void fetchDataFromDatabase() {
//		logger.info("Fetching data from database...");
		// Your data fetching logic here
//		logger.info("Data fetched successfully.");
		getRequest();
	}


	public void getRequest() {
		String username = "medic";
		String password = "password";
		String basicAuth = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());

		try {
			URL url = new URL("https://19a0-197-136-183-18.ngrok-free.app/medic/_all_docs");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization", basicAuth);

			int responseCode = conn.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				System.out.println(response.toString());
				Gson gson = new Gson();
				JsonObject jsonObject = gson.fromJson(response.toString(), JsonObject.class);

				// Get the "rows" array from the jsonObject
				JsonArray rowsArray = jsonObject.getAsJsonArray("rows");

				// Iterate over the "rows" array
				for (JsonElement rowElement : rowsArray) {
					JsonObject rowObject = rowElement.getAsJsonObject();

					// Print out the values for each row
					System.out.println("id: " + rowObject.get("id").getAsString());
					System.out.println("key: " + rowObject.get("key").getAsString());
					JsonObject valueObject = rowObject.getAsJsonObject("value");
					System.out.println("rev: " + valueObject.get("rev").getAsString());
					System.out.println();
				}
			} else {
				System.out.println("GET request failed with response code: " + responseCode);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }




}
