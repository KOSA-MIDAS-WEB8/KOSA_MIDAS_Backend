package com.midas.midashackathon.domain.work.repository;

import com.midas.midashackathon.domain.user.entity.UserEntity;
import com.midas.midashackathon.domain.work.entity.TodoEntity;
import com.midas.midashackathon.domain.work.entity.WorkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
    List<TodoEntity> findAllByVisibleToTeamIsFalse();
}
