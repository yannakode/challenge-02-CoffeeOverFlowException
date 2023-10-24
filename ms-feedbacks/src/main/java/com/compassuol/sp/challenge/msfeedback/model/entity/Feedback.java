package com.compassuol.sp.challenge.msfeedback.model.entity;

import com.compassuol.sp.challenge.msfeedback.enums.Scales;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "feedback_tb")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Scales scale;
    @Column(nullable = false)
    private String comment;
    @Column(nullable = false)
    private Long orderId;
}
