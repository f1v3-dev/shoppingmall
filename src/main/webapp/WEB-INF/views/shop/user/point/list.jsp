<%--
  Created by IntelliJ IDEA.
  User: seungjo
  Date: 12/7/23
  Time: 11:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div style="width: 1000px; margin: auto;">
    <h2 class="text-center" style="margin-bottom: 50px">포인트 사용 내역</h2>
    <table class="table text-center">
        <thead>
        <tr>
            <th>포인트</th>
            <th>적립 구분 및 내역</th>
            <th>적립 일자</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="point" items="${pointHistories}">
            <tr>
                <fmt:formatNumber maxFractionDigits="3" value="${point.point}" var="parsePoint"/>
                <c:if test="${point.point > 0}">
                    <td class="text-success">+${parsePoint}</td>
                </c:if>
                <c:if test="${point.point < 0}">
                    <td class="text-danger">${parsePoint}</td>
                </c:if>

                <td>${point.description}</td>
                <td>
                    <fmt:parseDate value="${point.dateUpdated}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="parseDate"
                                   type="both"/>
                    <fmt:formatDate value="${parseDate}" pattern="yyyy-MM-dd" var="date"/>
                        ${date}
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>