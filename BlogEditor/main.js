const {app, BrowserWindow, Menu, ipcMain} = require('electron')
const {SMenu} = require('./js/app')
app.on("ready", function () {
    let mainWindow = new BrowserWindow({
        width: 1200,
        height: 900,
        webPreferences: {
            nodeIntegration: true,
            contextIsolation: false,
        }
    });
    mainWindow.loadFile("./renderer/index.html");
    mainWindow.openDevTools();
    Menu.setApplicationMenu(SMenu.InitTemplate(mainWindow));
});
app.on("window-all-closed", function () {
    if (process.platform !== 'darwin') app.quit()
});
ipcMain.handle("get-install-path", function () {
    return __dirname
});

