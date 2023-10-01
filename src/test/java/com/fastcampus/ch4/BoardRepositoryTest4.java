//package com.fastcampus.ch4;
//
//import com.querydsl.core.BooleanBuilder;
//import com.querydsl.core.Tuple;
//import com.querydsl.jpa.impl.JPAQuery;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.persistence.EntityManager;
//import javax.persistence.TypedQuery;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static com.fastcampus.ch4.QBoard.board; // board 사용할 때 QBoard안 붙여도 됨!
//
//@SpringBootTest
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // @Order 쓰려면 적어야함
//class BoardRepositoryTest4 {
//
//    @Autowired
//    public EntityManager em;
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
//            board.setViewCnt((long) (Math.random() * 100)); // 0~99, 괄호 주의
//            board.setInDate(new Date());
//            board.setUpDate(new Date());
//            boardRepo.save(board);
//        }
//    }
//
//    @Test
//    @DisplayName("querydsl로 쿼리 작성 테스트3 - 동적 쿼리작성")
//    public void querydslTest3() {
//
//        String searchBy = "TC"; // 제목(title)과 내용(content)에서 검색
//        String keyword = "1";
//        keyword = "%" + keyword + "%";
//
//        // 쿼리 빌더 생성해서 조건 달라지게 설정 후 where절에 넣어주기
//        BooleanBuilder builder = new BooleanBuilder();
//
//        // 동적으로 조건을 달리하게
//        if(searchBy.equalsIgnoreCase("T"))
//            builder.and(board.title.like(keyword));
//        if(searchBy.equalsIgnoreCase("C"))
//            builder.and(board.content.like(keyword));
//        if(searchBy.equalsIgnoreCase("TC"))
//            builder.and(board.title.like(keyword).or(board.content.like(keyword)));
//
//        JPAQueryFactory qf = new JPAQueryFactory(em);
//        JPAQuery query = qf.selectFrom(board)
//                .where(builder) // where절에 동적 쿼리 넣어주기
//                .orderBy(board.upDate.desc());
//
//        List<Board> list = query.fetch();
//        list.forEach(System.out::println);
//
//    }
//
//    @Test
//    @DisplayName("querydsl로 쿼리 작성 테스트2 - 간단한 쿼리작성")
//    public void querydslTest2() {
//
//        JPAQueryFactory qf = new JPAQueryFactory(em);
//
//        // 컬럼의 일부만 가져올 때는 JPAQuery<Tuple> // com.querydsl.core
//        JPAQuery<Tuple> query = qf.select(board.writer, board.viewCnt.sum()).from(board)
//                .where(board.title.notLike("title1%"))
//                .where(board.writer.eq("writer1"))
//                .where(board.content.contains("content"))
//                .where(board.content.isNotNull())
//                .groupBy(board.writer)
//                .having(board.viewCnt.sum().gt(100)) // 조회수가 100이 넘는 게시물
//                .orderBy(board.writer.asc())
//                .orderBy(board.viewCnt.desc());
//
//        List<Tuple> list = query.fetch();
//        list.forEach(System.out::println);
//
//    }
//
//        @Test
//    @DisplayName("querydsl로 쿼리 작성 테스트1 - 간단한 쿼리작성")
//    public void querydslTest1() {
//
////        QBoard board = QBoard.board;
//
//        // 1. JPAQueryFactory를 생성
//        JPAQueryFactory qf = new JPAQueryFactory(em); // 엔티티 매니저로부터 쿼리 펙토리를 생성
//
//        // 2. 쿼리 작성
//        JPAQuery<Board> query = qf.selectFrom(board)
//                .where(board.title.eq("title1"));
//
//        // 3. 쿼리 실행
//        List<Board> list = query.fetch();
//        list.forEach(System.out::println);
//    }
//}