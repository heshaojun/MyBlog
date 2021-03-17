const {app, BrowserWindow, Menu} = require('electron')


function createWindow() {
    const mainWindow = new BrowserWindow({
        width: 1200,
        height: 900
    });
    mainWindow.loadFile("./resource/index.html")
}

const isMac = process.platform === "darwin"

function initMenu() {
    const template = [
        {
            label: app.name,
            submenu: (isMac ? [
                {role: 'about'},
                {type: 'separator'},
                {role: 'services'},
                {type: 'separator'},
                {role: 'hide'},
                {role: 'hideothers'},
                {role: 'unhide'},
                {type: 'separator'},
                {role: 'quit'}
            ] : [])
        }, {
            label: '文件',
            submenu: [
                (isMac ? {label: "关闭", role: "close"} : {label: "关闭", role: "quit"})
            ]
        },
    ];
    const menu = Menu.buildFromTemplate(template);
    Menu.setApplicationMenu(menu);
}

app.on("ready", function () {
    initMenu();
    createWindow();
});
app.on("window-all-closed", function () {
    if (process.platform !== 'darwin') app.quit()
});