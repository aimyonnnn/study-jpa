package com.fastcampus.ch4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.Date;

@SpringBootApplication
public class Ch4Application implements CommandLineRunner {

    @Autowired
    EntityManagerFactory emf;

    public static void main(String[] args) {

       SpringApplication app = new SpringApplication(Ch4Application.class);
//       app.setWebApplicationType(WebApplicationType.NONE); // 이렇게 하면 톰캣이 안뜸 -> 게시판 만들기에선 주석처리함
       app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        EntityManager em = emf.createEntityManager();
        System.out.println("em = " + em);

//        // 트랜잭션 얻기
//        EntityTransaction tx = em.getTransaction();
//
//        // user entity 객체 생성하기
//        User user = new User();
//        user.setId("aaa");
//        user.setPassword("1234");
//        user.setName("Lee");
//        user.setEmail("aaa@aaa.com");
//        user.setInDate(new Date());
//        user.setUpDate(new Date());
//
//        tx.begin(); // 트랜잭션 시작
//        // 1. 저장
//        em.persist(user); // user entity를 em에 영속화(저장)
//
//        // 2. 변경
//        user.setPassword("4321"); // PersistenceContext가 변경감지. update문 실행
//        user.setEmail("bbb@bbb.com");
//        tx.commit(); // 트랜잭션 종료(DB에 반영)
//
//        // 3. 조회
//        User user2 = em.find(User.class, "aaa"); // em에 있으면 DB조회 안함
//        // => console에 select문이 출력되지 않는 이유는 영속 상태이기 때문에 DB까지 가지 않음
//        System.out.println("user==user2 = " + (user == user2));
//        User user3 = em.find(User.class, "bbb"); // em에 없으면 DB조회
//        System.out.println("user3 = " + user3);
//
//        // 4. 삭제
//        tx.begin();
//        em.remove(user); // em에서 user entity를 삭제
//        tx.commit();

    }
}
