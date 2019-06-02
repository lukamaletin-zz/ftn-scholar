package io.github.lukamaletin.ftnscholar.repository;

import io.github.lukamaletin.ftnscholar.model.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
}
