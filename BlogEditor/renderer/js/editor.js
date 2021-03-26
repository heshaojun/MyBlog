const {ipcRenderer} = require('electron');
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
    $('#tables-area').tabs({
        border: false,
        fit: true,
        tools: [
            {
                iconCls: 'icon-add',
                handler: function () {
                    alert("添加")
                }
            }
        ]
    });
});


function loadArticleTree(selector) {
    let data = ipcRenderer.sendSync("fetch-article-menu", "");
    console.log("--------------------");
    console.log(data);
    let list = [];
    for (let key in data) {
        let item = {
            text: key,
            state: "closed",
            children: data[key]
        }
        list.push(item);
    }
    console.log(list);
    $(selector).tree({
        onDblClick: function (node) {
            if (node['id']) {
                openArticle(node['id']);
            }
        },
        data: list
    })
};

function openArticle(articleId) {
    console.log("打开文章")
}

function showWelcome() {

}

function openTab() {
    console.log("打开标签页")
}
