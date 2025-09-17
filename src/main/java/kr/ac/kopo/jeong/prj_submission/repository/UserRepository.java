package kr.ac.kopo.jeong.prj_submission.repository;

import kr.ac.kopo.jeong.prj_submission.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}