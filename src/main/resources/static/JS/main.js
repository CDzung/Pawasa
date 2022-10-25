function open_noti(i, k) {
    if (i == 1) {
        document.querySelector(k).style.display = 'block';
    } else {
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

function showCategory(i) {
    $.ajax({
        type: "GET",
        url: "/loadMenu",
        data: 'id=' + i,
        success: function (data) {
            $('.catalog_menu_dropdown .fhs_column_stretch').html(data);
        }
    });
}

function tabslider(i,j) {
    if(j === true){
        var current_tab = $("#categorytab-" + i + " .tabslider-tabs li:first-child");
        current_tab.addClass("active")
        var a = current_tab.attr("rel");
        if (i === 'gia-noi-bat') {
            $('#tabdeal-gia-noi-bat').empty();
        }
        if (i === 'mglnv') {
            $('#tabmglnv-mglnv').empty();
        }
        $("#categorytab-" + i + " >div .tabslider-loading-icon").show();
        $.ajax({
            type: "GET",
            url: "/loadProduct",
            data: 'id=' + a,
            success: function (data) {
                $("#categorytab-" + i + " >div .tabslider-loading-icon").hide();
                if (i === 'gia-noi-bat') {
                    $('#tabdeal-gia-noi-bat').html(data)
                }
                if (i === 'mglnv') {
                    $('#tabmglnv-mglnv').html(data)
                }
            }
        })
    }
    else{
        $("#categorytab-" + i + " .tabslider-tabs li").click(function (e) {
            var current_tab = $("#categorytab-" + i + " .tabslider-tabs li.active");
            console.log(current_tab)
            current_tab.removeClass("active");
            var current_tab_label = $(e.target);
            var a = current_tab_label.attr("rel");
            console.log(a);
            current_tab_label.addClass("active");
            if (i === 'gia-noi-bat') {
                $('#tabdeal-gia-noi-bat').empty();
            }
            if (i === 'mglnv') {
                $('#tabmglnv-mglnv').empty();
            }
            $("#categorytab-" + i + " >div .tabslider-loading-icon").show();
            // loadding
            $.ajax({

                type: "GET",
                url: "/loadProduct",
                data: 'id=' + a,
                success: function (data) {
                    $("#categorytab-" + i + " >div .tabslider-loading-icon").hide();
                    // off loading
                    if (i === 'gia-noi-bat') {
                        $('#tabdeal-gia-noi-bat').html(data)
                    }
                    if (i === 'mglnv') {
                        $('#tabmglnv-mglnv').html(data)
                    }
                }
            })
        });
    }
}
function changepass(){
    var i = $(".fhs-edit-account-password-form").css('display');
    console.log(i)
    if(i === "none"){
        $(".fhs-edit-account-password-form").show();
    }
    else{
        $(".fhs-edit-account-password-form").hide();
    }
}
$.ready(showCategory(1))
$.ready(tabslider("gia-noi-bat",true))
$.ready(tabslider("mglnv",true))

function checkLogin(i){
    if(i != null){
        window.location.href="/pawasa/Account/profile";
    }
    else {
        window.location.href="/pawasa/login"
    }
}