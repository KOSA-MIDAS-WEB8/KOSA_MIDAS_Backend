package com.midas.midashackathon.domain.user.entity;

import com.midas.midashackathon.domain.department.entity.DepartmentEntity;
import com.midas.midashackathon.domain.user.type.Role;
import com.midas.midashackathon.domain.work.entity.WorkEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class UserEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String accountId;

    private String password;

    private String phoneNumber;

    private String name;

    @JoinColumn
    @ManyToOne
    private DepartmentEntity department;

    @OneToMany(mappedBy = "author", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<WorkEntity> works;
    public void addWork(WorkEntity work) {
        works.add(work);
    }
    public void removeWork(WorkEntity work) {
        works.remove(work);
    }
}
