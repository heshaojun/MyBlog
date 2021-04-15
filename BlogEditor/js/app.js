const {app, Menu, ipcMain, dialog} = require('electron')
const isMac = process.platform === "darwin";
const isWind = process.platform === "win32";
const path = require('path');
const fs = require('fs');
const childProcess = require('child_process');

/*构建自定义菜单*/
class SMenu {
    static InitTemplate(mainWindow) {
        return Menu.buildFromTemplate([
            {
                label: app.name,
                submenu: [
                    {label: "关于", role: 'about'},
                    {type: 'separator'},
                    {label: "退出", role: 'quit'},
                    {label: "复制", accelerator: "CmdOrCtrl+C", selector: "copy:"},
                    {label: "粘贴", accelerator: "CmdOrCtrl+V", selector: "paste:"},
                    {label: "全选", role: 'selectAll'}
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
                    }
                ]
            }
        ]);
    }
}

/*监听-配置文件数据获取*/
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
            if (dataStr && dataStr != "") {
                event.returnValue = eval('(' + dataStr + ')');
            }
        }
    });
});

ipcMain.handle("select-folder", function (event, args) {
    let result = dialog.showOpenDialogSync({
        title: "选择文件夹",
        properties: ["openDirectory"]
    });
    return result[0];
});
ipcMain.handle("save-config-data", function (event, args) {
    let configPath;
    if (isWind) {
        configPath = process.cwd() + "\\config.json"
    } else {
        configPath = process.cwd() + "/config.json"
    }
    fs.writeFileSync(configPath, args);
    return "ok";
});
ipcMain.on("generate-id", function (event, args) {
    let config = readConfigSync();
    let path = config['dataPath'] + "/" + String(config['articleGit']).split("/")[4].split(".git")[0];
    let dirs = fs.readdirSync(path);
    let id = 1000000;
    let ids = [];

    dirs.forEach(function (name, index) {
        if (name.endsWith(".json")) {
            id++;
            ids.push(name.replace(".json", ""));
        }
    })
    while (true) {
        if (ids.indexOf(id.toString()) == -1) {
            return id.toString;
        } else {
            id++;
        }
    }
    event.returnValue = id.toString();
});

function readConfigSync() {
    let configPath
    if (isWind) {
        configPath = process.cwd() + "\\config.json"
    } else {
        configPath = process.cwd() + "/config.json"
    }
    let buffer = fs.readFileSync(configPath);
    let dataStr = buffer.toString();
    if (dataStr && dataStr != "") {
        return eval('(' + dataStr + ')');
    } else {
        return null
    }
}

ipcMain.on('ready-data', function (event, args) {
    event.returnValue = readyData(args);
});

function readyData(config) {
    let root = config['dataPath'];
    if (!fs.existsSync(root)) {
        console.log("创建文件夹");
        fs.mkdirSync(root);
    }
    console.log("git操作!");
    let articleRelPath = String(config['articleGit']).split("/")[4].split(".git")[0];
    let articlePath = root + "/" + articleRelPath;
    if (!fs.existsSync(articlePath)) {
        console.log("克隆项目");
        childProcess.exec("git clone " + config['articleGit'], {cwd: root}, function (error, stdout, stderr) {
            if (error) {
                console.log("克隆资源失败!");
                console.log(error.toString());
            } else {
                return true;
            }
        });
    } else {
        childProcess.exec("git status", {cwd: articlePath}, async function (error, stdout, stderr) {
            if (error) {
                console.log("git 状态异常");
                console.log(error.toString());
                childProcess.exec("rm -rf " + articleRelPath, {cwd: root}, function (error, stdout, stderr) {
                    if (error) {
                        console.log("删除文件失败！");
                        console.log(error.toString());
                        return false;
                    } else {
                        return readyData(config);
                    }
                });
            } else {
                return true;
            }
        });
    }
    console.log(articlePath);
}

ipcMain.on("commit-and-push", function (event, args) {
    let config = readConfigSync();
    let path = config['dataPath'] + "/" + String(config['articleGit']).split("/")[4].split(".git")[0];
    childProcess.exec("git commit -m '" + args + "'", {cwd: path}, function (error, stdout, stderr) {
        if (error) {
            event.returnValue = {status: false, comment: "提交数据失败！", msg: error.toString()};
            console.log("提交代码失败！")
        } else {
            childProcess.exec("git push", {cwd: path}, function (error, stdout, stderr) {
                if (error) {
                    event.returnValue = {status: false, comment: "上传数据失败！", msg: error.toString()}
                } else {
                    event.returnValue = {status: true}
                }
            });
        }
    });
});

ipcMain.on("fetch-article-menu", function (event, args) {
    let config = readConfigSync();
    let path = config['dataPath'] + "/" + String(config['articleGit']).split("/")[4].split(".git")[0];
    let dirs = fs.readdirSync(path);
    let menuData = {};
    dirs.forEach(function (file, index) {
        if (file.endsWith(".json")) {
            let info = fs.statSync(path + "/" + file);
            if (info.isFile()) {
                let articleId = file.split(".json")[0];
                let articleInfo = readArticleData(articleId);
                if (articleInfo) {
                    if (!menuData[articleInfo['articleLabel']]) {
                        menuData[articleInfo['articleLabel']] = [];
                    }
                    menuData[articleInfo['articleLabel']].push({text: articleInfo['title'], id: articleInfo['id']});
                }
            }
        }
    });
    event.returnValue = menuData;
});
ipcMain.on("read-article", function (event, args) {
    console.log("读取文章：" + args);
    let articleStr;
    try {
        articleStr = readArticleData(args);
    } catch (e) {
        articleStr = null;
    }
    event.returnValue = articleStr;
});

function readArticleData(articleId) {
    let config = readConfigSync();
    let path = config['dataPath'] + "/" + String(config['articleGit']).split("/")[4].split(".git")[0];
    let buffer = fs.readFileSync(path + "/" + articleId + ".json");
    let articleStr = buffer.toString();
    if (articleStr && articleStr != "") {
        console.log("读取到文章信息：" + articleStr);
        return eval('(' + articleStr + ')');
    } else {
        return null
    }
}

ipcMain.on("save-article", function (event, args) {
    saveArticle(args);
    event.returnValue = "ok";
});

function saveArticle(articleData) {
    let config = readConfigSync();
    console.log(articleData);
    let path = config['dataPath'] + "/" + String(config['articleGit']).split("/")[4].split(".git")[0];
    fs.writeFileSync(path + "/" + articleData['id'] + ".json", JSON.stringify(articleData));
    console.log("写入文章成功")
}

module.exports = {
    SMenu: SMenu
};
