<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <ion-title>Notifications</ion-title>
            </ion-toolbar>
        </ion-header>
        <ion-content :fullscreen="true">
            <div id="incoming">
                <ion-card v-for="(user) in getUserPositions()" :key="user"
                          :style="{ backgroundColor: user.needsHelp ? '#93bdd9' : ''}">
                    <ion-card-header>
                        <ion-card-title>{{ user.username.slice(0, 1).toUpperCase() + user.username.slice(1) }} in need
                            of assistance
                        </ion-card-title>
                    </ion-card-header>
                    <ion-card-content>
                        <b>Location:</b> {{ user.position }}
                        <br>
                        <b>Disability:</b> {{ user.handicap }}
                        <br>
                        <b>Floor:</b> {{ user.floorName }}, Zone {{ user.zoneName }}
                        <br>
                        Can you help?
                    </ion-card-content>
                    <ion-button fill="clear" :color="user.needsHelp ? '' : 'success'"
                                @click="getUserHelped(user)">
                        {{ user.needsHelp ? 'Sent' : 'Send to deputy' }}
                    </ion-button>
                </ion-card>
            </div>
        </ion-content>
    </ion-page>
</template>

<script setup lang="ts">
import {
    IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonCard, IonCardHeader, IonCardContent,
    IonCardTitle, IonButton, isPlatform
} from '@ionic/vue';

import {getAllUserPositionData, setHelpedToTrue} from "@/data/user";
import {ref} from "vue";
import {setCounter} from "@/services/notificationCounter";
import {Alarm, getAlarmStatus, scheduleAdvanced} from "@/data/localNotification";

const userPositions: any = ref({})

const getUserPositionsTest = async () => {
    setInterval(async () => {
        const recievedUserPositions = await getAllUserPositionData();

        for (const userPosition of recievedUserPositions.data) {
            if (userPosition.floorName !== null) {
                userPositions.value[userPosition.username] = userPosition;
            } else {
                delete userPositions.value[userPosition.username]
            }
        }
        const notificationCounter = Object.values(userPositions.value).filter((user: any) => user.needsHelp === false).length;
        setCounter(notificationCounter);
    }, 1000);
}

getUserPositionsTest()
// Checks whether the platform is iOS, in that case checks every 1 second whether the
// server's alarm status has changed, if it has changed then the local notification is triggered.
// This was implemented as a workaround for push notification, since not much time was left to
// enable push notification for iOS.
if (isPlatform("ios")) {
    const alarmStatus = ref<[Alarm]>();
    const checkIfAlarmIsActive = async () => {
        const myInterval = setInterval(async () => {
            const response = await getAlarmStatus();
            alarmStatus.value = response.data;
            if (alarmStatus.value !== undefined) {
                if (alarmStatus.value[0].status === true) {
                    await scheduleAdvanced();
                    //await store.create('alreadyAnswered', "true");
                    clearInterval(myInterval);
                }
            }
        }, 1000);
    }
    checkIfAlarmIsActive();
}

const getUserHelped = async (user: any) => {
    // TODO: if the user has been given help, don't do anything
    if (user.needsHelp) {
        return;
    }

    await setHelpedToTrue(user.username);
    userPositions.value[user.username].needsHelp = false;

    const notificationCounter = Object.values(userPositions.value).filter((user: any) => user.needsHelp === true).length;
    setCounter(notificationCounter);
}

function getUserPositions() {
    return Object.values(userPositions.value).sort((a: any, b: any) => Number(a.needsHelp) - Number(b.needsHelp));
}
</script>
