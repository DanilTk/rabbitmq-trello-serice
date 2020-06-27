package pl.dtkachenko.trelloserice.service;

import org.springframework.stereotype.Service;
import pl.dtkachenko.trelloserice.adapters.TrelloAdapter;
import pl.dtkachenko.trelloserice.mappers.TaskMapperImpl;
import pl.dtkachenko.trelloserice.messaging.TaskPublisher;
import pl.dtkachenko.trelloserice.model.Task;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TrelloService {
    private TrelloAdapter adapter;
    private TaskMapperImpl taskMapper;
    private TaskPublisher publisher;

    public TrelloService(TrelloAdapter adapter, TaskMapperImpl taskMapper, TaskPublisher publisher) {
        this.adapter = adapter;
        this.taskMapper = taskMapper;
        this.publisher = publisher;
    }

    public Set<Task> retrieveTasks() {
        return adapter.getAllTasks()
                .stream()
                .map(trelloTask -> taskMapper.map(trelloTask))
                .collect(Collectors.toSet());
    }

    public void publishTasks() {
        Set<Task> tasks = retrieveTasks();
        tasks.forEach(task -> publisher.publishToQueue(task));
    }
}
