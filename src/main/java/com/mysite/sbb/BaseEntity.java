package com.mysite.sbb;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// 004-02: 새 파일. 모든 엔티티가 공통으로 갖는 id·생성일시·수정일시를 한곳에 모은 부모 클래스 (강사식)
//         @MappedSuperclass → 이 클래스 자체는 테이블이 되지 않고, 상속한 엔티티의 테이블에 컬럼만 물려준다
//         @EntityListeners(AuditingEntityListener) → 저장·수정 직전에 @CreatedDate/@LastModifiedDate 값을 자동으로 채운다
//         (SbbApplication의 @EnableJpaAuditing 이 있어야 동작한다)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class BaseEntity {
    // 004-02: Question·Answer에 각각 있던 id를 여기로 옮겼다
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 004-02: 처음 저장될 때 한 번만 채워진다. 책처럼 setCreateDate(LocalDateTime.now())를 직접 부를 필요가 없다
    @CreatedDate
    private LocalDateTime createDate;

    // 004-02: 저장·수정될 때마다 갱신된다. 책은 3장(수정 기능)에서 modifyDate를 따로 추가하지만 미리 넣어 둔다
    @LastModifiedDate
    private LocalDateTime modifyDate;
}
