// 006-01: 여러 도메인이 함께 쓰는 공통 엔티티라 global.jpa.entity 로 이동 (강사식 패키지 구조)
package com.mysite.sbb.global.jpa.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.Hibernate;
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

    // 026-01: 같은 엔티티인지 판단하는 기준을 "같은 종류 + 같은 id" 로 정한다 (강사식)
    //         기본 equals 는 "같은 객체(메모리 주소)" 인지만 본다. DB에서 따로 읽어 온 같은 회원도 다른 것으로 본다
    //         026-02 추천자 Set<Member> 는 equals/hashCode 로 중복을 걸러내므로, 같은 회원이 두 번 들어가지 않게 하려면 필요하다
    //         Hibernate.getClass → LAZY 프록시(가짜 대리 객체)여도 실제 엔티티 클래스로 비교한다
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BaseEntity other = (BaseEntity) o;
        // 026-01: 아직 저장 전(id 가 null)인 엔티티는 자기 자신하고만 같다
        return getId() != null && getId().equals(other.getId());
    }

    // 026-01: hashCode 는 클래스 기준으로 고정 → 저장 전후로 id 가 생겨도 Set 안에서 위치가 바뀌지 않는다
    @Override
    public int hashCode() {
        return Hibernate.getClass(this).hashCode();
    }
}
