package com.dailycodework.sbend2endapplication.entities;

import com.dailycodework.sbend2endapplication.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CharityAction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;
    private String title;
    private String description;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date date;
    private String location;
    private Double fundraisingGoal;
    private Double amountRaised;
    @OneToMany(mappedBy = "charityAction")
    private List<Media> media;

  public   String CharityActionName() {
        return title;
    }
    // getters and setters
}

