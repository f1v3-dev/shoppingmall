<%--
  Created by IntelliJ IDEA.
  User: seungjo
  Date: 11/28/23
  Time: 10:09 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" session="false" %>

<script defer src="/signup.js"></script>


<div style="margin: auto; width: 400px">
    <div class="p-2">
        <form id="signupForm" name="signupForm" method="post" action="/signupAction.do">

            <h1 class="h3 mb-3 fw-normal">Sign up Page</h1>

            <div class="form-floating" style="margin-bottom: 5px">
                <input type="text" name="user_id" class="form-control" id="user_id" placeholder="아이디">
<%--                <button class="btn btn-info" style="margin-top: 5px; margin-bottom: 5px" type="button"--%>
<%--                        onclick="return checkDuplicate()">Check--%>
<%--                </button>--%>
                <label for="user_id">아이디</label>
            </div>

            <div class="form-floating" style="margin-bottom: 5px">
                <input type="text" name="user_name" class="form-control" id="user_name" placeholder="이름"
                       accept-charset="euc-kr">
                <label for="user_name">이름</label>
            </div>


            <div class="form-floating" style="margin-bottom: 5px">
                <input type="password" name="user_password" class="form-control" id="user_password" placeholder="비밀번호">
                <label for="user_password">비밀번호</label>
            </div>

            <div class="form-floating" style="margin-bottom: 5px">
                <input type="date" name="user_birth" class="form-control" id="user_birth" placeholder="생년월일">
                <label for="user_birth">생년월일</label>
            </div>

            <button class="w-100 btn btn-lg btn-primary mt-3" type="submit">Sign up</button>
        </form>
    </div>
</div>
