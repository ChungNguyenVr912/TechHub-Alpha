

$(function (){
    const form = $('#add-product');
    const productHistoryList = $('#product-history');
    const newProductName = $('#product-name');
    const firstVariantGroup = $('#variant-group-1');
    const secondVariantGroup = $('#variant-group-2');
    const firstVariant = $('#variant-1');
    const secondVariant = $('#variant-2');
    const price = $('#product-price');
    const stock = $('#product-stock');
    const productImage = $('#product-img');

    loadProductList();
});

function loadProductList() {
    $.ajax(
        {
            url: '/api/products',
            type: 'get',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('accessToken')}`
            },
            success: function (response){
                let data = response.data;
            }
        }
    )
}