package com.dailycodework.sbend2endapplication.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Media {
    public enum MediaType {
        IMAGE,
        VIDEO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private MediaType type;
    private String url;
    @ManyToOne
    @JoinColumn(name = "charity_action_id")
    private CharityAction charityAction;

    // getters and setters
}