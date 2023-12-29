<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: seungjo
  Date: 12/7/23
  Time: 4:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div style="width: 1000px; margin: auto;">
    <h2 class="text-center" style="margin-bottom: 50px">주문 내역</h2>
    <table class="table text-center">
        <thead>
        <tr>
            <th></th>
            <th>상품명</th>
            <th>상품 금액(단가)</th>
            <th>수량</th>
            <th>주문일자</th>
            <th>배송 도착 예정일</th>
        </tr>
        </thead>
        <tbody style="vertical-align: middle">
        <c:forEach var="order" items="${orderList}">
            <tr>
                <td>
                    <c:choose>
                        <c:when test="${order.productImage eq null}">
                            <img src="/resources/no-image.png" width="80px" height="95px" alt="no-image">
                        </c:when>
                        <c:otherwise>
                            <img src="/resources/images/${order.productImage}" width="80px" height="95px" alt="상품 이미지">
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>${order.modelName}</td>
                <td>
                    <fmt:setLocale value="ko_KR"/>
                    <fmt:formatNumber value="${order.unitCost}" type="currency"/>
                </td>
                <td>${order.quantity}</td>
                <td>
                    <fmt:parseDate value="${order.orderDate}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="parseShipDate" type="both"/>
                    <fmt:formatDate value="${parseShipDate}" pattern="yyyy-MM-dd hh:mm:ss" var="orderDate"/>
                    ${orderDate}
                </td>
                <td>
                    <fmt:parseDate value="${order.shipDate}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="parseShipDate" type="both"/>
                    <fmt:formatDate value="${parseShipDate}" pattern="yyyy-MM-dd" var="shipDate"/>
                    ${shipDate}
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>