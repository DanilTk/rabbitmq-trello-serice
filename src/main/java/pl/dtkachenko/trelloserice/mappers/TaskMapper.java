package pl.dtkachenko.trelloserice.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import pl.dtkachenko.trelloserice.model.Task;
import pl.dtkachenko.trelloserice.model.TrelloTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mappings({
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "description", source = "desc"),
            @Mapping(target = "creationDate", source = "dateLastActivity"),
            @Mapping(target = "dueDate", source = "due")})
    Task map(TrelloTask task);


    default Date stringToDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            return format.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}
