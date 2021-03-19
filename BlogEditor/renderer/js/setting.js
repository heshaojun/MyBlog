const {ipcRenderer} = require('electron');
$(function () {
    let configData = ipcRenderer.sendSync("fetch-config-data", "查询配置文件");
    if (configData.dataPath) {
        $('#root-path-selector').val(configData.dataPath);
    }
    if (configData.resourceGit) {
        $('#resource-git-storage').val(configData.resourceGit);
    }
    if (configData.articleGit) {
        $('#article-git-storage').val(configData.articleGit);
    }
});

$('#root-path-selector').click(function (event) {
    ipcRenderer.invoke("select-folder", "").then(function (result) {
        $(event.target).val(result);
    });
});

function saveConfigData() {
    if (!$('#root-path-selector').val()) {
        $('#root-path-selector').focus();
        return false;
    }
    if (!$('#resource-git-storage').val()) {
        $('#resource-git-storage').focus();
        return false;
    }
    if (!$('#article-git-storage').val()) {
        $('#article-git-storage').focus();
        return false;
    }
    let data = {
        dataPath: $('#root-path-selector').val(),
        resourceGit: $('#resource-git-storage').val(),
        articleGit: $('#article-git-storage').val()
    };
    let dataStr = JSON.stringify(data);
    ipcRenderer.invoke("save-config-data", dataStr).then((result) => {
        if (result === "ok") {
            ipcRenderer.send('load-page', './renderer/index.html');
        }
    });
}