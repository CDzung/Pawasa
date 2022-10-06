function open_noti(i, k) {
    if (i == 1) {
        document.querySelector(k).style.display = 'block';
    }
    else {
        document.querySelector(k).style.display = 'none';
    }
}

$('.catalog_menu_dropdown .verticalmenu > li > a').removeClass('dropdown-toggle');
$('.catalog_menu_dropdown .verticalmenu > li > a').removeAttr('data-toggle');

$('.catalog_menu_dropdown .verticalmenu > li > a').hover(function () {
    $('.catalog_menu_dropdown .verticalmenu > li').removeClass('active');
    $(this).parent().addClass('active');
    showMenuContent();
});
showMenuContent();
function showMenuContent() {
    let $item_active = $('.catalog_menu_dropdown .verticalmenu > li.active');
    if ($item_active.length <= 0) {
        $item_active = $('.catalog_menu_dropdown .verticalmenu > li').first();
        $item_active.addClass('active');
    } else {
        $item_active = $item_active.first();
    }

    if ($item_active.length > 0) {
        let $dropdown = $item_active.find('.dropdown-menu-inner');
        let $title = $item_active.children('a');
        $('.fhs_header_desktop .fhs_menu_title').html($title.html());
        $('.fhs_header_desktop .fhs_menu_content').html($dropdown.html());
    }
}


function resizeHeightImg() {
    let cW = $('.inivoslider .swiper-wrapper').width();
    let aW = $('.inivoslider .swiper-wrapper').attr('width');
    let aH = $('.inivoslider .swiper-wrapper').attr('height');
    let img_height = Math.round((cW / aW) * aH);
    $('.inivoslider .swiper-wrapper').css("height", img_height);
}
resizeHeightImg();
$(window).on('resize', function () {
    resizeHeightImg();
});
var inivoslider;
$(document).ready(function () {
    inivoslider = new Swiper('.inivoslider', {
        autoplay: {
            delay: 3000,
        },
        loop: true,
        preloadImages: false,
        lazy: true,
        navigation: {
            nextEl: '.swiper-button2-next',
            prevEl: '.swiper-button2-prev',
        },
        pagination: {
            el: ".swiper-pagination",
        },
    });
});