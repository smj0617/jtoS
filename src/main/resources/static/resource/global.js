// 015-06: 새 파일. 모든 화면이 함께 쓰는 자바스크립트 (강사식)
//         layout.html 에서 한 번만 불러오므로 모든 화면에서 동작한다 (013-01 global.css 와 같은 자리, 같은 역할)
//         앞으로 여러 화면에서 쓸 동작은 여기에 모은다
//         - 016: 폼 검증 실패 시 첫 오류 입력칸에 커서 두기
//         - 025: <a method="DELETE"> 링크를 DELETE 요청으로 보내기

// 015-06: 화면(HTML)이 다 읽힌 뒤 실행할 함수를 등록하는 도우미
//         defer 로 불러오므로 보통은 이미 다 읽힌 상태지만, 언제 불러와도 안전하게 동작하도록 둘 다 처리한다
function onReady(fn) {
    if (document.readyState === "loading") {
        document.addEventListener("DOMContentLoaded", fn);
    } else {
        fn();
    }
}

// 016-08: 서버 검증에 실패해 폼이 다시 열렸을 때, 첫 번째 오류 입력칸에 커서를 둔다 (강사식)
//         016-06 의 th:errorclass 가 오류 칸에 is-invalid 클래스를 붙이므로 그 클래스로 찾는다
//         querySelector 는 화면 위에서부터 처음 만나는 하나를 돌려준다 → 첫 오류 칸
onReady(() => {
    const firstInvalid = document.querySelector(".is-invalid");
    if (firstInvalid) {
        firstInvalid.focus();
    }
});
