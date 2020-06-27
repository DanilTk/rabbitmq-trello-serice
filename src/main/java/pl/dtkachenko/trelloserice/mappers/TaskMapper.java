package pl.dtkachenko.trelloserice.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import pl.dtkachenko.trelloserice.model.Task;
import pl.dtkachenko.trelloserice.model.TrelloTask;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mappings({
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "description", source = "desc"),
            @Mapping(target = "creationDate", source = "dateLastActivity"),
            @Mapping(target = "dueDate", source = "due")})
    Task map(TrelloTask task);


    default LocalDateTime stringToDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            return LocalDateTime.parse(date, formatter);
        } catch (NullPointerException e) {
            return null;
        }
    }
}
