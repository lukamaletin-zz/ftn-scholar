package io.github.lukamaletin.ftnscholar.repository;

import io.github.lukamaletin.ftnscholar.model.user.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<BaseUser, Long> {

    <E extends BaseUser> Optional<E> findById(long id);

    <E extends BaseUser> Optional<E> findByUsername(String username);

    <E extends BaseUser> Optional<E> findByUsernameAndPassword(String username, String password);

    <E extends BaseUser> E save(E user);
}
