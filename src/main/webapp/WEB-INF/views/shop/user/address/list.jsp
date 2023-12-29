<%--
  Created by IntelliJ IDEA.
  User: seungjo
  Date: 12/4/23
  Time: 1:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div style="width: 1000px; margin: auto;">
    <h3> 주소 목록 </h3>
    <p style="color: darkgray"> 주소는 최대 3개까지만 등록 가능합니다. </p>
    <table class="table table-hover text-center">
        <thead>
        <tr>
            <th scope="col">우편 번호</th>
            <th scope="col">상세 주소</th>
            <th scope="col">상세 보기</th>
        </tr>
        </thead>
        <tbody class="text-center">
        <c:forEach items="${addressList}" var="address">
            <tr>
                <td>${address.zipCode}</td>
                <td>${address.detailedAddress}</td>
                <td><a href="<c:url value="/mypage/address/view.do?id=${address.addressId}"/>">VIEW</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div class="d-md-flex justify-content-md-end">
        <button type="button" class="btn btn-success justify-content-end"
                onclick="location.href='/mypage/address/register.do'">주소 등록
        </button>
    </div>
</div>