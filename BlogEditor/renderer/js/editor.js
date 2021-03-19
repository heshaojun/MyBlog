$(function () {
    $("#west-panel").panel({
        title: "目录",
        onOpen: function () {
            let html = loadArticleTree([{
                "classify": "类型1",
                "list": [
                    {"title": "文章1", "id": "12234354"}
                ]
            }, {}, {}], "accordion_")
            if (html) {
                $("#west-panel").html(html);
            }
            $("#west-panel.easyui-tree").tree({});
        },
        tools: [
            {
                iconCls: 'icon-reload',
                handler: function (event) {
                    alert("reload");
                    $("#west-panel").panel('reload')
                }
            }
        ]
    });
    $("#west-panel").panel('refresh');
});

function loadArticleTree(selector) {
    selector
};

function openArticle(articleId) {

}