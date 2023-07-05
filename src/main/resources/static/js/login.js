$(function () {
    $('#loginForm').submit(function (event){
        event.preventDefault();
        var username = $('#username').val();
        var password = $('#password').val();
        var form = {
            username,
            password
        }
        $.ajax(
            {
                url: '/api/auth/login',
                type: 'POST',
                data: JSON.stringify(form),
                processData: false,
                contentType: 'application/json',
                success: function(response,status,jqXHR) {
                    console.log(jqXHR+' : '+status);
                    console.log(response);
                    window.localStorage.setItem('token', response.token);
                    window.location.replace('/users');
                },
                error: function(xhr, status, error) {
                    console.log(error.status + ':' + status);
                }
            }
        )
    });
})