package com.midas.midashackathon.domain.work.entity;

import com.midas.midashackathon.domain.user.entity.UserEntity;
import com.midas.midashackathon.domain.work.type.TodoStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class TodoEntity {
    @Id
    private Long id;

    private String name;

    @Lob
    private String description;

    private LocalDateTime conductAt;

    private boolean visibleToTeam;

    @Setter
    @Enumerated(EnumType.STRING)
    private TodoStatus status;

    @JoinColumn
    @ManyToOne
    private UserEntity user;

    public void update(String title, String description, LocalDateTime conductAt, Boolean visibleToTeam) {
        if(title != null) this.name = title;
        if(description != null) this.description = description;
        if(conductAt != null) this.conductAt = conductAt;
        if(visibleToTeam != null) this.visibleToTeam = visibleToTeam;
    }
}
