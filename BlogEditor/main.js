const {app, BrowserWindow, Menu, ipcMain} = require('electron');
const {SMenu} = require('./js/app');
let mainWindow;
app.on("ready", function () {
    mainWindow = new BrowserWindow({
        width: 600,
        height: 450,
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
    const isWindows = process.platform === 'win32';
    let needsFocusFix = false;
    let triggeringProgrammaticBlur = false;

    mainWindow.on('blur', (event) => {
        if(!triggeringProgrammaticBlur) {
            needsFocusFix = true;
        }
    })

    mainWindow.on('focus', (event) => {
        if(isWindows && needsFocusFix) {
            needsFocusFix = false;
            triggeringProgrammaticBlur = true;
            setTimeout(function () {
                mainWindow.blur();
                mainWindow.focus();
                setTimeout(function () {
                    triggeringProgrammaticBlur = false;
                }, 100);
            }, 100);
        }
    })
});
app.on("window-all-closed", function () {
    if (process.platform !== 'darwin') app.quit()
});
