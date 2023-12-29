<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" session="false" %>

<%--
  Created by IntelliJ IDEA.
  User: seungjo
  Date: 11/28/23
  Time: 12:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div style="margin: auto; width: 400px;">

    <h2 style="text-align: center">Administrator Page</h2>
    <div class="d-grid gap-2 col-6 mx-auto">
        <button class="btn btn-outline-dark btn-lg" type="button" onclick="location.href='/admin/category.do'">카테고리 관리</button>
        <button class="btn btn-outline-dark btn-lg" type="button" onclick="location.href='/admin/product.do'">상품 관리</button>
        <button class="btn btn-outline-dark btn-lg" type="button" onclick="location.href='/admin/user.do'">회원 관리</button>
    </div>
    <p class="mt-5 mb-3 text-muted">© nhn academy 2022-2024</p>
</div>