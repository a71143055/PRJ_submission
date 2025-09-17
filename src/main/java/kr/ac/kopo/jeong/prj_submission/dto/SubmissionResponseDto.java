package kr.ac.kopo.jeong.prj_submission.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class SubmissionResponseDto {
    private Long submissionId;
    private String fileUrl;
    private LocalDateTime submittedAt;
    private String feedback;
    private Integer score;
    private boolean isLate;
}
