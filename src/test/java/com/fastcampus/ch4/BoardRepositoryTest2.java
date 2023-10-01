//package com.fastcampus.ch4;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Date;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // @Order 쓰려면 적어야함
//class BoardRepositoryTest2 {
//
//    @Autowired
//    private BoardRepository boardRepo;
//
//    // 테스트 데이터 넣기
//    @BeforeEach
//    public void testData(){ // 100개 데이터가 테스트 할 때마다 입력됨
//        for (int i = 1; i <= 100; i++) {
//            Board board = new Board();
//            board.setBno((long)i); // 래퍼클래스라 형변환이 필요함
//            board.setTitle("title" + i);
//            board.setContent("content" + i);
//            board.setWirter("writer" + (i % 5)); // writer 0~4
//            board.setViewCnt((long)Math.random()*100); // 0~99
//            board.setInDate(new Date());
//            board.setUpDate(new Date());
//            boardRepo.save(board);
//        }
//    }
//
//    // delete에 @Transactional이 없어서 TransactionRequiredException 에러 발생함!
//    @Test
//    public void deleteTest(){
//        assertTrue(boardRepo.deleteByWriter("writer1")==20);
//        List<Board> list = boardRepo.findByWriter("writer1");
//        assertTrue(list.size()==0);
//    }
//
//    @Test
//    public void findTest(){
//        List<Board> list = boardRepo.findByWriter("writer1");
//        assertTrue(list.size()==20);
//        list.forEach(System.out::println);
//    }
//
//    @Test
//    public void countTest(){
//        assertTrue(boardRepo.countAllByWriter("writer1")==20);
//    }
//
//}