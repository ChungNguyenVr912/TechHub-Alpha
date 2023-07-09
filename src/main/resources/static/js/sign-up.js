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


// function loadList() {
//     $.getJSON(
//         'api/customers',
//         function (response) {
//             const list = $('#listCustomer');
//             list.html(
//                 `<div class="d-flex border py-1 px-2">
//                     <div class="col fw-bold fs-4">Name</div>
//                     <div class="col-3 fw-bold fs-4">Email</div>
//                     <div class="col-3 fw-bold fs-4">Address</div>
//                     <div class="col-2 fw-bold fs-4">Avatar</div>
//                     <div class="fs-4 fw-bold">Edit</div>
//                     <div class="fw-bold fs-4">Delete</div>
//                 </div>`
//             );
//             response.forEach(function (customer) {
//                 list.append(
//                     `<div class="d-flex border py-1 px-2">
//                         <div class="col">${customer.name}</div>
//                         <div class="col-3">${customer.email}</div>
//                         <div class="col-3">${customer.address}</div>
//                         <div class="col-2 overflow-hidden text-nowrap me-1"><a href="${customer.avatar}">Avatar url</a></div>
//                         <div><a href="/edit/${customer.id}">
//                             <button class="btn btn-warning">Edit</button>
//                         </a>
//                         </div>
//                         <div>
//                             <button class="btn btn-danger"
//                                     onClick="deleteCustomer(${customer.id})">Delete
//                             </button>
//                         </div>
//                     </div>`
//                 )
//             });
//         }
//     )
// }

function deleteCustomer(id) {
    $.ajax(
        {
            url: 'api/users/' + id,
            type: 'delete',
            success: function (result, status, jqXHR) {
                alert("Status:" + jqXHR.status + ":" + status)
                setTimeout(function () {
                    loadList();
                }, 500)
            }
            ,
            error: function (result, status, error) {
                alert("Error: " + status + error);
                console.log("Loi: " + error);
            }
        }
    );
}

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