package shop.mtcoding.examtest.board;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.examtest.common.dto.ResponseDto;
import shop.mtcoding.examtest.common.ex.CustomException;
import shop.mtcoding.examtest.common.util.Verify;
import shop.mtcoding.examtest.user.model.User;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final HttpSession session;

    @GetMapping("/board/{id}/apply")
    public ResponseEntity<?> apply(@PathVariable Integer id) {
        User principal = (User) session.getAttribute("principal");
        if (!principal.getRole().equals("employee")) {
            throw new CustomException("권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        Verify.validateApiObject(principal, "로그인이 필요한 기능입니다");
        boardService.insertApply(id, principal.getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "지원 성공", null), HttpStatus.OK);
    }
    
}
