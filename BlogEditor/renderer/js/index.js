const {ipcRenderer} = require('electron');
const {fs} = require('fs');
const {exec} = require('child_process');
const {path} = require('path');

$(function () {
    loadConfig().then((config) => {
        if (config) {
            ipcRenderer.send("ready-data", config);
            console.log("跟路径存在，显示入口");
            $('#context-area').html('    <div class="center d-table w-100 text-center" id="nav-list" hidden>\n' +
                '        <div class="p-3"><a href="javascript:ipcRenderer.send(\'load-page\', \'./renderer/setting.html\')">修改配置</a></div>\n' +
                '        <div class="p-3"><a href="javascript:ipcRenderer.send(\'load-page\', \'./renderer/editor.html\')">编写文章</a></div>\n' +
                '    </div>');
        } else {
            alert("没有配置资源，请进行资源相关的配置");
            ipcRenderer.send('load-page', './renderer/setting.html')
        }
    })
});

async function loadConfig() {
    let result = ipcRenderer.sendSync("fetch-config-data", "查询配置文件");
    console.log(result);
    if (result.dataPath && result.resourceGit && result.articleGit) {
        console.log("数据完整");
    } else {
        console.log("数据不完整");
        return null;
    }
    return result
}

function ternTo() {

}