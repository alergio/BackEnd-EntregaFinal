package com.dh.dental.clinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IGenericRepository <T, Long> extends JpaRepository<T, Long> {
    @Deprecated
    void forcedDeleteById(Long id);
}
