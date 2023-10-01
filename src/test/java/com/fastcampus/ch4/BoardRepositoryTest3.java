package com.fastcampus.ch4;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // @Order 쓰려면 적어야함
class BoardRepositoryTest3 {

    @Autowired
    public EntityManager em;

    @Autowired
    private BoardRepository boardRepo;

    // 테스트 데이터 넣기
    @BeforeEach
    public void testData(){ // 100개 데이터가 테스트 할 때마다 입력됨
        for (int i = 1; i <= 100; i++) {
            Board board = new Board();
            board.setBno((long)i); // 래퍼클래스라 형변환이 필요함
            board.setTitle("title" + i);
            board.setContent("content" + i);
            board.setWirter("writer" + (i % 5)); // writer 0~4
            board.setViewCnt((long)Math.random()*100); // 0~99
            board.setInDate(new Date());
            board.setUpDate(new Date());
            boardRepo.save(board);
        }
    }

    @Test
    @DisplayName("@Query로 네이티브 쿼리(SQL)작성 테스트2")
    public void queryAnnoTest5(){
//        @Query(value = "select title, writer from board", nativeQuery = true)
//        List<Object[]> findAllBoardBySQL2(); // 컬럼을 따로 조회할 땐 Object[] 배열로 받아야함.
        List<Object[]> list = boardRepo.findAllBoardBySQL2(); // 전체 컬럼을 가져오는게 아니라서 Object[]
        list.stream() // list를 stream으로 변환
                .map(arr -> Arrays.toString(arr)) // arr를 String으로 변환
                .forEach(System.out::println);
        assertTrue(list.size()==100 );

    }

    @Test
    @DisplayName("@Query로 네이티브 쿼리(SQL)작성 테스트")
    public void queryAnnoTest4(){
//        @Query(value = "select * from board", nativeQuery = true) // SQL, nativeQuery default는 false임
//        List<Board> findAllBoardBySQL();
        List<Board> list = boardRepo.findAllBoardBySQL();
        assertTrue(list.size()==100 );

    }

    @Test
    @DisplayName("@Query로 JPQL작성 테스트 - 매개변수 이름")
    public void queryAnnoTest3(){
        // @Query("select b from Board b where b.title= :title and b.writer= :writer")
        // List<Board> findByTitleAndWriter2(String title, String writer);
        List<Board> list = boardRepo.findByTitleAndWriter2("title1", "writer1");
        list.forEach(System.out::println);
        assertTrue(list.size()==1);
    }

    @Test
    @DisplayName("@Query로 JPQL작성 테스트 - 매개변수 순서")
    public void queryAnnoTest2(){
        // @Query("select b from Board b where b.title=?1 and b.writer=?2")
        List<Board> list = boardRepo.findByTitleAndWriter2("title1", "writer1");
        list.forEach(System.out::println);
        assertTrue(list.size()==1);
    }

    @Test
    @DisplayName("@Query로 JPQL작성 테스트")
    public void queryAnnoTest(){
        List<Board> list = boardRepo.findAllBoard();
        assertTrue(list.size()==100);
    }
    
    @Test
    @DisplayName("createQuery로 JPQL작성 테스트")
    public void createQueryTest() {
        String query = "select b from Board b";
        TypedQuery<Board> tQuery = em.createQuery(query, Board.class);
        List<Board> list = tQuery.getResultList();

        list.forEach(System.out::println);
        assertTrue(list.size()==100);
    }
}