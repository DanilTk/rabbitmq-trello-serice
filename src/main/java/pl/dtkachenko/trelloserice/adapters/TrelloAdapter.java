package pl.dtkachenko.trelloserice.adapters;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.dtkachenko.trelloserice.model.TrelloTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TrelloAdapter {
    private final RestTemplate restTemplate;
    @Value("${trello.key}")
    private String KEY;
    @Value("${trello.token}")
    private String TOKEN;
    @Value("${trello.url.get}")
    private String GET_URL;

    public TrelloAdapter(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public List<TrelloTask> getAllTasks() {

        String address = new StringBuilder(GET_URL)
                .append("?")
                .append("key=")
                .append(KEY)
                .append('&')
                .append("token=")
                .append(TOKEN)
                .toString();

        TrelloTask[] tasks = restTemplate.getForObject(address, TrelloTask[].class);

        return new ArrayList<>(Arrays.asList(tasks));
    }
}
