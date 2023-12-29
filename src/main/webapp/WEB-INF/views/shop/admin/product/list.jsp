<%--
  Created by IntelliJ IDEA.
  User: seungjo
  Date: 11/30/23
  Time: 7:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div style="width: 1000px; margin: auto;">
    <%-- req.setAttribute("productList", productPage.getContent()); <- productList를 뽑아서 표 형태로 보여줌--%>
    <table class="table table-hover text-center">
        <thead>
        <tr>
            <th scope="col">상품 번호</th>
            <th scope="col">모델 번호</th>
            <th scope="col">모델명</th>
            <th scope="col">단가</th>
            <th scope="col">상세 정보</th>
        </tr>
        </thead>
        <tbody class="text-center">
        <c:forEach items="${productList}" var="product">
            <tr>
                <td>${product.productId}</td>
                <td>${product.modelNumber}</td>
                <td>${product.modelName}</td>

                <td>
                    <fmt:setLocale value="ko_KR"/>
                    <fmt:formatNumber value="${product.unitCost}" type="currency"/>
                </td>
                <td><a href="<c:url value="/admin/product/view.do?id=${product.productId}"/>">VIEW</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <nav aria-label="Page navigation example">
        <div>
            <ul class="pagination justify-content-center">

                <%-- Pagination << --%>
                <li class="page-item ${currentPage <= 1 ? 'disabled' : ''}">
                    <a class="page-link" href="/admin/product.do?page=${currentPage - 1}" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>

                <c:forEach begin="1" end="${totalPage}" varStatus="page">
                    <li class="page-item"><a class="page-link"
                                             href="/admin/product.do?page=${page.index}">${page.index}</a></li>
                </c:forEach>
                    <%-- Pagination >> --%>
                    <li class="page-item ${currentPage >= totalPage ? 'disabled' : ''}">
                        <a class="page-link" href="/admin/product.do?page=${currentPage + 1}" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
            </ul>

            <div class="d-md-flex justify-content-md-end">
                <button type="button" class="btn btn-success justify-content-end"
                        onclick="location.href='/admin/product/register.do'">상품 등록
                </button>
            </div>
        </div>
    </nav>
</div>