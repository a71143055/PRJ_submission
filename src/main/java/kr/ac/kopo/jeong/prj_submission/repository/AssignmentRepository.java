package kr.ac.kopo.jeong.prj_submission.repository;

import kr.ac.kopo.jeong.prj_submission.model.Assignment;
import kr.ac.kopo.jeong.prj_submission.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByCreatedBy(User professor);
}
