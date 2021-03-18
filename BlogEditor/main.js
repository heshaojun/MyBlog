const {app, BrowserWindow, Menu, ipcMain} = require('electron');
const {SMenu} = require('./js/app');
let mainWindow
app.on("ready", function () {
    mainWindow = new BrowserWindow({
        width: 800,
        height: 600,
        webPreferences: {
            nodeIntegration: true,
            contextIsolation: false,
        }
    });
    mainWindow.loadFile("./renderer/index.html");
    mainWindow.openDevTools();
    Menu.setApplicationMenu(SMenu.InitTemplate(mainWindow));
    ipcMain.on("load-page", function (event, args) {
        mainWindow.loadFile(args);
    });
});
app.on("window-all-closed", function () {
    if (process.platform !== 'darwin') app.quit()
});
