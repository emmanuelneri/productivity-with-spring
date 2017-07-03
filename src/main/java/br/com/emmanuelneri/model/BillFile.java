package br.com.emmanuelneri.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Document(collection = "bill_file")
@Getter
public class BillFile {

    @Id
    private String id;
    private String date;
    private String content;

    public BillFile(String content) {
        this.content = content;
        this.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

}
