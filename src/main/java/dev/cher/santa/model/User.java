package dev.cher.santa.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
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
    private String state;
    private String fullName;
    @Column(length = 1000)
    private String present;

    private Long secretSantaId;
    private Long giftReceiverId;

}
