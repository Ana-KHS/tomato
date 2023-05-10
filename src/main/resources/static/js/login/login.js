$(document).ready(function() {
    const result = document.getElementById('loginStatus').value;

    if(result === 'Fail') {
        alert("로그인에 실패하였습니다.\n아이디와 비밀번호를 다시 확인해 주세요.");
        return false;
    }else {
        return true;
    }
})
