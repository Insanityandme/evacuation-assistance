import {LocalNotifications} from "@capacitor/local-notifications";
import router from "@/router";
import {StorageService} from "@/services/storage.service";
import {changeActiveTrue} from "@/data/changeActive";
import {CapacitorHttp} from "@capacitor/core";
import {resourceUrl} from "@/data/resourceUrl";

const url = `${resourceUrl}/api/alarm/`;

// create a StorageService object
const store = new StorageService();

/**
 * Add listeners to constantly listen for new notifications, as well as sending the user to the correct page/component
 * depending on when pressed on notification directly or either of the buttons in the notification.
 */
const addListeners = async () => {
    const userData = await store.read('user');
    // eslint-disable-next-line
    const userDataParsed = JSON.parse(userData.value!);
    const role = userDataParsed.roles[0];

    await LocalNotifications.addListener('localNotificationReceived', notification => {
        console.log('Local notification received: ', notification);
        if (role === 'ROLE_DEPUTYLEADER') {
            router.replace('/tabs/home/deputyleader');
        } else if (role === 'ROLE_EVACLEADER') {
            router.replace('/tabs/home/evacleader/note/reroute');
        }
    });

    await LocalNotifications.addListener('localNotificationActionPerformed', notification => {
        console.log('Local notification action performed', notification.actionId, notification.inputValue);
        const actionId = notification.actionId;
        const actionTypeId = notification.notification.actionTypeId;

        if (actionId == "tap" && role === 'ROLE_DEPUTYLEADER') {
            router.replace('/tabs/home/deputyleader');
            console.log(actionTypeId);
        } else if (actionId == "tap" && role === 'ROLE_EVACLEADER') {
            router.replace('/tabs/home/evacleader/note/reroute');
            console.log(actionTypeId);
        }
        if (actionId == "yes" && role === 'ROLE_EVACLEADER') {
            router.replace('/tabs/home/evacleader/note/yes');
            changeActiveTrue(userDataParsed.username);
        }
        if (actionId == "no" && role === 'ROLE_EVACLEADER') {
            router.replace('/tabs/home/evacleader/note/no');
        }
    });
}
/**
 * Checks whether a user has accepted the Notification Permission, otherwise requests permission.
 * If permission is granted, then a custom local actionType is registered that allows the notification
 * to have two custom buttons yes and no to answer the availability question.
 */
const checkNotificationsPermission = async () => {
    let permStatus = await LocalNotifications.checkPermissions();

    if (permStatus.display === 'prompt') {
        permStatus = await LocalNotifications.requestPermissions();
    }

    if (permStatus.display === "granted") {
        console.log("User granted permissions!")
    }
    if (permStatus.display === 'denied') {
        throw new Error('User denied permissions!');
    }
    await LocalNotifications.registerActionTypes({
        types: [
            {
                id: 'areYouAvailable',
                actions: [
                    {
                        id: 'yes',
                        title: 'Yes, I\'m Available!',
                        foreground: true
                    },
                    {
                        id: 'no',
                        title: 'No, I\'m Not Available!',
                        foreground: true,
                        destructive: true
                    }
                ]
            }
        ]
    })
}
// This custom local notification is triggered when the evacuation leader presses the 'Notify Me Now,
// with Buttons' button in the settings page, where a notification is displayed with two buttons, conforming
// to the ActionType 'areYouAvailable' above.
export const scheduleAdvanced = async () => {
    await LocalNotifications.schedule({
        notifications: [
            {
                title: 'Evacuation in Progress',
                body: 'Are you available?',
                id: 2,
                sound: 'custom.mp3',
                actionTypeId: 'areYouAvailable',
                /*extra: {
                    data: 'Pass data to your handler'
                },*/
            }
        ]
    })
}
// Method to be called when Evacuation leader is logged in to check for notification permission and setup listeners.
export const enableLocalNotifications = async () => {
    await addListeners()
    await checkNotificationsPermission()
}
//Alarm interface to be used to check for alarm status from the server.
export interface Alarm {
    status: boolean
}
//Checks for alarm status by sending get request to server.
export const getAlarmStatus = async () => {
    const options = {
        url: `${url}status`,
        headers: {"Content-Type": "application/json"},
    }

    return CapacitorHttp.get(options);
}