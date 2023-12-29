<%--
  Created by IntelliJ IDEA.
  User: seungjo
  Date: 12/6/23
  Time: 3:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    .card:hover {
        transform: scale(1.05);
        transition: transform 0.3s ease;
    }

    h1 {
        text-align: center;
        padding: 3%;
    }
</style>

<h1> 검색 결과</h1>

<div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
    <c:forEach items="${productList}" var="product">
        <div class="col">
            <div class="card shadow-sm" style="width: 100%; height: 100%;">
                <svg class="bd-placeholder-img card-img-top" width="100%" height="225"
                     xmlns="http://www.w3.org/2000/svg"
                     role="img" aria-label="Placeholder: Thumbnail" preserveAspectRatio="xMidYMid slice"
                     focusable="false">
                    <title>Product Image</title>
                    <rect width="100%" height="100%" fill="#fbfbfb"></rect>
                    <c:choose>
                        <c:when test="${product.productImage eq null}">
                            <image href="/resources/no-image.png" width="100%" height="100%"></image>
                        </c:when>
                        <c:otherwise>
                            <image href="/resources/images/${product.productImage}" width="100%" height="100%"></image>
                        </c:otherwise>
                    </c:choose>
                </svg>
                <div class="card-body">
                    <a class="stretched-link" href="/product/view.do?id=${product.productId}"
                       style="text-decoration-line: none; color: black; font-weight: bold">
                            ${product.modelName}</a>
                    <br/>
                    <p class="card-text" style="color: dimgray">${product.unitCost}원</p>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

<%-- 페이징 처리 --%>
<nav style="padding: 3%" aria-label="Page navigation example">
    <div>
        <ul class="pagination justify-content-center">

            <%-- Pagination << --%>
            <li class="page-item ${currentPage <= 1 ? 'disabled' : ''}">
                <a class="page-link" href="/product/search.do?name=${searchName}&page=${currentPage - 1}" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>

            <c:forEach begin="1" end="${totalPage}" varStatus="page">
                <li class="page-item"><a class="page-link"
                                         href="/product/search.do?name=${searchName}&page=${page.index}">${page.index}</a></li>
            </c:forEach>
            <%-- Pagination >> --%>
            <li class="page-item ${currentPage >= totalPage ? 'disabled' : ''}">
                <a class="page-link" href="/product/search.do?name=${searchName}&page=${currentPage + 1}" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>
    </div>
</nav>


<h5 style="color: darkgray">최근 본 상품</h5>
<div class="row row-cols-5">

    <c:forEach items="${recentViewList}" var="view">
        <div class="col" style="width: 150px">
            <div class="card" style="height: 100%">
                <c:choose>
                    <c:when test="${view.productImage eq null}">
                        <img src="/resources/no-image.png" width="100%" height="150px" alt="no-image">
                    </c:when>
                    <c:otherwise>
                        <img src="/resources/images/${view.productImage}" width="100%" height="150px"
                             alt="${view.modelName} image">
                    </c:otherwise>
                </c:choose>

                <div class="card-body">
                    <a class="stretched-link" href="/product/view.do?id=${view.productId}"
                       style="text-decoration-line: none; color: black; font-size: 14px; font-weight: bold; text-overflow: ellipsis;">
                            ${view.modelName}</a>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
