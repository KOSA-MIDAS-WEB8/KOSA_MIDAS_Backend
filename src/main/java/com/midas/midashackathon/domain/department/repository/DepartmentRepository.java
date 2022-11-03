package com.midas.midashackathon.domain.department.repository;

import com.midas.midashackathon.domain.department.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity, String> {
}
