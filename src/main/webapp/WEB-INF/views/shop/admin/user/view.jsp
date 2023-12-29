<%--
  Created by IntelliJ IDEA.
  User: seungjo
  Date: 11/30/23
  Time: 7:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:parseDate value="${user.createdAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="parseOrderDate" type="both"/>
<fmt:formatDate value="${parseOrderDate}" pattern="yyyy-MM-dd" var="createdAt"/>

<fmt:parseDate value="${user.latestLoginAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="parsedlatestLoginAt" type="both"/>
<fmt:formatDate value="${parsedlatestLoginAt}" pattern="yyyy-MM-dd HH:mm:ss" var="latestLoginAt"/>


<div style="margin: auto; width: 700px">

    <h3> Product View Page </h3>
    <table class="table table-bordered text-center align-middle">
        <tbody>
        <tr>
            <td>ID</td>
            <td>${user.userId}</td>
        </tr>

        <tr>
            <td>이름</td>
            <td>${user.userName}</td>
        </tr>

        <tr>
            <td>비밀번호</td>
            <td>${user.userPassword}</td>
        </tr>

        <tr>
            <td>생년월일</td>
            <td>${user.userBirth}</td>
        </tr>

        <tr>
            <td>역할</td>
            <td>${user.userAuth}</td>
        </tr>

        <tr>
            <td>포인트</td>
            <td>${user.userPoint}</td>
        </tr>

        <tr>
            <td>가입일자</td>
            <td>${createdAt}</td>
        </tr>

        <tr>
            <td>마지막 로그인 일자</td>
            <td>${latestLoginAt}</td>
        </tr>
        </tbody>
    </table>

</div>
