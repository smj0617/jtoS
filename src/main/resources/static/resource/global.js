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

// 025-07: <a href="..." method="DELETE"> 링크를 DELETE 요청으로 보낸다 (강사식)
//         링크는 GET 만 보낼 수 있으므로, 누르는 순간 숨은 폼을 만들어 POST + _method=DELETE(025-01 필터) + CSRF 토큰으로 보낸다
//         data-confirm="..." 이 있으면 먼저 확인창을 띄우고, 취소하면 아무것도 하지 않는다
//         CSRF 토큰은 layout.html 의 <meta name="_csrf"> 에서 읽는다
document.addEventListener("click", (e) => {
    const link = e.target.closest("a[method]");
    if (!link) return;
    const method = link.getAttribute("method").toUpperCase();
    if (method === "GET") return;

    e.preventDefault();
    const confirmMsg = link.dataset.confirm;
    if (confirmMsg && !confirm(confirmMsg)) return;

    const form = document.createElement("form");
    form.method = "post";
    form.action = link.href;
    const addHidden = (name, value) => {
        const input = document.createElement("input");
        input.type = "hidden";
        input.name = name;
        input.value = value;
        form.appendChild(input);
    };
    addHidden("_method", method);
    const csrfParam = document.querySelector("meta[name='_csrf_parameter']");
    const csrfToken = document.querySelector("meta[name='_csrf']");
    if (csrfParam && csrfToken) addHidden(csrfParam.content, csrfToken.content);
    document.body.appendChild(form);
    form.submit();
});

// 029-05: data-value 가 있는 <select> 는 그 값을 선택된 상태로 맞춘다 (강사식: 검색 옵션 기본값을 JS 로)
//         029-04 목록의 검색 대상 select 가 th:data-value="${kwType}" 로 현재 값을 담아 둔다
//         → 검색 후 다시 그려진 화면에서도 방금 고른 "제목/내용/글쓴이" 가 그대로 보인다
//         option 마다 th:selected 조건을 쓰지 않아도 되고, 앞으로 다른 select 에도 data-value 만 달면 된다
onReady(() => {
    document.querySelectorAll("select[data-value]").forEach((select) => {
        if (select.dataset.value) {
            select.value = select.dataset.value;
        }
    });
});

// 016-08: 서버 검증에 실패해 폼이 다시 열렸을 때, 첫 번째 오류 입력칸에 커서를 둔다 (강사식)
//         016-06 의 th:errorclass 가 오류 칸에 is-invalid 클래스를 붙이므로 그 클래스로 찾는다
//         querySelector 는 화면 위에서부터 처음 만나는 하나를 돌려준다 → 첫 오류 칸
onReady(() => {
    const firstInvalid = document.querySelector(".is-invalid");
    if (firstInvalid) {
        firstInvalid.focus();
    }
});
