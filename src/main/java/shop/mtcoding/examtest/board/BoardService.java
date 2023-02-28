package shop.mtcoding.examtest.board;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.examtest.apply.model.Apply;
import shop.mtcoding.examtest.apply.model.ApplyRepository;
import shop.mtcoding.examtest.board.model.BoardRepository;
import shop.mtcoding.examtest.common.ex.CustomApiException;
import shop.mtcoding.examtest.common.util.Verify;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final ApplyRepository applyRepository;

    public void insertApply(Integer boardId, Integer principalId) {
        Verify.validateObject(boardRepository.findById(boardId), "존재하지 않는 공고입니다.");
        Apply apply = new Apply(principalId, boardId);
        // 아래 코드 수정 요망
        Verify.validateApiObject(applyRepository.findByUserIdAndBoardId(apply), "이미 지원한 공고입니다.");
        try {
            applyRepository.insert(apply);
        } catch (Exception e) {
            throw new CustomApiException("서버 오류로 인한 지원 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
