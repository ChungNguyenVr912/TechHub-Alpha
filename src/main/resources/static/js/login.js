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
        console.log(JSON.stringify(form))
        console.log('debug')
        // $.ajax(
        //     {
        //         url: '/api/auth/login',
        //         type: 'POST',
        //         data: JSON.stringify(form),
        //         processData: false,
        //         contentType: 'application/json',
        //         success: function(response,status,jqXHR) {
        //             console.log(jqXHR+' : '+status);
        //             localStorage.setItem('accessToken', response.token)
        //             localStorage.setItem('refreshToken', response.refreshToken)
        //             setTokenCookie('token',response.token,7)
        //             setCookie('user',JSON.stringify(response.user),7)
        //
        //             $('#desc').html(response.message);
        //             launch_toast();
        //             setTimeout(function (){
        //                 window.location.replace('/home');},1500)
        //
        //         },
        //         error: function(xhr, status, error) {
        //             $('#desc').html(xhr.responseJSON.message);
        //             launch_toast();
        //             console.log(error.status + ':' + status);
        //         }
        //     }
        // );


        fetch('/api/auth/login', {
            method: 'post',
            credentials: 'include',
            headers: {
                // 'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
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
                let data = JSON.parse(response);
                localStorage.setItem('accessToken', data.token)
                localStorage.setItem('refreshToken', data.refreshToken)
                setTokenCookie('token', data.token, 7)
                setCookie('user', JSON.stringify(data.user), 7)
                $('#desc').html(data.message);
                // launch_toast();
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

function loginAlert(){
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
        /* Read more about handling dismissals below */
        if (result.dismiss === Swal.DismissReason.timer) {
            window.location.replace('/home')
        }
    })
}