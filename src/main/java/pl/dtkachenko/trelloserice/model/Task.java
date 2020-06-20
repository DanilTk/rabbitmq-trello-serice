package pl.dtkachenko.trelloserice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private String name;
    private String description;
    private LocalDateTime creationDate;
    private LocalDateTime completionDate;
    private LocalDateTime dueDate;
}
