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
