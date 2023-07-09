$(function (){
    $('#getList').click(function (event) {
        $.ajax(
            {
                url: '/api/users/all',
                type: 'get',
                headers:{
                    'Authorization': 'Bearer '+localStorage.getItem('accessToken')
                },
                success: function (response){
                    console.log(response);
                    $('#show').html('')
                    response.forEach(function (user) {
                        $('#show').append(
                            `<div>
                                <span><img height="100px" width="100px" src="${user.avatar}" alt="avatar"></span>
                                <span>${user.username}</span>
                                <span>${user.fullName}</span>
                                <span>${user.email}</span>
                            </div>`
                        );
                    })
                },
                error:function (xhr, status, error) {
                    console.log(status);
                    console.log(xhr.responseJSON.message);
                    alert(xhr.responseJSON.message)
                    console.log(xhr.status)
                }
            }
        )
    });

});