package com.example.user_service.auth.registration.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(String token);

    @Query("select t from Token t inner join t.user u where u.userId = :id and (t.expired = falser or t.revoked = false)")
    List<Token> findAllValidTokenByUser(@Param("id") Long id);
}
