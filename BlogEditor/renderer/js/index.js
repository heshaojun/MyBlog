const {ipcRenderer} = require('electron');
const {fs} = require('fs');
const {exec} = require('child_process');
const {path} = require('path');

$(function () {
    loadConfig().then((v) => {
        alert(v);
    })
});

async function loadConfig() {
    let result = ipcRenderer.sendSync("fetch-config-data", "查询配置文件");
    console.log(result);
    return result
}