const {app, Menu, ipcMain} = require('electron')
const isMac = process.platform === "darwin";
const isWind = process.platform === "win32";
const path = require('path');
const fs = require('fs');

/*构建自定义菜单*/
class SMenu {
    static InitTemplate(mainWindow) {
        return Menu.buildFromTemplate([
            {
                label: app.name,
                submenu: [
                    {label: "关于", role: 'about'},
                    {type: 'separator'},
                    {label: "退出", role: 'quit'}
                ]
            }, {
                label: '文件',
                submenu: [
                    (isMac ? {label: "关闭", role: "close"} : {label: "关闭", role: "quit"}),
                    {
                        label: "设置",
                        click: async () => {
                            if (mainWindow) {
                                mainWindow.loadFile('./renderer/setting.html')
                            }
                        }
                    }, {
                        label: "编写博客",
                        click: async () => {
                            if (mainWindow) {
                                mainWindow.loadFile('./renderer/editor.html')
                            }
                        }
                    }
                ]
            }
        ]);
    }
}

ipcMain.on("fetch-config-data", function (event, args) {
    let configPath
    if (isWind) {
        configPath = process.cwd() + "\\config.json"
    } else {
        configPath = process.cwd() + "/config.json"
    }
    fs.readFile(configPath, function (err, data) {
        if (err) {
            console.log("Reading configuration file err " + err.message)
            event.returnValue = {};
        } else {
            let dataStr = data.toString();
            event.returnValue = eval('(' + dataStr + ')');
        }
    });
});

module.exports = {
    SMenu: SMenu
};
