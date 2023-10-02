package com.fastcampus.ch4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    // application.properties에 있는거 읽어오는법
    // @Value 어노테이션에 properties의 key값을 "${key}"을 적어주면 된다.
    @Value("${uploadPath}")
    private String uploadPath;
    
    // 파일업로드폼
    @GetMapping("/uploadForm")
    public String showUploadForm(){
        return "/board/uploadFile";
    }
    
    // 지정된 파일 삭제
    @PostMapping("/deleteFile")
    public ResponseEntity<String> deleteFile(String filename){
        System.out.println("filename = " + filename);

        File file = new File(uploadPath+filename);
        if(file.delete()) {
            return new ResponseEntity<>("성공적으로 삭제되었습니다.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 파일업로드 비즈니스 로직
//    @PostMapping("/uploadFile")
    @PostMapping("/uploadAjax")
    @ResponseBody
    public ResponseEntity<List<String>> uploadFile(MultipartFile[] files) throws IOException {

        List<String> list = new ArrayList<>();

        for(MultipartFile file : files){
            System.out.println("file.getOriginalFilename() = " + file.getOriginalFilename());
            System.out.println("file.getSize() = " + file.getSize());
            
            File upFile = new File(uploadPath, file.getOriginalFilename());
            file.transferTo(upFile); // 업로드된 파일을 c:/upload에 저장
            list.add(file.getOriginalFilename());
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 파일 다운로드
    // produces = MediaType.APPLICATION_OCTET_STREAM_VALUE을 지정해줘야 파일 다운 가능함
    @ResponseBody
    @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> downloadFile(String filename){
        System.out.println("여기까지옴2");
        System.out.println("filename = " + filename);
        Resource resource = new FileSystemResource(uploadPath+filename);

        System.out.println("resource = " + resource);

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    // 수정하기폼
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
