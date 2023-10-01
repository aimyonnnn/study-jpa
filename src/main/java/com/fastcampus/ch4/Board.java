package com.fastcampus.ch4;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Board {
    
    @Id
//    @GeneratedValue // 자동 번호 증가
    private Long bno; // 게시물 번호
    private String title;
//    private String writer;
    private String content;

    // Board와 User가 ManyToOne으로 연결되어 있기 때문에 Board정보를 조회할 때 User 정보를 함께 갖고옴!
    // b1 = Board{bno=1, title='title1', content='content1', user=User{id='aaa', password='1234', name='LEE', email='aaa@aaa.com', inDate=2023-10-01 23:17:26.43, upDate=2023-10-01 23:17:26.43}, viewCnt=0, inDate=2023-10-01 23:17:26.455, upDate=2023-10-01 23:17:26.455}
    @ManyToOne // 여러 Board에 하나의 User. FK자동 생성
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Long viewCnt;
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date inDate;
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date upDate;

    @Override
    public String toString() {
        return "Board{" +
                "bno=" + bno +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
//                ", user=" + user +
                ", viewCnt=" + viewCnt +
                ", inDate=" + inDate +
                ", upDate=" + upDate +
                '}';
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getBno() {
        return bno;
    }

    public void setBno(Long bno) {
        this.bno = bno;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getViewCnt() {
        return viewCnt;
    }

    public void setViewCnt(Long viewCnt) {
        this.viewCnt = viewCnt;
    }

    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public Date getUpDate() {
        return upDate;
    }

    public void setUpDate(Date upDate) {
        this.upDate = upDate;
    }
}
