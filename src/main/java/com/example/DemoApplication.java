package com.example;

import com.example.data.repositories.MovieRepository;
import com.example.data.service.MovieService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SpringBootApplication
public class DemoApplication {

    //@Scheduled(fixedRate = 2000)
    public static void main(String[] args) throws IOException, InterruptedException {

        ConfigurableApplicationContext run = SpringApplication.run(DemoApplication.class, args);

        MovieRepository movieRepository = run.getBean(MovieRepository.class);
        MovieService movieService = new MovieService(movieRepository);

        String title = "Harry+Potter";

        HttpResponse<String> response = getRespone("https://www.omdbapi.com/?s=" + title + "&apikey=d3d95a11");

        JSONObject jsonObject = new JSONObject(response.body());

        JSONArray jsonArray = jsonObject.getJSONArray("Search");


        for(int i = 0; i < jsonArray.length(); ++i) {
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            String imdbID = jsonObject1.get("imdbID").toString();
            System.out.println(imdbID);

        }

        System.out.println("===============");

    }

    public static HttpResponse<String> getRespone(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(
                        URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

}

