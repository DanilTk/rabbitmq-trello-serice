package pl.dtkachenko.trelloserice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrelloTask {
    private String name;
    private String desc;
    private String dateLastActivity;
    private String due;
}
