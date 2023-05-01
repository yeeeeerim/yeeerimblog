<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="../layout/header.jsp" %>


<div class="container my-3">
    <c:if test="${sessionUser.id == board.user.id}">
        <div class="mb-3 d-flex">
            <a href="/s/board/${board.id}/updateForm" class="btn btn-warning">수정</a>
            <form action="/s/board/${board.id}/delete" method="post">
                <button class="btn btn-danger">삭제</button>
            </form>
        </div>
    </c:if>


    <div class="mb-2 d-flex justify-content-end">
        글 번호 :
        <span id="id" class="me-3">
            <i>${board.id}</i>
        </span>
        작성자 :
        <span class="me-3">
            <i>${board.user.username}</i>
        </span>
    </div>

    <div>
        <h1><b>${board.title}</b></h1>
    </div>
    <hr/>
    <div>
        <div>${board.content}</div>
    </div>
    <hr/>

    <c:if test="${love == true}">
        <i id="heart" class="fas fa-heart fa-lg" style="color: red;"></i>
    </c:if>
    <c:if test="${love == false}">
        <i id="heart" class="fa-regular fa-heart fa-lg"></i>
    </c:if>

    <div class="card mt-3">
        <form action="/s/reply/save" method="post">
            <input type="hidden" name="boardId" value="${board.id}">
            <div class="card-body">
                <textarea id="reply-content" class="form-control" rows="1" name="content"></textarea>
            </div>
            <div class="card-footer">
                <button type="submit" id="btn-reply-save" class="btn btn-primary">등록</button>
            </div>
        </form>
    </div>
    <br/>
    <div class="card">
        <div class="card-header">댓글 리스트</div>
        <c:forEach items="${reply}" var="reply">

            <ul id="reply-box" class="list-group">
                <li id="reply-1" class="list-group-item d-flex justify-content-between">
                    <div style="display : none;">${reply.id}</div>
                    <div>${reply.content}</div>
                    <div class="d-flex">
                        <div class="font-italic">작성자: ${reply.writer}</div>
                        <button onClick="replyDelete()" class="badge bg-secondary">삭제</button>
                    </div>
                </li>
            </ul>
        </c:forEach>

    </div>
</div>

<script>
    const heartIcon = document.getElementById("heart");
    const loveValue = ${love};
    const boardId = ${board.id};
    const user=${sessionUser.id};
    if(!loveValue&(user!=null)){
        heartIcon.addEventListener("click",sendLove);
        console.log(user);
    }
    else{
        heartIcon.addEventListener("click",deleteLove);
    }
    function sendLove() {
        console.log(user);
        fetch("/s/love/save", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                boardId: boardId,
                userId:user
            }),
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then(() => {
                // 좋아요 수 등의 UI 업데이트를 수행합니다.
            })
            .catch((error) => {
                console.error("Error:", error);
            })
            .finally(() => {
                window.location.reload();
            });
    }

    function deleteLove() {

        fetch("/s/love/delete", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                boardId: boardId,
                userId:user
            }),
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then(() => {
                // 좋아요 수 등의 UI 업데이트를 수행합니다.
            })
            .catch((error) => {
                console.error("Error:", error);
            })
            .finally(() => {
                window.location.reload();
            });
    }

</script>

<%@ include file="../layout/footer.jsp" %>