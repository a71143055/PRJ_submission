package kr.ac.kopo.jeong.prj_submission.repository;

import kr.ac.kopo.jeong.prj_submission.model.Assignment;
import kr.ac.kopo.jeong.prj_submission.model.Submission;
import kr.ac.kopo.jeong.prj_submission.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByStudent(User student);
    List<Submission> findByAssignment(Assignment assignment);
}
