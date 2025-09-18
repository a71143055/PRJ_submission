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
import org.springframework.web.multipart.MultipartFile;

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
        try {
            // 📌 과제 및 학생 조회
            Assignment assignment = assignmentRepository.findById(dto.getAssignmentId())
                    .orElseThrow(() -> new IllegalArgumentException("과제를 찾을 수 없습니다."));
            User student = userRepository.findById(dto.getStudentId())
                    .orElseThrow(() -> new IllegalArgumentException("학생을 찾을 수 없습니다."));

            // 📁 파일 저장 경로 설정
            String uploadDir = "uploads/";
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs(); // 디렉토리 없으면 생성
            }

            MultipartFile file = dto.getFile();
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            String filePath = uploadDir + fileName;

            File targetFile = new File(filePath);
            file.transferTo(targetFile); // 파일 저장

            boolean isLate = LocalDate.now().isAfter(assignment.getDueDate());

            // 📄 제출 엔티티 생성 및 저장
            Submission submission = Submission.builder()
                    .assignment(assignment)
                    .student(student)
                    .submittedAt(LocalDateTime.now())
                    .fileUrl(filePath)
                    .build();

            submissionRepository.save(submission);

            // 📦 응답 DTO 반환
            return SubmissionResponseDto.builder()
                    .submissionId(submission.getId())
                    .fileUrl(filePath)
                    .submittedAt(submission.getSubmittedAt())
                    .isLate(isLate)
                    .feedback(submission.getFeedback())
                    .score(submission.getScore())
                    .build();

        } catch (Exception e) {
            // 🐞 예외 로그 출력
            System.err.println("제출 처리 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            throw new IOException("제출 실패: " + e.getMessage());
        }
    }
}
