<template>
    <ion-page>
        <ion-content>
            <h1 class="ion-margin">Evacuation Assistance Application</h1>
            <LoginForm :sign-in-user="signIn"/>
            <ion-toast position="bottom" color="danger" @didDismiss="setOpen(false)" :is-open="isOpen"
                       message="Invalid Email or Password"
                       :duration="2000"></ion-toast>
        </ion-content>
    </ion-page>
</template>

<script lang="ts">
export default {
    name: "LoginPage"
}
</script>

<script setup lang="ts">
import {IonPage, IonToast, IonContent} from '@ionic/vue';
import {ref} from 'vue';
import LoginForm from '@/components/LoginForm.vue';
import {StorageService} from '@/services/storage.service'
import {signInUser, User} from '@/data/user';
import {useIonRouter} from '@ionic/vue';

// boolean value for the toast component in vuejs
const isOpen = ref(false);
// function to change the boolean value of isOpen
const setOpen = (state: boolean) => {
    isOpen.value = state
};
// create a StorageService object
const store = new StorageService();
// used to navigating to different routes
const ionRouter = useIonRouter();
const alreadySignedIn = async () => {
    // locally stored user data
    const userData = await store.read('user');
    if (userData.value !== null) {
        console.log("Successfully used stored token to redirect to home page");
        ionRouter.push("/tabs/UsersManager/");
    }
}

alreadySignedIn();
/* function to login into the home page of your account,
   checks if you have an accesstoken and if that's
   the case create a local storage object
   that will be used to store your "login" information.
 */
const signIn = async (user: User) => {
    // POST request to our backend API
    const response = await signInUser(user);
    console.log(response.data);
    // if we exist in the backend DB, create a object storing our information
    if (response.data.accessToken) {
        console.log("Retreived accesstoken and storing it response data...");
        await store.create('user', response.data);
        // rerouting to homepage
        ionRouter.push("/tabs/UsersManager/");
    } else if (response.status === 400 || response.status === 401) {
        // if you get a bad request, make sure the toast component can notify someone again.
        setOpen(true);
    }
}

</script>


<style scoped>

</style>