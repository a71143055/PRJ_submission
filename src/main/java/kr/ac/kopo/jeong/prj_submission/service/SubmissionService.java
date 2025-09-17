package kr.ac.kopo.jeong.prj_submission.service;

import kr.ac.kopo.jeong.prj_submission.dto.SubmissionRequestDto;
import kr.ac.kopo.jeong.prj_submission.dto.SubmissionResponseDto;
import kr.ac.kopo.jeong.prj_submission.model.Assignment;
import kr.ac.kopo.jeong.prj_submission.model.Submission;
import kr.ac.kopo.jeong.prj_submission.model.User;
import kr.ac.kopo.jeong.prj_submission.repository.AssignmentRepository;
import kr.ac.kopo.jeong.prj_submission.repository.SubmissionRepository;
import kr.ac.kopo.jeong.prj_submission.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubmissionService {
    private final SubmissionRepository submissionRepository;
    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;

    public SubmissionResponseDto submitAssignment(SubmissionRequestDto dto) throws IOException {
        Assignment assignment = assignmentRepository.findById(dto.getAssignmentId())
                .orElseThrow(() -> new IllegalArgumentException("과제를 찾을 수 없습니다."));
        User student = userRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("학생을 찾을 수 없습니다."));

        String filePath = "/uploads/" + UUID.randomUUID() + "_" + dto.getFile().getOriginalFilename();
        dto.getFile().transferTo(new File(filePath));

        boolean isLate = LocalDate.now().isAfter(assignment.getDueDate());

        Submission submission = Submission.builder()
                .assignment(assignment)
                .student(student)
                .submittedAt(LocalDateTime.now())
                .fileUrl(filePath)
                .build();

        submissionRepository.save(submission);

        return SubmissionResponseDto.builder()
                .submissionId(submission.getId())
                .fileUrl(filePath)
                .submittedAt(submission.getSubmittedAt())
                .isLate(isLate)
                .build();
    }
}
