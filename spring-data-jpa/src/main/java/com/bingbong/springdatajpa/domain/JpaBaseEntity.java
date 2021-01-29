package com.bingbong.springdatajpa.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperclass // JPA 상속을 말하는 것이 아니라 데이터의 속성을 내릴 수 있게 해줌
public class JpaBaseEntity {

    @Column(updatable = false)
    private LocalDateTime createDate;
    private LocalDateTime updatedDate;

    @PrePersist
    public void prePersist() { // 저장하기 전에 이벤트 실행
        LocalDateTime now = LocalDateTime.now();
        this.createDate = now;
        this.updatedDate = now; // null을 넣어도 되나 실제 쿼리할 때 null이 있으면 쿼리가 지저분해진다
    }

    @PreUpdate
    public void preUpdate() { // 업데이트가 있을 때 실행
        this.updatedDate = LocalDateTime.now();
    }
}
