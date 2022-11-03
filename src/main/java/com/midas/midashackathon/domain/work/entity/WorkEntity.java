package com.midas.midashackathon.domain.work.entity;

import com.midas.midashackathon.domain.user.entity.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class WorkEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private LocalDate date;

    private Time plannedStart;
    private Time plannedEnd;

    @Setter
    private Time actualStart;

    @Setter
    private Time actualEnd;

    @ManyToOne
    @JoinColumn
    private UserEntity author;

    public void updatePlan(LocalTime start, LocalTime end) {
        this.plannedStart = Time.valueOf(start);
        this.plannedEnd = Time.valueOf(end);
    }
}
