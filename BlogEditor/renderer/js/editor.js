const {ipcRenderer} = require('electron');
showdown.setFlavor('github');
let converter = new showdown.Converter({noHeaderId: true});
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
        },
        onLoad: function (panel) {
            loadData(panel)
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
            href: './tab_article.html'
        })
    }
}

function loadData(panel) {
    let articleId = $(panel).attr("id");
    let articleData = ipcRenderer.sendSync("read-article", articleId);
    console.log(articleData);
    let articleTitle = $(panel).find(".article-title");
    $(articleTitle).attr("article_id", articleId);
    let articleType = $(panel).find(".article-type");
    $(articleType).attr("article_id", articleId);
    let articleCreateTime = $(panel).find(".article-create-time");
    $(articleCreateTime).attr("article_id", articleId);
    let articleLabel = $(panel).find(".article-label");
    $(articleLabel).attr("article_id", articleId);
    let articleClassify = $(panel).find(".article-classify");
    $(articleClassify).attr("article_id", articleId);
    let articleSummary = $(panel).find(".article-summary");
    $(articleSummary).attr("article_id", articleId);
    let articleDetail = $(panel).find(".article-detail");
    $(articleDetail).attr("article_id", articleId);
    let articleShow = $(panel).find(".article-show-area");
    $(articleShow).attr("article_id", articleId);
    if (articleData) {
        //文章存在
        $(articleTitle).val(articleData['title']);
        $(articleType).val(articleData['type']);
        $(articleLabel).val(articleData['articleLabel']);
        $(articleCreateTime).val(articleData['time']);
        let classifies = articleData['classifyLabels'];
        for (let i in classifies) {
            $(articleClassify).find("span.label-area").append("<span class=\" label-item\"\n" +
                "                         style=\"background-color: #7abaff;border-radius: 1px;padding-left:2px;margin-right: 5px\">\n" +
                "                       <span class=\"article-classify-text\">" + classifies[i] + "</span>\n" +
                "                       <button\n" +
                "                               style=\"border: none;background-color: transparent;padding: 0;color: #ba8b00\"\n" +
                "                               onclick=\"removeLabel(this)\">x</button>\n" +
                "                     </span>");
        }
        ;
        $(articleSummary).val(articleData['summery']);
        $(articleDetail).val(articleData['context']);
        articleRender(articleId, articleData['context']);
    } else {
        //文章不存在，新建
        let myDate = new Date;
        let year = myDate.getFullYear(); //获取当前年
        let mon = myDate.getMonth() + 1; //获取当前月
        let date = myDate.getDate(); //获取当前日
        let h = myDate.getHours();//获取当前小时数(0-23)
        let m = myDate.getMinutes();//获取当前分钟数(0-59)
        let s = myDate.getSeconds();//获取当前秒
        $(articleCreateTime).val(year + '-' + mon + '-' + date + ' ' + h + ':' + m + ':' + s);
    }
}

function articleRender(articleId, context) {
    let html = converter.makeHtml(context);
    $(".article-show-area[article_id='" + articleId + "']").html(html);
}

function articleContextChanged(selector) {
    let articleId = $(selector).attr("article_id");
    let context = $(selector).val();
    articleRender(articleId, context);
}

//判断文章内容是否更改
function ifArticleChanged(title, id) {
    return true
}

//保存修改
function saveChange(selector, id) {
    let articleId = id;
    if (selector) {
        articleId = $(selector).attr("article_id");
    }
    let panel;
    let panels = $('#tables-area').tabs('tabs');
    for (let p in panels) {
        if ($(p).attr("id") == articleId) {
            panel = p;
            break;
        }
    }
}

function getDataFromTable(panel) {
    let articleId = $(panel).attr('id');

}

function closeSafely(title, id) {
    if (ifArticleChanged(title, id)) {

    }
    return true
}

function removeLabel(selector) {
    $($(selector).parent(".label-item")).remove();
}

function addClassify(selector) {
    let target = $(selector).parent(".article-classify").find(".label-area");
    target.append("<input type='text' onblur='replaceLabel(this)' autofocus>")
}

function replaceLabel(selector) {
    let text = $(selector).val();
    if (text) {
        let lists = $(selector).parents(".label-area").find(".article-classify-text");
        for (let i in lists) {
            if ($(lists[i]).text() == text) {
                $(selector).remove();
                return;
            }
        }
        $(selector).replaceWith(" <span class=\" label-item\"\n" +
            "                         style=\"background-color: #7abaff;border-radius: 1px;padding-left:2px;margin-right: 5px\">\n" +
            "                       <span class=\"article-classify-text\">" + text + "</span>\n" +
            "                       <button\n" +
            "                               style=\"border: none;background-color: transparent;padding: 0;color: #ba8b00\"\n" +
            "                               onclick=\"removeLabel(this)\">x</button>\n" +
            "                     </span>")
    }
}