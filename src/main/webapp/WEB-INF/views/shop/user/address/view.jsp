<%--
  Created by IntelliJ IDEA.
  User: seungjo
  Date: 12/4/23
  Time: 2:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div style="margin: auto; width: 500px">
    <h3> Address View Page</h3>
    <table class="table table-bordered text-center align-middle">
        <tbody>
        <tr>
            <td>주소 ID</td>
            <td>${address.addressId}</td>
        </tr>

        <tr>
            <td>우편 번호</td>
            <td>${address.zipCode}</td>
        </tr>

        <tr>
            <td>상세 주소</td>
            <td>${address.detailedAddress}</td>
        </tr>
        </tbody>
    </table>

    <button style="float: left" type="button" class="btn btn-info"
            onclick="location.href='/mypage/address/update.do?id=${address.addressId}'">수정
    </button>

    <form style="float: right" id="deleteForm" method="POST" action="/mypage/address/delete.do">
        <input type=hidden name="id" value="${address.addressId}"/>
        <button type="button" class="btn btn-danger" onclick="removeCategory()">삭제</button>
        <script>
            function removeCategory() {
                if (confirm("주소를 삭제하시겠습니까?") === true) {
                    document.getElementById('deleteForm').submit();
                } else {
                    return false;
                }
            }
        </script>
    </form>
</div>