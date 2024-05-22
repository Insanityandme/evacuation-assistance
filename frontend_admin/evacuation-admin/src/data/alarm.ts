import {CapacitorHttp} from "@capacitor/core";
//import {resourceUrl} from "@/data/resourceUrl";

//const resourceUrl = 'http://localhost:8081/';
const resourceUrl = 'https://evac.al-darraji.net/';
const notificationUrl = 'api/notification';
const alarmUrl = 'api/alarm';
/**
 * This method makes an REST API-request to send notifications to multiple devices simultaneously,
 * informing them about a simulated alarm.
 */
export const sendNotifications = async() => {
    const options = {
        url: `${resourceUrl + notificationUrl}/sendToMultipleDevices`,
        headers: {"Content-Type": "application/json"}
    }

    return CapacitorHttp.post(options);
}
/**
 * API call to activate the simulation of alarm, in order for Local Notification to work.
 */
export const activateAlarm = async() => {
    const options = {
        url: `${resourceUrl + alarmUrl}/activate`,
        headers: {"Content-Type": "application/json"}
    }

    return CapacitorHttp.post(options);
}
/**
 * API call to deactivate the simulation of alarm, in order for Local Notification to work.
 */
export const deActivateAlarm = async() => {
    const options = {
        url: `${resourceUrl + alarmUrl}/deactivate`,
        headers: {"Content-Type": "application/json"}
    }

    return CapacitorHttp.post(options);
}
