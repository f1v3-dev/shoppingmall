<%--
  Created by IntelliJ IDEA.
  User: seungjo
  Date: 12/2/23
  Time: 3:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div style="margin: auto; width: 600px">

    <h2 style="text-align: center"> 마이페이지 </h2>
    <div class="d-grid gap-2 col-6 mx-auto">
        <button class="btn btn-outline-dark btn-lg" type="button" onclick="location.href='/mypage/update.do'">정보 수정</button>
        <button class="btn btn-outline-dark btn-lg" type="button" onclick="location.href='/mypage/address.do'">주소 관리</button>
        <button class="btn btn-outline-dark btn-lg" type="button" onclick="location.href='/mypage/order_list.do'">주문 명세 조회</button>
        <button class="btn btn-outline-dark btn-lg" type="button" onclick="location.href='/mypage/point.do'">포인트 사용 내역 조회</button>
    </div>
</div>