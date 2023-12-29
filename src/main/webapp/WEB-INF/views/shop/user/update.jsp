<%--
  Created by IntelliJ IDEA.
  User: seungjo
  Date: 12/2/23
  Time: 3:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div style="margin: auto; width: 600px">
    <div class="p-2">
        <form method="post" action="/mypage/update.do">

            <h1 class="h3 mb-3 fw-normal">마이페이지</h1>

            <%-- 보유 포인트 보여주기 --%>
            <p> 잔여 포인트 : ${user.userPoint} Point </p>

            <div class="form-floating" style="margin-bottom: 5px">
                <input type="text" name="user_id" class="form-control" id="user_id" placeholder="아이디"
                       value="${user.userId}" readonly required>
                <label for="user_id">아이디</label>
            </div>

            <div class="form-floating" style="margin-bottom: 5px">
                <input type="text" name="user_name" class="form-control" id="user_name" placeholder="이름" required
                       value="${user.userName}" accept-charset="euc-kr">
                <label for="user_name">이름</label>
            </div>


            <div class="form-floating" style="margin-bottom: 5px">
                <input type="password" name="user_password" class="form-control" id="user_password" placeholder="비밀번호"
                       value="${user.userPassword}" required>
                <label for="user_password">비밀번호</label>
            </div>


            <div class="form-floating" style="margin-bottom: 5px">
                <input type="date" name="user_birth" class="form-control" id="user_birth" placeholder="생년월일"
                       value="${birth}" required>
                <label for="user_birth">생년월일</label>
            </div>

            <button class="w-100 btn btn-lg btn-primary mt-3" type="submit"> 회원정보 수정</button>
        </form>

        <form id="deleteUser" method="POST" action="/deleteUser.do">
            <input type="hidden" name="user_id" value="${user.userId}"/>
            <input type="hidden" name="address_id" value="${address.addressId}"/>
            <button type="button" class="w-100 btn btn-danger" onclick="removeUser()" style="margin-top: 10px">회원 탈퇴
            </button>
        </form>
        <script>
            function removeUser() {
                if (confirm("정말로 탈퇴하시겠습니까?") === true) {
                    document.getElementById("deleteUser").submit();
                    alert("회원탈퇴가 완료되었습니다.");
                } else {
                    return false;
                }
            }
        </script>
    </div>
</div>