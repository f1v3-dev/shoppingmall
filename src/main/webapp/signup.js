const SERVER_URL = "http://localhost:8080";

window.addEventListener("DOMContentLoaded", function () {
    'use strict';

    const signupForm = document.getElementById("signupForm");

    const validateForm = function (form) {
        const id = form["user_id"];
        const name = form["user_name"];
        const password = form["user_password"];
        const birth = form["user_birth"];

        checkDuplicate(id, function (check) {
            if (!check) {
                alert('해당 아이디는 이미 존재합니다. 다른 아이디를 사용해주세요.');
                id.focus();
                return false;
            }
        });

        if (id.value.trim() == '') {
            alert("아이디를 입력해주세요.");
            id.focus();
            return false;
        } else if (name.value.trim() == '') {
            alert("이름을 입력해주세요.");
            name.focus();
            return false;
        } else if (password.value.trim() == '') {
            alert("비밀번호를 입력해주세요.");
            password.focus();
            return false;
        } else if (birth.value.trim() == '') {
            alert("생년월일을 입력해주세요.");
            birth.focus();
            return false;
        }

        return true;
    };


    signupForm.addEventListener('submit', function (event) {
        event.preventDefault();

        if (!validateForm(signupForm)) {
            return false;
        }
    })

});

function checkDuplicate() {

    const userId = document.getElementById("user_id");
    if (userId.value.trim() == '') {
        alert("아이디를 입력해주세요.");
        userId.focus();
        return false;
    } else if (userId.value.length <= 4) {
        alert("아이디는 5자 이상 입력해주세요.");
        userId.focus();
        return false;
    }

    const xhr = new XMLHttpRequest();
    const url = SERVER_URL + "/signup/checkDuplicate?userId=" + userId.value;
    console.log(url);

    xhr.addEventListener('load', function () {
        const response = this.response;
        console.log(response);
        console.log(response.value);

        if (this.response.notExists == "true") {
            alert("해당 아이디는 사용 가능합니다.")
            return true;
        } else {
            alert("해당 아이디는 이미 존재합니다. 다른 아이디를 사용해주세요.")
            return false;
        }
    });

    xhr.open("GET", url);
    xhr.setRequestHeader("content-type", "application/json");
    xhr.responseType = 'json';
    xhr.send('');
}