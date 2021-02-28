let articleId = null;
$(function () {
    $(".modal-area").load(MODAL_PANELS_URL);
    /*加载导航栏*/
    $('.common-header').load(NAVIGATOR_PANEL_URL);
    /*加载统计信息*/
    $.get(SITE_INFO_PANEL_URL, function (data, status) {
        $('.site-info-area').append(data);
    });
    articleId = $('.content-right').attr("article-id");
    /*加载文章内容*/
    $('.content-right .content-right-area').load("../html/article_detail_panel.html?articleId=" + articleId);
    /*加载最新文章列表面板*/
    $.get(NEWEST_ARTICLE_PANEL_URL, function (data, status) {
        $('.newest-article-area').append(data);
    });
    /*加载最热文章类表面板*/
    $.get(HOTTEST_ARTICLE_PANEL_URL, function (data, status) {
        $('.hottest-article-area').append(data);
    });
    /*加载文章分类标签面板*/
    $.get(CLASSIFY_LABELS_PANEL_URL, function (data, status) {
        $('.classify-labels-area').append(data);
    });
    /*加载文章标签面版*/
    $.get(ARTICLE_LABELS_PANEL_URL, function (data, status) {
        $('.article-labels-area').append(data);
    });
    /*加载底部备案信息*/
    $.get(BOTTOM_PANEL_URL, function (data, status) {
        $('.common-footer').append(data);
    });
    $(window).scroll(function () {
        if ($('.content-left-area').height() > $(window).height()) {
            $('.content-left-area').css('top', $(window).height() - $('.content-left-area').height());
        } else {
            $('.content-left-area').css('top', "50px");
        }
    });

});
