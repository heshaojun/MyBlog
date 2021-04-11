const {ipcRenderer} = require('electron');
$(function () {
    //显示目录
    $("#west-panel").panel({
        title: "目录",
        onOpen: function () {
            loadArticleTree(".easyui-tree");
        },
        tools: [
            {
                iconCls: 'icon-add',
                handler: function () {
                    alert("添加");
                    showWelcome()
                }
            },
            {
                iconCls: 'icon-reload',
                handler: function (event) {
                    loadArticleTree(".easyui-tree");
                }
            }
        ]
    });
    //初始化tab区域
    $('#tables-area').tabs({
        border: false,
        fit: true,
        onBeforeClose: function (title, id) {
            return closeSafely(title, id);
        }
    });
});

//文章目录树展示函数
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
            console.log(node);
            if (node['id']) {
                openTab(node['id'], node['text']);
            }
        },
        data: list
    })
};

//展示欢迎页
function showWelcome() {
    $('#tables-area').tabs('add', {
        title: "欢迎页面", content: "内容", href: "./tab_welcome.html", editable: false, closable: false
    });
}

//打开新界面
function openTab(id, title) {
    if (title && id) {
        if ($('#tables-area').tabs('tabs')) {
            for (let index in $('#tables-area').tabs('tabs')) {
                let item = $($('#tables-area').tabs('tabs')[index]).panel('options');
                console.log(item);
                //tab页存在，则选择tab页
                if (item['id'] && item['title']) {
                    if (item['id'] == id && item['title'] == title) {
                        $('#tables-area').tabs('select', item['index']);
                        return;
                    }
                }
            }
        }
        //tab页不存在，添加新的tab页
        $('#tables-area').tabs('add', {
            id: id,
            title: title,
            closable: true,
            select: true,
            href: './tab_article.html?id=' + id
        })
    }
}

//判断文章内容是否更改
function ifArticleChanged(title, id) {
    return true
}

//保存修改
function saveChange(title, id) {
    return false
}

function closeSafely(title, id) {
    if (ifArticleChanged(title, id)) {

    }
    return true
}