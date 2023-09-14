// 데이터 전송 객체 생성!
var comment = {
    // 이벤트 등록
    init: function() {
        var _this = this;
        // 생성 버튼 변수화
        const createBtn = document.querySelector('#comment-create-btn');
        // 생성 버튼 클릭 시, 수행할 메소드 연결!
        createBtn.addEventListener('click', function(){
            _this.create();
        });
        // 수정 버튼 변수화
        const updateBtns = document.querySelectorAll('.comment-update-btn');
        // 모든 수정 버튼별, 이벤트 등록
        updateBtns.forEach(function(item) {
            item.addEventListener('click', function() { // 클릭 이벤트 발생시,
                var form = this.closest('form'); // 클릭 이벤트가 발생한 버튼에 제일 가까운 폼을 찾고,
                _this.update(form); // 해당 폼으로, 업데이트 수행한다!
            });
        });
    },
    // 댓글 등록
    create: function() {
        // 데이터
        var data = {
            author: document.querySelector('#comment-author').value,
            content: document.querySelector('#comment-content').value,
        };
        // url에서 article의 id를 추출!
        var split = location.pathname.split('/');
        var articleId = split[split.length - 1];
        // Ajax 통신
        // - https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API/Using_Fetch
        // - https://t.ly/Vrrz
        fetch('/api/comments/' + articleId, { // 요청을 보냄
            method: 'POST',
            body: JSON.stringify(data),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function(response) { // 응답 처리
            if (response.ok) { // 성공
                alert('댓글이 등록되었습니다.');
                window.location.reload(`/articles/${articleId}#comment`);
            } else { // 실패
                alert('댓글 등록 실패..!');
            }
        });
    },
    // 댓글 수정
    update: function(form) {
        alert("클릭")
        // 데이터
        var data = {
            id: form.querySelector('#comment-id').value,
            email: form.querySelector('#comment-email').value,
            content: form.querySelector('#comment-content').value,
        };
        var articleId = form.querySelector('#articleId').value;
        // 비동기 통신
        fetch(`/article/01/${articleId}/comment/${data.id}`, { // 요청을 보냄
            method: 'PATCH',
            body: JSON.stringify(data),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function(response) { // 응답 처리
            if (response.ok) { // 성공
                alert('댓글이 수정되었습니다.');
            } else { // 실패
                alert('댓글 수정 실패..!');
            }
            window.location.reload(true); // 페이지 리로드
        });
    }
};
// 객체 초기화!
comment.init();