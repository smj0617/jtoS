package com.mysite.sbb.domain.member.member.entity;

import com.mysite.sbb.global.jpa.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

// 022-01: 새 파일. 회원 엔티티 (책 3-06의 SiteUser)
//         강사식: 이름은 Member, 패키지는 domain.member.member.entity (질문과 같은 3단 구조)
//         책이 SiteUser 라고 부른 이유는 user 가 여러 DB에서 예약어라서다. member 는 H2 예약어가 아니다
//         BaseEntity 상속 → id, createDate(가입일), modifyDate 를 물려받는다 (004-02)
@Getter
@Setter
@Entity
public class Member extends BaseEntity {
    // 022-01: 로그인 아이디. unique = true → DB에 같은 값이 두 번 들어가지 못하게 유니크 제약을 건다
    @Column(unique = true, length = 25)
    private String username;

    // 022-01: 비밀번호. 평문이 아니라 암호화한 값(022-03 BCrypt)을 저장하므로 길이를 넉넉히 둔다
    private String password;

    // 022-01: 이메일도 한 사람당 하나 → 유니크
    @Column(unique = true)
    private String email;
}
