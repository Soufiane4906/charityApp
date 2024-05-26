package com.dailycodework.sbend2endapplication.entities;

import com.dailycodework.sbend2endapplication.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "charity_action_id")
    private CharityAction charityAction;

    private Double amount;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

   public String getCharityActionName() {
        return charityAction.getTitle();
    }

    public String getUserName() {
       if (user == null) return "";
        return user.getFirstName() + " " + user.getLastName();
    }
    // getters and setters
}

