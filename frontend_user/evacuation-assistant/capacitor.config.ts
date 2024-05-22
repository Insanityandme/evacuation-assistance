import {CapacitorConfig} from '@capacitor/cli';

const config: CapacitorConfig = {
    plugins: {
        CapacitorHttp: {
            enabled: true
        },
        PushNotifications: {
            presentationOptions: ["badge", "sound", "alert"]
        },
    },
    server: {
        "cleartext": true
    },
    appId: 'io.ionic.evacuation',
    appName: 'Evacuation Assistant',
    webDir: 'dist',
    bundledWebRuntime: false
};

export default config;
