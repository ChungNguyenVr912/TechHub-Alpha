$(function () {
    $('#loginForm').submit(function (event) {
        event.preventDefault();
        var username = $('#username').val();
        var password = $('#password').val();
        var rememberMe = $('#remember-me').val() === 'on' ? 'true' : 'false';
        var form = {
            username,
            password,
            rememberMe
        }

        fetch('/api/auth/login', {
            method: 'post',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(form)
        })
            .then(response => {
                if (response.ok) {
                    return response.text();
                } else {
                    $('#desc').html("Login failed, wrong username or password!");
                    launch_toast();
                }
            })
            .then(response => {
                const responseData = JSON.parse(response)
                localStorage.setItem('token', responseData.token)
                localStorage.setItem('refreshToken', responseData.refreshToken)
                $('#desc').html(response.message);
                loginAlert();
            });
    });
})

function setTokenCookie(cookieName, cookieValue, expirationDays) {
    var date = new Date();
    date.setTime(date.getTime() + (expirationDays * 24 * 60 * 60 * 1000));
    var expires = "expires=" + date.toUTCString();
    document.cookie = cookieName + "=" + cookieValue + ";" + expires + ";path=/;HttpOnly";
}

function setCookie(cookieName, cookieValue, expirationDays) {
    var date = new Date();
    date.setTime(date.getTime() + (expirationDays * 24 * 60 * 60 * 1000));
    var expires = "expires=" + date.toUTCString();
    document.cookie = cookieName + "=" + cookieValue + ";" + expires + ";path=/";
}

function launch_toast() {
    var x = document.getElementById("toast")
    x.className = "show";
    setTimeout(function () {
        x.className = x.className.replace("show", "");
    }, 3000);
}

function loginAlert() {
    let timerInterval
    Swal.fire({
        title: 'Login success!',
        html: 'Return home in <b></b> milliseconds.',
        timer: 1500,
        timerProgressBar: true,
        didOpen: () => {
            Swal.showLoading()
            const b = Swal.getHtmlContainer().querySelector('b')
            timerInterval = setInterval(() => {
                b.textContent = Swal.getTimerLeft()
            }, 100)
        },
        willClose: () => {
            clearInterval(timerInterval)
        }
    }).then((result) => {
        if (result.dismiss === Swal.DismissReason.timer) {
            window.location.replace('/home')
        }
    })
}