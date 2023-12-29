<%--
  Created by IntelliJ IDEA.
  User: seungjo
  Date: 12/3/23
  Time: 3:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div style="margin: auto; width: 400px;">

  <h2 style="text-align: center">Administrator Page</h2>
  <div class="d-grid gap-2 col-6 mx-auto">
    <button class="btn btn-outline-dark btn-lg" type="button" onclick="location.href='/admin/user/role_admin.do'">관리자 회원 리스트</button>
    <button class="btn btn-outline-dark btn-lg" type="button" onclick="location.href='/admin/user/role_user.do'">일반 회원 리스트</button>
  </div>
  <p class="mt-5 mb-3 text-muted">© nhn academy 2022-2024</p>
</div>