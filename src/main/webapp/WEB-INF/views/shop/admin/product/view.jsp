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

<div style="margin: auto; width: 700px">

    <h3> Product View Page </h3>
    <table class="table table-bordered text-center align-middle">
        <tbody>
        <tr>
            <td>상품 ID</td>
            <td>${product.productId}</td>
        </tr>

        <tr>
            <td>카테고리</td>
            <td>
                <c:forEach items="${categoryList}" var="category">
                    ${category.categoryName} &nbsp;
                </c:forEach>
            </td
        </tr>


        <tr>
            <td>모델 번호</td>
            <td>${product.modelNumber}</td>
        </tr>

        <tr>
            <td>모델명</td>
            <td>${product.modelName}</td>
        </tr>

        <tr>
            <td>단가</td>
            <td>
                <fmt:setLocale value="ko_KR"/>
                <fmt:formatNumber value="${product.unitCost}" type="currency"/>
            </td>
        </tr>

        <tr>
            <td>상세 정보</td>
            <td>${product.description}</td>
        </tr>

        <tr>
            <td>상품 이미지</td>

            <td>
                <c:choose>
                    <c:when test="${product.productImage eq null}">
                        <img src="/resources/no-image.png" width="300px" height="300px" alt="no-image">
                    </c:when>
                    <c:otherwise>
                        <img src="/resources/images/${product.productImage}" width="300px" height="300px" alt="image">
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
        </tbody>
    </table>

    <button style="float: left" type="button" class="btn btn-info"
            onclick="location.href='/admin/product/update.do?id=${product.productId}'">수정
    </button>

    <form style="float: right" id="deleteForm" method="POST" action="/admin/product/delete.do">
        <input type=hidden name="id" value="${product.productId}"/>
        <button type="button" class="btn btn-danger" onclick="removeProduct()">삭제</button>
        <script>
            function removeProduct() {
                if (confirm("정말 삭제하시겠습니까?") === true) {
                    alert("상품이 삭제되었습니다.");
                    document.getElementById('deleteForm').submit();
                } else {
                    return false;
                }
            }
        </script>
    </form>

</div>

