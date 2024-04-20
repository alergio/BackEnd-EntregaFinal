package com.dh.dental.clinic.repository.impl;

import com.dh.dental.clinic.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ITokenRepository extends JpaRepository<Token, Long> {

    @Query("""
            SELECT t FROM Token t INNER JOIN User u
            ON t.user.id = u.id
            WHERE t.user.id = :userId AND t.loggedOut = false
            """)
    List<Token> findAllTokenByUser(Long userId);

    Optional<Token> findByToken(String token);

}
