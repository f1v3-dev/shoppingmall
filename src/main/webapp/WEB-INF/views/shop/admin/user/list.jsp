<%--
  Created by IntelliJ IDEA.
  User: seungjo
  Date: 11/30/23
  Time: 7:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--userList로 넘어옴 -> c:forEach로 처리하면 될듯 --%>
<div style="width: 800px; margin: auto;">
    <table class="table table-hover text-center">
        <thead>
        <tr>
            <th scope="col">ID</th>
            <th scope="col">이름</th>
            <th scope="col">생년월일</th>
            <th scope="col">포인트</th>
            <th scope="col">가입 일자</th>
            <th scope="col">상세 정보</th>
        </tr>
        </thead>
        <tbody class="text-center">
        <c:forEach items="${userList}" var="user">

            <fmt:parseDate value="${user.createdAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="parseOrderDate" type="both"/>
            <fmt:formatDate value="${parseOrderDate}" pattern="yyyy-MM-dd" var="createdAt"/>

            <tr>
                <td>${user.userId}</td>
                <td>${user.userName}</td>
                <td>${user.userBirth}</td>
                <td>${user.userPoint}</td>
                <td>${createdAt}</td>
                <td><a href="<c:url value="/admin/user/view.do?id=${user.userId}"/>">VIEW</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <nav aria-label="Page navigation example">
        <div>
            <ul class="pagination justify-content-center">

<%--                &lt;%&ndash; Pagination << &ndash;%&gt;--%>
<%--                <li class="page-item">--%>
<%--                    <a class="page-link" href="#" aria-label="Previous">--%>
<%--                        <span aria-hidden="true">&laquo;</span>--%>
<%--                    </a>--%>
<%--                </li>--%>

                <c:forEach begin="1" end="${page}" varStatus="page">
                    <li class="page-item"><a class="page-link" href="/admin/user/${role}.do?page=${page.index}">${page.index}</a></li>
                </c:forEach>
<%--                &lt;%&ndash; Pagination >> &ndash;%&gt;--%>
<%--                <li class="page-item">--%>
<%--                    <a class="page-link" href="#" aria-label="Next">--%>
<%--                        <span aria-hidden="true">&raquo;</span>--%>
<%--                    </a>--%>
<%--                </li>--%>
            </ul>

        </div>
    </nav>
</div>