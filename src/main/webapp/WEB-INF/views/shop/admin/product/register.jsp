<%--
  Created by IntelliJ IDEA.
  User: seungjo
  Date: 11/28/23
  Time: 3:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:choose>
    <c:when test="${empty product}">
        <c:set var="action" value="/admin/product/register"/>
    </c:when>
    <c:otherwise>
        <c:set var="action" value="/admin/product/update"/>
    </c:otherwise>
</c:choose>

<div style="margin: auto; width: 400px">

    <%--            <form method="post" action="/file/fileUpload" enctype="multipart/form-data">--%>
    <form method="POST" action="${action}" enctype="multipart/form-data">

        <%--        카테고리 3개 선택할 수 있게해야됨 <c:forEach>로 3번 돌리자 --%>
        <c:choose>
            <c:when test="${empty product}">
                <c:forEach begin="1" end="3" var="i">
                    <select class="form-select" name="category_id${i}" id="category_id${i}" style="margin-bottom: 5px"
                            required>
                        <option value="" style="color: lightgray" selected disabled hidden>카테고리를 선택해주세요</option>
                        <c:forEach var="category" items="${categoryList}">
                            <option value="${category.categoryId}">${category.categoryName}</option>
                        </c:forEach>
                        <option value="NULL">선택 안함</option>
                    </select>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <c:forEach var="category" items="${setCategoryList}" varStatus="loop">
                    <c:set var="i" value="${loop.index + 1}"/>
                    <select class="form-select" name="category_id${i}" id="category_id${i}" style="margin-bottom: 5px"
                            required>
                        <option value="${category.categoryId}" selected>${category.categoryName}</option>
                        <c:forEach var="newCategory" items="${categoryList}">
                            <option value="${newCategory.categoryId}">${newCategory.categoryName}</option>
                        </c:forEach>
                        <option value="NULL">선택 안함</option>
                    </select>
                </c:forEach>

                <c:if test="${setCategoryList.size() < 3}">
                    <c:forEach begin="${setCategoryList.size() + 1}" end="3" var="i">
                        <select class="form-select" name="category_id${i}" id="category_id${i}"
                                style="margin-bottom: 5px"
                                required>
                            <option value="NULL" selected>선택 안함</option>
                            <c:forEach var="category" items="${categoryList}">
                                <option value="${category.categoryId}">${category.categoryName}</option>
                            </c:forEach>
                        </select>
                    </c:forEach>
                </c:if>
            </c:otherwise>
        </c:choose>


        <c:if test="${not empty product}">
            <div class="form-floating" style="margin-top: 20px; margin-bottom: 5px">
                <input type="number" name="product_id" class="form-control" id="product_id" value="${product.productId}"
                       style="background-color: lightgray;" readonly placeholder="상품ID" required>
                <label for="product_id">상품 ID</label>
            </div>
        </c:if>


        <div class="form-floating" style="margin-bottom: 5px">
            <input type="text" name="model_number" class="form-control" id="model_number"
                   maxlength="10" value="${product.modelNumber}" placeholder="모델 번호"
                   required>
            <label for="model_number">모델 번호</label>
        </div>

        <div class="form-floating" style="margin-bottom: 5px">
            <input type="text" name="model_name" class="form-control" id="model_name" placeholder="모델명"
                   value="${product.modelName}" required>
            <label for="model_name">모델명</label>
        </div>

        <div class="form-floating" style="margin-bottom: 5px">
            <input type="number" name="unit_cost" class="form-control" id="unit_cost" placeholder="단가"
                   value="${product.unitCost}" required>
            <label for="unit_cost">단가</label>
        </div>

        <label style="margin-top: 5px; " for="description">상세정보</label>
        <div class="form-floating" style="margin-bottom: 5px;">
    <textarea style="height: 200px" id="description" name="description"
              class="form-control">${product.description}</textarea>
        </div>

        <%-- Update라면 기존에 있던 이미지를 불러와야 하는데 어떻게하지--%>
        <div class="input-group">
            <label for="product_image" style="color: lightgray; font-size: small"> jpeg, png 파일의 이미지만 넣어주세요 </label>
            <input type="file" id="product_image" name="product_image" accept="image/jpeg, image/png">
        </div>
        <button class="w-100 btn btn-lg btn-primary mt-3" type="submit">
            <c:choose>
                <c:when test="${empty product}">
                    상품 등록
                </c:when>
                <c:otherwise>
                    상품 수정
                </c:otherwise>
            </c:choose>
        </button>

    </form>
</div>

