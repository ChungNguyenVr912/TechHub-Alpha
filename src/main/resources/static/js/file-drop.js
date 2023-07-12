$('#product-img').change( function (event){
    const [file] = this.files;
    const preview = $('#preview');
    if (file) {
        preview.attr('src', URL.createObjectURL(file));
        preview.removeAttr('hidden');
    }else {
        preview.attr('hidden','hidden')
    }
});
