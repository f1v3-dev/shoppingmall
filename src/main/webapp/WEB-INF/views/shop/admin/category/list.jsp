<%--
  Created by IntelliJ IDEA.
  User: seungjo
  Date: 11/30/23
  Time: 7:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div style="width: 800px; margin: auto;">
    <table class="table table-hover text-center">
        <thead>
        <tr>
            <th scope="col">카테고리 번호</th>
            <th scope="col">카테고리 이름</th>
            <th scope="col">상세 정보</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="category" items="${categoryList}">
            <tr>
                <td>${category.categoryId}</td>
                <td>${category.categoryName}</td>
                <td><a href="<c:url value="/admin/category/view.do?id=${category.categoryId}"/>">VIEW</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <nav aria-label="Page navigation example">
        <div>
            <ul class="pagination justify-content-center">

                <%-- Pagination << --%>
                    <li class="page-item ${currentPage <= 1 ? 'disabled' : ''}">
                        <a class="page-link" href="/admin/category.do?page=${currentPage - 1}" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>

                <c:forEach begin="1" end="${totalPage}" varStatus="page">
                    <li class="page-item"><a class="page-link"
                                             href="/admin/category.do?page=${page.index}">${page.index}</a></li>
                </c:forEach>

                <%-- Pagination >> --%>
                <li class="page-item ${currentPage >= totalPage ? 'disabled' : ''}">
                    <a class="page-link" href="/admin/category.do?page=${currentPage + 1}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </ul>

            <div class="d-md-flex justify-content-md-end">
                <button type="button" class="btn btn-success justify-content-end"
                        onclick="location.href='/admin/category/register.do'">카테고리 등록
                </button>
            </div>
        </div>
    </nav>
</div>