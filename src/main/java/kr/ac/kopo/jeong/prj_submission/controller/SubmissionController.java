package kr.ac.kopo.jeong.prj_submission.controller;

import kr.ac.kopo.jeong.prj_submission.dto.SubmissionRequestDto;
import kr.ac.kopo.jeong.prj_submission.dto.SubmissionResponseDto;
import kr.ac.kopo.jeong.prj_submission.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;

    @PostMapping("/submit")
    public ResponseEntity<SubmissionResponseDto> submit(@ModelAttribute SubmissionRequestDto dto) {
        try {
            SubmissionResponseDto response = submissionService.submitAssignment(dto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}