const {ipcRenderer} = require('electron');
const {fs} = require('fs');
const {exec} = require('child_process');
const {path} = require('path');

$(function () {
    loadConfig().then((config) => {
        if (config.dataPath) {
            console.log("跟路径存在，显示入口")
        } else {
            alert("没有配置资源，请进行资源相关的配置");
            ipcRenderer.send("load-page", "./renderer/setting.html")
        }
    })
});

async function loadConfig() {
    let result = ipcRenderer.sendSync("fetch-config-data", "查询配置文件");
    console.log(result);
    return result
}