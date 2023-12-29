<%--
  Created by IntelliJ IDEA.
  User: seungjo
  Date: 12/6/23
  Time: 9:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<table class="table text-center">
    <thead>
    <tr>
        <th scope="col"></th>
        <th scope="col">이미지</th>
        <th scope="col">상품명</th>
        <th scope="col">가격</th>
        <th scope="col">수량</th>
        <th scope="col">합계</th>
        <th scope="col"></th>
    </tr>
    </thead>
    <tbody style="vertical-align: middle">
    <c:set var="cartTotalPrice" value="0"/>
    <c:forEach items="${cartList}" var="cart">
        <c:forEach items="${productList}" var="product">
            <c:if test="${cart.productId eq product.productId}">
                <c:set var="totalPrice" value="${product.unitCost * cart.quantity}"/>
                <c:set var="cartTotalPrice" value="${cartTotalPrice + totalPrice}"/>
                <tr>
                    <td>
                        <input type="checkbox"></td>
                    <td>
                        <c:choose>
                        <c:when test="${product.productImage eq null}">
                        <img src="/resources/no-image.png" width="80px" height="95px" alt="no-image"/>
                        </c:when>
                        <c:otherwise>
                        <img src="/resources/images/${product.productImage}" width="80px" height="95px"
                             alt="product image"/>
                        </c:otherwise>
                        </c:choose>
                    <td>${product.modelName}</td>
                    <td>
                        <fmt:setLocale value="ko_KR"/>
                        <fmt:formatNumber value="${product.unitCost}" type="currency"/>
                    </td>
                    <td>
                        <form method="POST" action="/mypage/cart/update.do">
                            <input type="hidden" name="cartId" value="${cart.cartId}"/>
                            <input type="hidden" name="userId" value="${userId}"/>
                            <input type="hidden" name="productId" value="${product.productId}"/>
                            <input type="hidden" name="dateCreated" value="${cart.dateCreated}"/>
                            <input type="number" name="quantity" size="2" value="${cart.quantity}" min="1" maxlength="2"
                                   style="width: 40px; height: 100%"/>
                            <button style="height: 100%; background-color: whitesmoke; border: 1px solid lightgray"
                                    type="submit">변경
                            </button>
                        </form>
                    </td>
                    <td>
                        <fmt:setLocale value="ko_KR"/>
                        <fmt:formatNumber value="${totalPrice}" type="currency"/>
                    </td>
                    <td>
                        <form method="POST" action="/mypage/cart/delete.do">
                            <input type="hidden" name="cartId" value="${cart.cartId}"/>
                            <button style="height: 100%; background-color: whitesmoke; border: 1px solid lightgray"
                                    type="submit">삭제
                            </button>
                        </form>
                </tr>
            </c:if>
        </c:forEach>
    </c:forEach>
    </tbody>

    <tfoot style="font-size: 20px;">
    <tr>
        <td colspan="3"></td>
        <td>총 상품 금액</td>
        <td>
            <fmt:setLocale value="ko_KR"/>
            <fmt:formatNumber value="${cartTotalPrice}" type="currency"/>
        </td>
        <td></td>
        <td>
            <form method="POST" action="/mypage/orderByCart.do">
                <input type="hidden" name="userId" value="${userId}"/>
                <button type="submit" class="btn btn-outline-success">상품주문</button>
            </form>
        </td>
    </tr>
    </tfoot>
</table>