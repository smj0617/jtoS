package com.mysite.sbb.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 010-01: 새 파일. 찾는 데이터가 없을 때 던지는 예외 (책 2-10)
//         @ResponseStatus(NOT_FOUND) → 이 예외가 컨트롤러 밖으로 나가면 스프링이 500 대신 404 로 응답한다
//         RuntimeException 상속 → 메서드마다 throws 를 적지 않아도 된다 (언체크 예외)
//         강사식: 책의 고정 문구("entity not found") 대신 어떤 데이터가 없는지 메시지를 직접 받는다
//         여러 도메인이 같이 쓰므로 global.exception 에 둔다
@ResponseStatus(HttpStatus.NOT_FOUND)
public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message) {
        super(message);
    }
}
