<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <ion-title>Settings</ion-title>
            </ion-toolbar>
        </ion-header>
        <ion-content :fullscreen="true">
            <div class="content-container">
                <div class="userinfo-container">
                    <ion-item>
                        <ion-label style="font-size: 19px">Logged in as: {{ userInfo.userName }} - {{
                            userInfo.role
                            }}
                        </ion-label>
                    </ion-item>
                </div>
                <ion-list>
                    <ion-item>
                        <ion-toggle slot="start"></ion-toggle>
                        <ion-label>Receive Push Notifications</ion-label>
                    </ion-item>
                    <ion-item>
                        <ion-toggle slot="start"></ion-toggle>
                        <ion-label>Receive Emails</ion-label>
                    </ion-item>
                    <ion-item>
                        <ion-toggle slot="start"></ion-toggle>
                        <ion-label>Receive Text Messages</ion-label>
                    </ion-item>
                </ion-list>
                <div class="logout-container" slot="content">
                    <div class="ion-padding">
                        <ion-button size="large" expand="block" router-link="/login" router-direction="back"
                                    @click="store.clear()">Logout
                        </ion-button>
                    </div>
                </div>
            </div>
        </ion-content>
    </ion-page>
</template>

<script setup lang="ts">
import {IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonItem, IonLabel, IonButton} from '@ionic/vue';
import {StorageService} from "@/services/storage.service";
import {reactive, ref} from "vue";

const store = new StorageService();

// Define reactive variables
const isUserInfoLoaded = ref(false);
const userInfo = reactive({
    userName: '',
    role: '',
    floor: '',
    zoneArray: [] as string[]
});


getUserInfo();

/**
 * Function that fetches the current logged in users info from the database
 * and saves it in variables for displaying in the GUI
 */
async function getUserInfo() {
    // Call the read method to retrieve the user data
    const userData = await store.read('user');

    if (userData !== null) {
        const userDataParsed = JSON.parse(userData.value!);
        console.log(userData);
        userInfo.userName = userDataParsed.username;
        userInfo.role = userDataParsed.roles[0];
        checkRole();
    }
}

/**
 * Function that converts the caps-text version of the role, from the database,
 * into a nicer looking text version for the display in the GUI
 */
function checkRole() {
    if (userInfo.role.includes('ROLE_DEPUTYLEADER')) {
        userInfo.role = 'Deputy leader'
    } else if (userInfo.role.includes('ROLE_EVACLEADER')) {
        userInfo.role = 'Evacuation leader'
    } else if (userInfo.role.includes('ROLE_USER')) {
        userInfo.role = 'User'
    }
}
</script>

<style scoped>
.content-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: space-between;
    height: 100%;
}

.userinfo-container {
    display: flex;
    justify-content: center;
    margin-top: 20px;
    margin-bottom: 20px;
}

.logout-container {
    display: flex;
    justify-content: center;
    align-items: flex-end;
    flex-grow: 1;
}
</style>