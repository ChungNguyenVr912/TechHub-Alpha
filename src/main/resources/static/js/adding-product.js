const form = $('#add-product');
const productHistoryList = $('#product-history');
const newProductName = $('#product-name');
const firstVariantGroup = $('#variant-1');
const secondVariantGroup = $('#variant-2');
const firstVariant = $('#variant-value-1');
const secondVariant = $('#variant-value-2');
const price = $('#product-price');
const stock = $('#product-stock');
var variantTypes;

$(function (){
    loadProductList();
    loadVariants();
    // $('#cancel-variant-1').click(() => firstVariant.selectedIndex = 0);
    // $('#cancel-variant-2').click(() => secondVariant.selectedIndex = 0);
    //////////////////////////////////////////////////////////////////////
    firstVariantGroup.change(function (){
        let value = firstVariantGroup.val();
        let variants;
        variantTypes.forEach(function (variantType){
            if (variantType.id == value){
                variants = variantType.variants;
            }
        });
        firstVariant.html("");
        firstVariant.append(`<option value="0" selected>Chọn variant</option>`)
        variants.forEach(function (variant){
            firstVariant.append(
                `<option value="${variant.id}">${variant.value}</option>`
            )
        })
    });
    ///////////////////////////////////////////////////////////////////////
    secondVariantGroup.change(function (){
        let value = secondVariantGroup.val();
        let variants;
        variantTypes.forEach(function (variantType){
            if (variantType.id == value){
                variants = variantType.variants;
            }
        });
        secondVariant.html("");
        secondVariant.append(`<option value="0" selected>Chọn variant</option>`)
        variants.forEach(function (variant){
            secondVariant.append(
                `<option value="${variant.id}">${variant.value}</option>`
            )
        })
    });
    ///////////////////////////////////////////////////////////////////////

    form.submit(function (event){
        event.preventDefault();
        let id = productHistoryList.val();
        let name = newProductName.val();
        let description = $('#product-description').val();
        let price = $('#product-price').val();
        let stock = $('#product-stock').val();
        let image = document.getElementById('product-img').files[0];
        let firstVariant = $('#variant-value-1').val();
        let secondVariant = $('#variant-value-2').val();
        let productForm = new FormData();
        if (id != 0){
            productForm.append('id',id);
        }else {
            productForm.append('name', name)
            productForm.append('description', description)
            productForm.append('image', image)
        }
        productForm.append('price', price);
        productForm.append('stock', stock);
        if (firstVariant != 0){
            productForm.append('firstVariantId', firstVariant);
        }
        if (secondVariant != 0){
            productForm.append('secondVariantId', secondVariant);
        }

        uploadProduct(productForm);
    });
});

function uploadProduct(productForm){
    $.ajax({
        url: '/api/manager/products',
        type: 'POST',
        headers:{
            'Authorization': `Bearer ${localStorage.getItem('accessToken')}`
        },
        data: productForm,
        processData: false,
        contentType: false,
        success: function(response,status,jqXHR) {
            console.log(jqXHR+' : '+status);
            console.log(response);
            if (jqXHR.status != 200){
                refreshToken().then(result => uploadProduct());
            }
        },
        error: function(xhr, status, error) {
            console.log(error.status + ':' + status);
        }
    });
}
function loadProductList() {
    $.ajax(
        {
            url: '/api/manager/products/types',
            type: 'get',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('accessToken')}`
            },
            success: function (response){
                let data = response.data;
                console.log(data);
                data.forEach(function (product){
                    productHistoryList.append(
                        `<option value="${product.id}">${product.name}</option>`
                    );
                })
            }
        }
    )
}

function loadVariants(){
    $.ajax(
        {
            url: '/api/manager/products/variant-types',
            type: 'get',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('accessToken')}`
            },
            success: function (response){
                variantTypes = response.data;
                // console.log(variantTypes);
                variantTypes.forEach(function (variantType){
                    if (variantType.id !== 1){
                        firstVariantGroup.append(
                            `<option value="${variantType.id}">${variantType.name}</option>`
                        );
                        secondVariantGroup.append(
                            `<option value="${variantType.id}">${variantType.name}</option>`
                        )
                    }
                })
            }
        }
    )
}