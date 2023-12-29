<%--
  Created by IntelliJ IDEA.
  User: seungjo
  Date: 11/30/23
  Time: 7:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:choose>
    <c:when test="${empty category}">
        <c:set var="action" value="/admin/category/register.do"/>
    </c:when>
    <c:otherwise>
        <c:set var="action" value="/admin/category/update.do"/>
    </c:otherwise>
</c:choose>

<div style="margin: auto; width: 400px">
    <form action="${action}" method="POST">

        <c:if test="${not empty category}">
            <div class="form-floating" style="margin-bottom: 5px">
                <input type="number" name="category_id" class="form-control" id="category_id"
                       value="${category.categoryId}" placeholder="카테고리 아이디" readonly>
                <label for="category_id">카테고리 ID</label>
            </div>
        </c:if>

        <div class="form-floating" style="margin-bottom: 5px;">
            <input type="text" name="category_name" class="form-control" id="category_name"
                   value="${category.categoryName}" placeholder="카테고리 이름"
                   required>
            <label for="category_name">카테고리 이름</label>
        </div>

        <button class="w-100 btn btn-lg btn-primary" type="submit">
            <c:choose>
                <c:when test="${empty category}">
                    카테고리 등록
                </c:when>
                <c:otherwise>
                    카테고리 수정
                </c:otherwise>
            </c:choose>
        </button>
    </form>
</div>