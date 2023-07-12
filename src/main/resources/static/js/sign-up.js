$(function () {
    $('#customer-form').submit(function (event) {
        event.preventDefault();
        var username = $('#username').val();
        var email = $('#email').val();
        var password = $('#password').val();
        var avatar = document.getElementById('avatar').files[0];

        const form = new FormData();
        form.append("username", username);
        form.append("email", email);
        form.append("password", password);
        form.append("avatar", avatar);
        console.log(form);

        // console.log(JSON.stringify(form));
        // console.log('debug-here');
        $.ajax({
            url: '/api/users/register',
            type: 'POST',
            data: form,
            processData: false,
            contentType: false,
            success: function(response,status,jqXHR) {
                console.log(jqXHR+' : '+status);
                console.log(response);
                window.location.replace('/users/login')
            },
            error: function(xhr, status, error) {
                console.log(error.status + ':' + status);
            }
        });
    });
});


$('#avatar').change( function (event){
    const [file] = this.files;
    const preview = $('#preview');
    if (file) {
        preview.attr('src', URL.createObjectURL(file));
        preview.removeAttr('hidden');
    }else {
        preview.attr('hidden','hidden')
    }
});