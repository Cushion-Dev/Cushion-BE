package com.chzzk.cushion.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    Member findByUsername(String username);

    Optional<Member> findByIdAndEmail(Long id, String email);
}
