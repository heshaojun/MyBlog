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
                    addNewEditor()
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
        onBeforeClose: function (title, index) {
            return closeSafely(title, index);
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

//添加新编辑页面
function addNewEditor() {
    let id = ipcRenderer.sendSync("generate-id", "");
    console.log("new id ==== " + id);
    openTab(id, "新建文章");
}

//打开新界面
function openTab(id, title) {
    if (title && id) {
        if ($('#tables-area').tabs('tabs')) {
            for (let index in $('#tables-area').tabs('tabs')) {
                let item = $($('#tables-area').tabs('tabs')[index]).panel('options');
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

//文章内容变动触发渲染
function articleContextChanged(selector) {
    let articleId = $(selector).attr("article_id");
    let context = $(selector).val();
    articleRender(articleId, context);
}


//保存修改
function saveChange() {
    let panel = $('#tables-area').tabs("getSelected");
    let articleId = $(panel).attr("id");
    let articleDataInPage = getDataFromTable(panel);
    let articleInDisc = ipcRenderer.sendSync("read-article", articleId);
    console.log("---------------------")
    console.log(articleInDisc);
    console.log(articleDataInPage);
    if (!ifArticleEqual(articleDataInPage, articleInDisc)) {
        if (articleValid(articleDataInPage)) {
            ipcRenderer.sendSync("save-article", articleDataInPage);
        }
    }
}

function articleValid(articleData) {
    if (!articleData["title"]) {
        alert("文章标题不能为空");
        return false;
    }
    if (!articleData["summery"]) {
        alert("文章摘要不能为空");
        return false;
    }
    if (!articleData["type"]) {
        alert("文章类型不能为空");
        return false;
    }
    if (!articleData["time"]) {
        alert("文章创建时间不能为空");
        return false;
    }
    if (!articleData["context"]) {
        alert("文章内容不能为空");
        return false;
    }
    if (!articleData["articleLabel"]) {
        alert("文章标签不能为空");
        return false;
    }
    if (!articleData["classifyLabels"]) {
        alert("分类标签不能为空");
        return false;
    }
    return true;
}

function ifArticleEqual(article1, article2) {
    if (article1 && article2) {
        if (article1["title"] != article2["title"]) return false;
        if (article1["summery"] != article2["summery"]) return false;
        if (article1["type"] != article2["type"]) return false;
        if (article1["time"] != article2["time"]) return false;
        if (article1["id"] != article2["id"]) return false;
        if (article1["context"] != article2["context"]) return false;
        if (article1["articleLabel"] != article2["articleLabel"]) return false;
        if (article1["classifyLabels"].toString() != article2["classifyLabels"].toString()) return false;
        return true
    } else {
        return false
    }
}

//获取tab页中文章内容
function getDataFromTable(panel) {
    let articleId = $(panel).attr('id');
    let articleTitle = $(panel).find(".article-title[article_id='" + articleId + "']");
    let articleType = $(panel).find(".article-type[article_id='" + articleId + "']");
    let articleCreateTime = $(panel).find(".article-create-time[article_id='" + articleId + "']");
    let articleLabel = $(panel).find(".article-label[article_id='" + articleId + "']");
    let articleClassify = $(panel).find(".article-classify[article_id='" + articleId + "'] .article-classify-text");
    let articleSummary = $(panel).find(".article-summary[article_id='" + articleId + "']");
    let articleDetail = $(panel).find(".article-detail[article_id='" + articleId + "']");
    let classifyLabels = [];
    $($(panel).find(".article-classify[article_id='" + articleId + "'] .article-classify-text")).each(function () {
        classifyLabels.push($(this).text());
    });

    let articleData = {
        "title": $(articleTitle).val(),
        "summery": $(articleSummary).val(),
        "type": $(articleType).val(),
        "time": $(articleCreateTime).val(),
        "id": articleId,
        "context": $(articleDetail).val(),
        "articleLabel": $(articleLabel).val(),
        "classifyLabels": classifyLabels
    }
    return articleData;

}

function closeSafely(title, index) {
    let panel = $('#tables-area').tabs('getTab', index);
    if (panel) {
        let articleData = getDataFromTable(panel);
        let articleId = $(panel).attr("id");
        let articleInDisc = ipcRenderer.sendSync("read-article", articleId);
        if (ifArticleEqual(articleData, articleInDisc)) {
            return true
        } else {
            let ifSave = window.confirm("是否保存文章《" + articleData['title'] + "》数据？");
            if (ifSave) {
                if (articleValid(articleData)) {
                    ipcRenderer.sendSync("save-article", articleData);
                }
            }
            return true;
        }
    } else {
        return true;
    }
    $('#tables-area').tabs('select', index);

    return false;
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