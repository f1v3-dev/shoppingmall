<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: seungjo
  Date: 12/4/23
  Time: 2:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:choose>
    <c:when test="${empty address}">
        <c:set var="action" value="/mypage/address/register.do"/>
    </c:when>
    <c:otherwise>
        <c:set var="action" value="/mypage/address/update.do"/>
    </c:otherwise>
</c:choose>

<div style="margin: auto; width: 400px">
    <p style="color: darkgray"> 주소는 최대 3개까지만 등록 가능합니다. </p>
    <form method="POST" action="${action}">
        <c:if test="${not empty address}">
            <div class="form-floating" style="margin-top: 20px; margin-bottom: 5px">
                <input type="number" name="address_id" class="form-control" id="address_id" value="${address.addressId}"
                       style="background-color: lightgray;" readonly placeholder="주소ID" required>
                <label for="address_id">주소 ID</label>
            </div>
        </c:if>


        <div class="form-floating" style="margin-bottom: 5px">
            <input type="text" name="zip_code" class="form-control" id="zip_code"
                   maxlength="5" value="${address.zipCode}" placeholder="우편번호" required>
            <label for="zip_code">우편 번호</label>
        </div>


        <div class="form-floating" style="margin-bottom: 5px">
            <input type="text" name="detailed_address" class="form-control" id="detailed_address"
                   value="${address.detailedAddress}" placeholder="모델 번호" required>
            <label for="detailed_address">상세 주소</label>
        </div>

        <button class="w-100 btn btn-lg btn-primary mt-3" type="submit">
            <c:choose>
                <c:when test="${empty address}">
                    등록
                </c:when>
                <c:otherwise>
                    수정
                </c:otherwise>
            </c:choose>
        </button>

    </form>
</div>