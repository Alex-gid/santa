package dev.cher.santa.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

import java.sql.Timestamp;


@Entity(name ="usersDataTable")
@Getter
@Setter
@ToString
public class User {

    @Id
    private Long chatId;
    private String firstName;
    private String lastName;
    private String userName;
    private Timestamp registeredAt;

}
