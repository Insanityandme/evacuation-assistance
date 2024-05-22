<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <ion-title>Evacuation Assistance</ion-title>
            </ion-toolbar>
        </ion-header>
        <ion-content>
            <LoginForm :sign-in-user="signIn"/>
            <ion-toast position="bottom" color="danger" @didDismiss="setOpenServerConnection(false)"
                       :is-open="isOpenServerConnection"
                       message="No connection to server"
                       :duration="2000"></ion-toast>
            <ion-toast position="bottom" color="danger" @didDismiss="setOpenEmailOrPassword(false)"
                       :is-open="isOpenEmailOrPassword"
                       message="Invalid Email or Password"
                       :duration="2000"></ion-toast>
        </ion-content>
    </ion-page>
</template>

<script setup lang="ts">
import {IonPage, IonToast, IonContent, IonToolbar, IonHeader, IonTitle} from '@ionic/vue';
import {useIonRouter} from '@ionic/vue';
import {ref} from 'vue';
import LoginForm from '@/components/LoginForm.vue';
import {StorageService} from '@/services/storage.service'
import {signInUser, User} from '@/data/user';
import {enablePushNotifications} from "@/data/notifications";
import {enableLocalNotifications} from "@/data/localNotification";

// boolean value for the toast component in vuejs
const isOpenEmailOrPassword = ref(false);
const isOpenServerConnection = ref(false);

// function to change the boolean value of isOpen
const setOpenEmailOrPassword = (state: boolean) => {
    isOpenEmailOrPassword.value = state
};

// same as above but for the server connection
const setOpenServerConnection = (state: boolean) => {
    isOpenServerConnection.value = state
};

// create a StorageService object
const store = new StorageService();

// used to navigating to different routes
const ionRouter = useIonRouter();

// to check if a user is already signed in
const alreadySignedIn = async () => {
    // locally stored user data
    const userData = await store.read('user');
    // eslint-disable-next-line
    const userDataParsed = JSON.parse(userData.value!);

    if (userData.value !== null) {
        console.log("Successfully used stored token to redirect to home page");

        //Navigate to the appropriate home page based on the user's role
        if (userDataParsed.roles[0] === 'ROLE_DEPUTYLEADER') {
            ionRouter.replace("/tabs/home/deputyleader/");
            await enableLocalNotifications();
        } else if (userDataParsed.roles[0] === 'ROLE_EVACLEADER') {
            ionRouter.replace("/tabs/home/evacleader/");
            await enableLocalNotifications();
        } else if (userDataParsed.roles[0] === 'ROLE_USER') {
            ionRouter.replace("/tabs/home/user/");
        } else {
            console.log('Unknown role');
        }
    } else {
        console.log("User is not signed in yet.");
    }
}

alreadySignedIn();

/**
 * function to login to the home page of your account,
 * checks if you have an accesstoken and if that's
 * the case create a local storage object
 * that will be used to store your login information.
 * @param user the one who logged in
 */
const signIn = async (user: User) => {
    // POST request to our backend API
    const response = await signInUser(user);

    // if we exist in the backend DB, create an object storing our information
    if (response.data.accessToken) {
        console.log("Retreived accesstoken and storing it response data...");

        // very important data is turned into a JSON string and then able to JSON.parse it later on
        await store.create('user', JSON.stringify(response.data));
        console.log(response.data.roles);

        // navigate to the appropriate home page based on the user's role
        if (response.data.roles.includes('ROLE_DEPUTYLEADER')) {
            ionRouter.replace("/tabs/home/deputyleader/");
            await enablePushNotifications();
            await enableLocalNotifications();
        } else if (response.data.roles.includes('ROLE_EVACLEADER')) {
            ionRouter.replace("/tabs/home/evacleader/");
            // To make sure if you are logged in as an evac leader
            // you can get push notifications
            await enablePushNotifications();
            await enableLocalNotifications();
            console.log("Successfully listening to push notifications")
        } else if (response.data.roles.includes('ROLE_USER')) {
            ionRouter.replace("/tabs/home/user/");
        } else {
            console.log('Unknown role:', response.data.roles);
        }
    } else if (response.status == 400 || response.status == 401) {
        // if you get a bad request, make sure the toast component can notify someone again.
        setOpenEmailOrPassword(true);
    }
}
</script>

<style>
</style>
