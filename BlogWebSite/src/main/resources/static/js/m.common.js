/*导航栏面板地址*/
const NAVIGATOR_PANEL_URL = "../html/navigator.html";
/*文章列表面板地址*/
const ARTICLE_LIST_PANEL_URL = "../html/article_list_panel.html";
const ARTICLE_LIST_DATA_URL = "/rest/articleList";
/*网站统计信息面板地址*/
const SITE_INFO_PANEL_URL = "../html/site_info_panel.html";
const SITE_INFO_DATA_URL = "/rest/siteInfo";
/*最新文章 列表面板地址*/
const NEWEST_ARTICLE_PANEL_URL = "../html/newest_article_panel.html";
const NEWEST_ARTICLE_DATA_URL = "/rest/newestArticles";
/*最热文章信息面板地址*/
const HOTTEST_ARTICLE_PANEL_URL = "../html/hottest_article_panel.html";
const HOTTEST_ARTICLE_DATA_URL = "/rest/hottestArticles";
/*文章分类标签面板地址*/
const CLASSIFY_LABELS_PANEL_URL = "../html/classify_labels_panel.html";
const CLASSIFY_LABELS_DATA_URL = '/rest/classifyLabels';
/*文章标签面板地址*/
const ARTICLE_LABELS_PANEL_URL = "../html/article_labels_panel.html";
const ARTICLE_LABELS_DATA_URL = "/rest/articleLabels";
/*底部备案信息面板页*/
const BOTTOM_PANEL_URL = '../html/bottom.html';
/*文章详情面板路径*/
const ARTICLE_DETAIL_PANEL_URL = "../html/article_detail_panel.html";
const ARTICLE_DETAIL_DATA_URL = "/rest/articleDetail";
const ARTICLE_COMMENTS_DATA_URL = "/rest/comments";
/*留言板面板路径*/
const MSG_BOARD_PANEL_URL = '../html/msg_board_panel.html';
/*关于我面板路径*/
const ABOUT_ME_PANEL_URL = '../html/aboutme_panel.html';
/**/
const MODAL_PANELS_URL = '../html/modal_panels.html';

let pageParams = {};
let visitor_login_info = null;
getUserInfo();
let timeCount = 0;

$(function () {
    let params = window.location.search.substring(1).split("&");
    for (let index in params) {
        let param = params[index];
        if (param.includes("=")) {
            let kv = param.split("=", 2);
            pageParams[kv[0]] = kv[1];
        }
    }
});

function getUserInfo() {
    $.post("/rest/getUserInfo", function (result) {

        if (result['code'] == 200) {
            let data = result['data'];
            visitor_login_info = {username: data['userName'], loginType: data["loginType"]};
        }
    });
};

function popUpLogin(selector) {
    if (!visitor_login_info) {
        $("#login_modal").modal('toggle')
    } else {
        $(selector).removeAttr("readonly");
        $(".visitor-login-info").text(visitor_login_info['name'])
    }
}

function userLogin() {
    let email = $("#login-email").val();
    let userName = $("#login-name").val();
    let code = $("#login-code").val();
    if (!email) {
        alert("邮箱为空");
        return;
    }
    if (!userName) {
        alert("用户名称为空");
        return;
    }
    if (!code) {
        alert("验证码为空");
        return;
    }
    if (email.includes("<") || email.includes(">") || email.includes("$")) {
        alert("请输入正确的邮箱");
        return;
    }
    if (userName.includes("<") || userName.includes(">") || userName.includes("$")) {
        alert("请输入正确的名称");
        return;
    }
    if (code.length != 6 || code.includes(">") || code.includes("$")) {
        alert("请输入正确的验证码");
        return;
    }
    $.post("/rest/userLogin", {userName: userName, email: email, code: code}, function (result) {
        if (result['code'] == 200) {
            let data = result['data'];
            visitor_login_info = {username: data['userName'], loginType: data["loginType"]};
            $(".visitor-login-info").text("" + userName);
            alert("欢迎：" + userName + "登录");
            $("#login_modal").modal('hide')
        } else {
            alert(result['msg'])
        }
    })
}

function fetchCode() {
    let email = $("#login-email").val();
    if (email) {
        if (email.length > 100) {
            alert("输入邮箱地址异常");
            return;
        }
        if (email.includes("<") || email.includes(">")) {
            alert("输入邮箱地址异常");
            return;
        }
    } else {
        alert("请输入邮箱");
        return
    }
    $.post("/rest/fetchCode", {email: email}, function (result) {
        if (result['code'] == 200) {
            alert("已经向" + email + "发送验证码，请查收！");
            $("#login-get-email").attr("disabled", true);
            $("#login-code").focus();
            timeCount = 60;
            emailSendTimeCount();
        } else {
            alert(result['msg']);
        }
    });
}

function emailSendTimeCount() {
    timeCount--;
    if (timeCount <= 0) {
        $("#login-get-email").removeAttr("disabled");
        $("#login-get-email").text("重新发送");
        timeCount = 0;
        return;
    }
    $("#login-get-email").text("" + timeCount + "s");
    setTimeout(emailSendTimeCount, 1000)
}