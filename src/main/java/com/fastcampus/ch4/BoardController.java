package com.fastcampus.ch4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;
    
    // 수정하기
    @GetMapping("/modify")
    public String modify(Long bno, Model model){
        Board board = boardService.read(bno);
        model.addAttribute("board", board);
        return "/board/write";
    }

    // 수정하기 비즈니스 로직
    @PostMapping("/modify")
    public String modify(Board board){
        boardService.modify(board);

        return "redirect:/board/list";
    }
    
    // 글쓰기폼으로 이동
    @GetMapping("/write")
    public String showWriteForm(Model model){
        Board board = new Board();
        User user = new User();
        user.setId("aaa");
        board.setUser(user);
        
        model.addAttribute("board", board);
        return "/board/write";
    }

    // 글쓰기 비즈니스 로직
    @PostMapping("/write")
    public String write(Board board){
        board.setBno(11L);  // 자동 증가 기능 사용하는게 바람직.
//        User user = new User();
//        user.setId("aaa");
//        board.setUser(user);
        board.setViewCnt(0L);
        board.setInDate(new Date());
        board.setUpDate(new Date());
        boardService.write(board);

        return "redirect:/board/list";
    }
    
    // 삭제
    @PostMapping("/remove")
    public String remove(Long bno){
        boardService.remove(bno);
        return "redirect:/board/list"; // 게시물 삭제후에 게시물 목록으로 이동
    }

    // 읽기
    @GetMapping("/read")
    public String read(Long bno, Model model){
        Board board = boardService.read(bno);
        model.addAttribute("board", board);
        return "/board/read";
    }
    
    // 조회
    @GetMapping("/list")
    public String getList(Model model){
        List<Board> list = boardService.getList();
        model.addAttribute("list", list);

        return "/board/list";
    }

}
