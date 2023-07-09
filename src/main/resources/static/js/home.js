$(function () {
    //avatar dropdown
    var cookieUser = getCookieValue('user');
    var user;
    if(cookieUser !== ""){
        user = JSON.parse(cookieUser);
    }
    if (user) {
        $('#account').html(
            `<img src="${user.avatar}" alt="avatar" 
                     class="rounded-circle border"
                     style="width:50px; height:50px"/>`
        )
    }
    $('#dropDown').click(function () {
        if (user) {
            $('.drop-down').toggleClass('drop-down--active');
        }else {
            window.location.replace('/users/login')
        }
    });


    //logout button
    $('#logout').click(function () {
        document.cookie = "user=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
        window.location.replace('/users/login')
    });

    //expand card
    $(".option").click(function () {
        $(".option").removeClass("active");
        $(this).addClass("active");

    });



});

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


