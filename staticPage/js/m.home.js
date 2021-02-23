$(function () {
    /*加载导航栏*/
    $('.common-header').load("../html/navigator.html");
    /*加载文章列表*/
    $('.content-right').load("../html/article_list_panel.html");
    /*加载统计信息*/
    $.get("../html/site_info_panel.html", function (data, status) {
        $('.site-info-area').append(data);
    });
    /*加载最新文章列表面板*/
    $.get("../html/newest_article_panel.html", function (data, status) {
        $('.newest-article-area').append(data);
    });
    /*加载最热文章类表面板*/
    $.get("../html/hottest_article_panel.html", function (data, status) {
        $('.hottest-article-area').append(data);
    });
    /*加载文章分类标签面板*/
    $.get("../html/classify_labels_panel.html", function (data, status) {
        $('.classify-labels-area').append(data);
    });
    /*加载文章标签面版*/
    $.get("../html/article_labels_panel.html", function (data, status) {
        $('.article-labels-area').append(data);
    });
    /*加载底部备案信息*/
    $('.common-footer').load('../html/bottom.html')
});
