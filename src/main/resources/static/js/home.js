$(function () {
    $('#getList').click(function (event) {
        $.ajax(
            {
                url: '/api/users/all',
                type: 'get',
                headers:{
                    'Authorization': 'Bearer '+localStorage.getItem('token')
                },
                success: function (response){
                    console.log(response);
                    response.forEach(function (user) {
                        $('#show').append(
                            `<div>
                                <span><img height="100px" src="${user.avatar}" alt="avatar"></span>
                                <span>${user.username}</span>
                                <span>${user.fullName}</span>
                                <span>${user.email}</span>
                            </div>`
                        );
                    })
                },
                error:function (xhr, status, error) {
                    console.log(status);
                    console.log(error);
                }
            }
        )
    })
})