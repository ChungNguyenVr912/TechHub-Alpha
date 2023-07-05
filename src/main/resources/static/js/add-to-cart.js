$('#cart').click(function (e){
    if(!this.classList.contains('loading')) {

        this.classList.add('loading');

        setTimeout(() => this.classList.remove('loading'), 3700);

    }
    e.preventDefault();
})
