<%--
  Created by IntelliJ IDEA.
  User: seungjo
  Date: 12/2/23
  Time: 2:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<div style="margin: auto; width: 800px">

    <div class="container">
        <div class="row">
            <div class="col">
                <%--    좌측에 사진을 크게 한개 두고 오른쪽에 상품 설명들 가져오기--%>
                <c:choose>
                    <c:when test="${product.productImage eq null}">
                        <img src="/resources/no-image.png" width="400px" height="600px"
                             style="float: left; margin-right: 15px">
                    </c:when>
                    <c:otherwise>
                        <img src="/resources/images/${product.productImage}" width="400px" height="600px"
                             style="float: left; margin-right: 15px">
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="col">
                <h3>${product.modelName}</h3>
                ${category.categoryName}
                <br/>
                <%--                table 써서 바꿔보기--%>
                <table>
                    <tbody>
                    <tr>
                        <td style="font-weight: bold; width: 100px;">Model Num.</td>
                        <td>${product.modelNumber}</td>
                    </tr>
                    <tr>
                        <td style="font-weight: bold">Price.</td>
                        <td>
                            <fmt:setLocale value="ko_KR"/>
                            <fmt:formatNumber value="${product.unitCost}" type="currency"/>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <div style="margin-top: 50px; margin-bottom: 20px;">
                    <h5 style="margin-bottom: 10px; font-weight: bold">DESCRIPTION</h5>
                    <p>${product.description}</p>
                </div>


                <hr style="border: solid 1px lightgray; margin-top: 15px; margin-bottom: 15px;">

                <form method="POST">
                    <input type="hidden" name="productId" value="${product.productId}">
                    <input type="hidden" name="unitCost" value="${product.unitCost}">
                    <label for="quantity" style="margin-bottom: 15px">
                        Quantity <input type="number" id="quantity" name="quantity" value="1" min="1" max="100"
                                        style="margin-left: 10px; width: 60px; height: 30px;">
                    </label>
                    <button type="submit" class="btn btn-lg btn-dark" style="width: 100%; border-radius: 15px; margin-bottom: 10px" formaction="/mypage/cart/add.do">Add To Cart</button>
                    <button type="submit" class="btn btn-lg btn-dark" style="width: 100%; border-radius: 15px" formaction="/mypage/orderByView.do">Buy It Now</button>
                </form>
            </div>
        </div>
    </div>
</div>
