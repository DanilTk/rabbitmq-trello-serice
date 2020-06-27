package pl.dtkachenko.trelloserice.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.dtkachenko.trelloserice.service.QueueService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class TrelloController {
    private QueueService queueService;

    @PostMapping("/tasks")
    ResponseEntity publishOnQueue() {
        queueService.publishTasks();
        return ResponseEntity.ok().body(null);
    }
}
