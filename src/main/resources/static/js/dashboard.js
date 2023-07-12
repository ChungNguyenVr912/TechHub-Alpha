$(function () {
    getUserList();
});
function refreshToken() {
    return new Promise((resolve, reject) => {
        fetch('/api/auth/refresh-token', {
            method: 'GET',
            credentials: 'include',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('refreshToken')}`,
            },
        })
            .then(response => {
                if (response.ok) {
                    return response.text()
                } else {
                    console.log('Refresh token expired!');
                    window.location.replace('/users/login')
                }
            })
            .then(responseData => {
                let newAccessToken = (JSON.parse(responseData)).accessToken;
                localStorage.setItem('accessToken', newAccessToken);
                console.log('token refreshed!');
            })
            .catch(error => {
                console.log(error)
            });
        resolve('done');
    });


}

function getUserList() {
    fetch('/api/admin/users', {
        method: 'GET',
        credentials: 'include',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
        },
    })
        .then(response => {
            if (response.ok) {
                return response.text();
            } else {
                try {
                    refreshToken().then(result => getUserList());
                } catch (error) {
                    console.log('Refresh token expired! (maybe, im not sure, please check!)');
                    window.location.replace('/users/login');
                }
            }

        })
        .then(data => {
            if (data){
                $('#show').html('');
                console.log(data);
                let listUser = JSON.parse(data);
                listUser.forEach(function (user) {
                    $('#show-users').append(
                        `<div class="d-flex justify-content-between">
                             <div class="col-2 rounded-2"><img height="100px" width="100px" src="${user.avatar}" alt="avatar"></div>
                             <div class="col-2">${user.username}</div>
                             <div class="col-3">${user.fullName}</div>
                             <div class="col-auto">${user.email}</div>
                             <div class="col-1">
                                <button class="btn btn-secondary">Edit</button>
                             </div>
                        </div>`
                    );
                })
            }
        })
        .catch(error => {
            console.error(error);
        });
}

function getUserInfo(){
    $.ajax(
        {
            url: '/api/users/get-info',
            method: 'get',
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
            },
            success: function (response) {
                let user = response.data;
                if (user) {

                    if (!user.staff){
                        window.location.replace('/users/login')
                    }
                }else {
                    refreshToken().then(result => getUserInfo());
                }
            },
            error: function (xhr, status, error) {
                console.log(status + xhr.responseJSON.message)
            }
        }
    );
}