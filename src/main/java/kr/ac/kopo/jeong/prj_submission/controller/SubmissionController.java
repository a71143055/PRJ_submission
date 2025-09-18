package kr.ac.kopo.jeong.prj_submission.controller;

import kr.ac.kopo.jeong.prj_submission.dto.SubmissionRequestDto;
import kr.ac.kopo.jeong.prj_submission.dto.SubmissionResponseDto;
import kr.ac.kopo.jeong.prj_submission.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;

    // 📄 제출 폼 페이지 렌더링
    @GetMapping("/submit-form")
    public String showSubmitForm(Model model) {
        model.addAttribute("submissionRequestDto", new SubmissionRequestDto());
        return "submit"; // templates/submit.html
    }

    // 📤 폼 제출 처리
    @PostMapping("/submit")
    public String submitFromTemplate(@ModelAttribute SubmissionRequestDto dto, Model model) {
        try {
            SubmissionResponseDto response = submissionService.submitAssignment(dto);
            model.addAttribute("response", response);
            return "submit-result";
        } catch (Exception e) {
            // 🔍 콘솔에 예외 메시지 출력
            System.err.println("제출 실패: " + e.getMessage());
            e.printStackTrace();

            model.addAttribute("error", "제출 중 오류가 발생했습니다: " + e.getMessage());
            return "submit";
        }
    }


    // 📦 API 방식도 유지 (선택)
    @PostMapping("/api")
    @ResponseBody
    public ResponseEntity<SubmissionResponseDto> submitApi(@ModelAttribute SubmissionRequestDto dto) {
        try {
            SubmissionResponseDto response = submissionService.submitAssignment(dto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

