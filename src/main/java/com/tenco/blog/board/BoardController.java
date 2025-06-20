package com.tenco.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller // IoC 대상 - 싱글톤 패턴으로 관리 됨
public class BoardController {

    // 생성자 의존 주의 DI 처리
    private final BoardPersistRepository br;

    // 게시글 목록 화면 요청 처리
    // 1. index.mustache 파일을 반환 시키는 기능을 만든다.
    // 주소설계 : http://localhost:8080/ , http://localhost:8080/index
    // {"/", "/index"}
    @GetMapping({"/", "/index"})
    // public String boardList(HttpServletRequest request) {
    public String boardList(Model model) {

        List<Board> boardList = br.findAll();

        // request.setAttribute("boardList", boardList);
        model.addAttribute("boardList", boardList);

        return "index";
    }


    // 게시글 작성 화면 요청 처리
    @GetMapping("/board/save-form")
    public String saveForm() {
        return "board/save-form";
    }

    // 게시글 작성 액션(수행) 처리
    @PostMapping("/board/save")
    public String save(BoardRequest.SaveDTO reqDTO) {
        // HTTP 요청 본문 : title=값&content=값&username=값
        // form 태그의 MIME 타입 (application/x-www-form-urlencoded)
        // reqDTO <-- 사용자가 던진 데이터 상태가 있음
        // DTO -- Board -- DB
        // Board board = new Board(reqDTO.getTitle(), reqDTO.getContent(), reqDTO.getUsername());
        Board board = reqDTO.toEntity();
        br.save(board);

        // PRG 패턴 (Post-Redirect-Get)
        return "redirect:/";
    }


}
