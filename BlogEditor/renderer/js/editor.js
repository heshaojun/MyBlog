$(function () {
    $("#west-panel").panel({
        title: "目录",
        onOpen: function () {
            loadArticleTree(".easyui-tree");
        },
        tools: [
            {
                iconCls: 'icon-reload',
                handler: function (event) {
                    loadArticleTree(".easyui-tree");
                }
            }
        ]
    });
});

function loadArticleTree(selector) {
    $(selector).tree({
        onDblClick: function (node) {
            if (node['id']) {
                openArticle(node['id']);
            }
        },
        data: [{
            text: "分类1",
            state: 'closed',
            children: [
                {
                    id: "123243",
                    text: "文章222"
                }
            ]
        }, {
            text: "分类2"
        }]
    })
};

function openArticle(articleId) {
    console.log("打开文章")
}