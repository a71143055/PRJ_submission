package kr.ac.kopo.jeong.prj_submission.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class SubmissionRequestDto {
    private Long assignmentId;
    private Long studentId;
    private MultipartFile file;
}
