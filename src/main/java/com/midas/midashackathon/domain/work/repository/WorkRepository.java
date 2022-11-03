package com.midas.midashackathon.domain.work.repository;

import com.midas.midashackathon.domain.user.entity.UserEntity;
import com.midas.midashackathon.domain.work.entity.WorkEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface WorkRepository extends CrudRepository<WorkEntity, Long> {
    Optional<WorkEntity> findByAuthorAndDate(UserEntity author, LocalDate date);
}
