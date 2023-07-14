function getUserInfo() {
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
                    localStorage.setItem('loggedIn', 'true')
                    $('#dropDown').html(
                        `<img src="${user.avatar}" alt="avatar" 
                     class="rounded-circle border"
                     style="width:50px; height:50px"/>`
                    )
                    if (!user.staff){
                        $('#dashboard').attr('hidden', 'hidden')
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

function getCookieValue(cookieName) {
    const name = cookieName + "=";
    const decodedCookie = decodeURIComponent(document.cookie);
    const cookieArray = decodedCookie.split(';');
    for (let i = 0; i < cookieArray.length; i++) {
        let cookie = cookieArray[i];
        while (cookie.charAt(0) === ' ') {
            cookie = cookie.substring(1);
        }
        if (cookie.indexOf(name) === 0) {
            return cookie.substring(name.length, cookie.length);
        }
    }
    return "";
}
$(function () {
    getUserInfo();

    $('#dropDown').click(function () {
        let loggedIn = localStorage.getItem('loggedIn')
        if (loggedIn === 'true') {
            $('.drop-down').toggleClass('drop-down--active');
        } else {
            window.location.replace('/users/login')
        }
    });
    //logout button
    $('#logout').click(function () {
        // document.cookie = "accessToken=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
        // document.cookie = "refreshToken=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
        localStorage.clear();
        window.location.reload();
    });

    //expand card
    $(".option").click(function () {
        $(".option").removeClass("active");
        $(this).addClass("active");

    });

    $('#dashboard').click(function (){
        window.location.replace('/admin/dashboard')
    })

});



