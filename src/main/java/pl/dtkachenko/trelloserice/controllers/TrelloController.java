package pl.dtkachenko.trelloserice.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.dtkachenko.trelloserice.service.TrelloService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class TrelloController {
    private TrelloService trelloService;

    @PostMapping("/tasks")
    ResponseEntity publishOnQueue() {
        trelloService.publishTasks();
        return ResponseEntity.ok().body(null);
    }
}
