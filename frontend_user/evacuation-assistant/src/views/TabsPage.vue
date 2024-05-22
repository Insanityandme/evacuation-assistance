<template>
    <ion-page>
        <ion-tabs>
            <ion-router-outlet></ion-router-outlet>
            <ion-tab-bar slot="bottom">
                <ion-tab-button tab="tab1" @click="()=> router.push('/tabs/home/' + navigation)">
                    <ion-icon aria-hidden="true" :icon="homeOutline"/>
                    <ion-label :key="tab">{{ tab.tab1 }}</ion-label>
                </ion-tab-button>

                <ion-tab-button tab="tab2" @click="()=> router.push('/tabs/tab2/' + navigation)">
                    <ion-icon aria-hidden="true" :icon="megaphoneOutline"/>
                    <ion-label :key="tab">{{ tab.tab2 }}</ion-label>
                    <ion-badge v-if="leader" color="danger"> {{ getCounter() }}</ion-badge>
                </ion-tab-button>

                <ion-tab-button tab="tab4" @click="()=> router.push('/tabs/tab4/' + navigation)">
                    <ion-icon aria-hidden="true" :icon="settingsOutline"/>
                    <ion-label>Settings</ion-label>
                </ion-tab-button>

            </ion-tab-bar>
        </ion-tabs>
    </ion-page>
</template>

<script setup lang="ts">
import {IonTabBar, IonTabButton, IonTabs, IonLabel, IonIcon, IonPage, IonRouterOutlet, IonBadge} from '@ionic/vue';
import {megaphoneOutline, homeOutline, settingsOutline} from 'ionicons/icons';
import {StorageService} from "@/services/storage.service";
import {ref} from 'vue';
import router from "@/router";
import {getCounter} from "@/services/notificationCounter";

const store = new StorageService();
let role = '';
const navigation = ref('');
const tab = ref({});
const leader = ref();
leader.value = false;

getRole();

/**
 *
 */
/**
 * Method to fetch the role of the currently logged in user
 * and dynamically change the routing depending on that role
 */
async function getRole() {
    // Call the read method to retrieve the user data
    const userData = await store.read('user');

    if (userData !== null) {

        // eslint-disable-next-line
        const userDataParsed = JSON.parse(userData.value!);
        role = userDataParsed.roles[0];

        if (role === 'ROLE_DEPUTYLEADER') {
            navigation.value = 'deputyleader';
            leader.value = true;
            tab.value = {
                tab1: 'Home',
                tab2: 'Notifications'
            }
        } else if (role === 'ROLE_EVACLEADER') {
            navigation.value = 'evacleader';
            leader.value = true;
            tab.value = {
                tab1: 'Home',
                tab2: 'Notifications'
            }
        } else if (role === 'ROLE_USER') {
            navigation.value = 'user';
            tab.value = {
                tab1: 'Home',
                tab2: 'Test'
            }
        }
    }
}
</script>
