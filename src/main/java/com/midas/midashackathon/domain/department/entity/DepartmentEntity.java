package com.midas.midashackathon.domain.department.entity;

import com.midas.midashackathon.domain.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.sql.Time;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class DepartmentEntity {

    @Id
    private String code;

    private String name;

    private Time coreTimeStart;
    private Time defaultStartTime;

    private Integer requiredCoreTime;

    private Integer requiredWorkTime;

    @OneToMany(mappedBy = "department", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<UserEntity> members;
    public void addMember(UserEntity user) {
        this.members.add(user);
    }

    public void removeMember(UserEntity user) {
        this.members.remove(user);
    }

    public void update(String name, Time coreTimeStart, Integer coreTimeHours, Integer workHour, Time defaultStartTime) {
        if(name != null) this.name = name;
        if(coreTimeStart != null) this.coreTimeStart = coreTimeStart;
        if(coreTimeHours != null) this.requiredCoreTime = coreTimeHours;
        if(workHour != null) this.requiredWorkTime = workHour;
        if(defaultStartTime != null) this.defaultStartTime = defaultStartTime;
    }
}
