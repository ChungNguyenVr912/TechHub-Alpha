$(function (){
    var productList;
    $.ajax(
        {
            url: '/api/products',
            method: 'GET',
            success: function (response){
                let productArea = $('#product-area');
                productArea.html("");
                productList = response.data;
                productList.forEach(function (product){
                    productArea.append(
                        `<div class="outer-card col-lg-3 col-md-6">
                            <div class="card">
                                <img
                                    src="${product.image}"
                                    class="card-img-top" alt="...">
                                    <div class="card-body">
                                        <h5 class="card-title">${product.name}</h5>
                                        <p class="card-text">${product.description}</p>
                                        <a href="#" class="btn btn-primary position-absolute sticky-bottom">Add to
                                            cart</a>
                                </div>
                            </div>
                        </div>`
                    )
                })
            },
            error:function (xhr, status, error) {
                console.log(xhr, status)

            }
        }
    );
})