
let signInUrl = "/api/auth/authenticate";
let forgotPasswordUrl = "/auth/forgot-password"
let changePasswordUrl = "/auth/change-password"
let delayTime = 5000

$(document).ready(function () {
    console.log("Login page is ready");
    let $loginSubmit = $("#login-submit")
    $("#login-submit").on('click',function (){
        let loginId = $("#login-id").val();
        let password = $("#password").val();
        let token = localStorage.getItem("token")
        delayButton($loginSubmit, delayTime);
        console.log(loginId + " - " + password + " - " + token)
        ajaxPost(signInUrl, {loginId, password, token}, afterSubmit, handleError)
    })
});

function handleError(error){
    console.log(error)
}
function afterSubmit(data) {
    console.log(data)
    if (data.status === "CHANGE_PASSWORD_REQUIRED") {
        window.location = changePasswordUrl;
        return
    }

    if (data.status === "INVALID_CREDENTIAL") {
        Swal.fire("Invalid!", "아이디 또는 비밀번호를 다시 확인하세요.", "error")
        return
    }

    if (data.status === "PASSWORD_ERROR_COUNT_EXCEED") {
        Swal.fire("Wrong 5 times!", "비밀번호를 5회 이상 틀렸습니다. 비밀번호찾기를 이용해주세요.", "error")
            .then(function () {
                window.location = "/auth/forgot-password";
            })
        return
    }

    if (data.status === "SMS_OTP_REQUIRED") {
        window.location = "/auth/sms-otp"
        return
    }

    if (data.status === "AUTHENTICATED") {
        console.log(data.jwt);
        localStorage.setItem("Authorization","Bearer " + data.jwt);
        window.location = "/"

        // console.log("Ready to forward admin site")
    }
}
function disableButton(e) {
    $(e).attr("disabled", "disabled")
}

function enableButton(e, timeout) {
    setTimeout(() => {
        $(e).removeAttr("disabled")
    }, timeout);
}
function delayButton(e, time) {
    disableButton(e)
    enableButton(e, time || 2000)
}