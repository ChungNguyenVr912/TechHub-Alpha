$(function () {
    $('#loginForm').submit(function (event){
        event.preventDefault();
        var username = $('#username').val();
        var password = $('#password').val();
        var rememberMe = $('#remember-me').val() === 'on'?'true':'false';
        var form = {
            username,
            password,
            rememberMe
        }
        console.log(JSON.stringify(form))
        console.log('debug')
        $.ajax(
            {
                url: '/api/auth/login',
                type: 'POST',
                data: JSON.stringify(form),
                processData: false,
                contentType: 'application/json',
                success: function(response,status,jqXHR) {
                    console.log(jqXHR+' : '+status);
                    localStorage.setItem('accessToken', response.token)
                    setTokenCookie('token',response.token,7)
                    setCookie('user',JSON.stringify(response.user),7)
                    $('#desc').html(response.message);
                    launch_toast();
                    setTimeout(function (){
                        window.location.replace('/home');},4000)

                },
                error: function(xhr, status, error) {
                    $('#desc').html(xhr.responseJSON.message);
                    launch_toast();
                    console.log(error.status + ':' + status);
                }
            }
        )
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
    setTimeout(function(){ x.className = x.className.replace("show", ""); }, 4000);
}