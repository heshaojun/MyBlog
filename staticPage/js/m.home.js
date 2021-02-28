$(function () {
    $(".modal-area").load(MODAL_PANELS_URL);
    /*加载导航栏*/
    $('.common-header').load(NAVIGATOR_PANEL_URL);
    /*加载文章列表*/
    $('.content-right .content-right-area').load(ARTICLE_LIST_PANEL_URL);
    /*加载统计信息*/
    $.get(SITE_INFO_PANEL_URL, function (data) {
        $('.site-info-area').append(data);
    });
    /*加载最新文章列表面板*/
    $.get(NEWEST_ARTICLE_PANEL_URL, function (data) {
        $('.newest-article-area').append(data);
    });
    /*加载最热文章类表面板*/
    $.get(HOTTEST_ARTICLE_PANEL_URL, function (data) {
        $('.hottest-article-area').append(data);
    });
    /*加载文章分类标签面板*/
    $.get(CLASSIFY_LABELS_PANEL_URL, function (data) {
        $('.classify-labels-area').append(data);
    });
    /*加载文章标签面版*/
    $.get(ARTICLE_LABELS_PANEL_URL, function (data) {
        $('.article-labels-area').append(data);
    });
    /*加载底部备案信息*/
    $('.common-footer').load(BOTTOM_PANEL_URL);
});
