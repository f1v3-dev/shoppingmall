<%--
  Created by IntelliJ IDEA.
  User: seungjo
  Date: 11/30/23
  Time: 7:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div style="margin: auto; width: 500px">
    <h3> Category View Page</h3>
    <table class="table table-bordered text-center align-middle">
        <tbody>
        <tr>
            <td>카테고리 ID</td>
            <td>${category.categoryId}</td>
        </tr>

        <tr>
            <td>카테고리 이름</td>
            <td>${category.categoryName}</td>
        </tr>
        </tbody>
    </table>

    <button style="float: left" type="button" class="btn btn-info"
            onclick="location.href='/admin/category/update.do?id=${category.categoryId}'">수정
    </button>

    <form style="float: right" id="deleteForm" method="POST" action="/admin/category/delete.do">
        <input type=hidden name="id" value="${category.categoryId}"/>
        <button type="button" class="btn btn-danger" onclick="removeCategory()">삭제</button>
        <script>
            function removeCategory() {
                if (confirm("카테고리를 삭제하시겠습니까?") === true) {
                    document.getElementById('deleteForm').submit();
                } else {
                    return false;
                }
            }
        </script>
    </form>
</div>