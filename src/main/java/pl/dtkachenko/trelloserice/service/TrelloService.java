package pl.dtkachenko.trelloserice.service;

import org.springframework.stereotype.Service;
import pl.dtkachenko.trelloserice.adapters.TrelloAdapter;
import pl.dtkachenko.trelloserice.mappers.TaskMapperImpl;
import pl.dtkachenko.trelloserice.model.Task;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrelloService {
    private TrelloAdapter adapter;
    private TaskMapperImpl taskMapper;

    public TrelloService(TrelloAdapter adapter, TaskMapperImpl taskMapper) {
        this.adapter = adapter;
        this.taskMapper = taskMapper;
    }

    public List<Task> retrieveTasks() {
        List<Task> tasks = adapter.getAllTasks()
                .stream()
                .map(trelloTask -> taskMapper.map(trelloTask))
                .collect(Collectors.toList());
        return tasks;
    }
}
