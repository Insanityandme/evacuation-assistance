<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <ion-title>Notifications</ion-title>
            </ion-toolbar>
        </ion-header>

        <ion-content :fullscreen="true">
            <div id="incoming">
                <ion-card v-for="(user) in getUserPositionsSorted()" :key="user"
                          :style="{ backgroundColor: !user.needsHelp ? '#93bdd9' : ''}">
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
                </ion-card>
            </div>
        </ion-content>
    </ion-page>
</template>

<script setup lang="ts">
import {
    IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonCardHeader,
    IonCardContent, IonCard, IonCardTitle,
} from '@ionic/vue';
import {getAllUserPositionData} from "@/data/user";
import {setCounter} from "@/services/notificationCounter";
import {ref} from "vue";

const userPositions: any = ref({})

/**
 * This function is responsible for polling the server for
 * user data and comparing that data to what exists locally
 *
 */
const getUserPositions = async () => {
    setInterval(async () => {
        const recievedUserPositions = await getAllUserPositionData();

        for (const userPosition of recievedUserPositions.data) {
            if (userPosition.floorName !== null && userPosition.needsHelp == true) {
                userPositions.value[userPosition.username] = userPosition;
            } else {
                delete userPositions.value[userPosition.username]
            }
        }

        const notificationCounter = Object.values(userPositions.value).filter((user: any) => user.needsHelp === true).length;

        setCounter(notificationCounter);
    }, 1000);
}

getUserPositions();

/**
 * This function sorts all local user position data based on
 * the variable needsHelp so that it can be displayed properly.
 */
function getUserPositionsSorted() {
    return Object.values(userPositions.value).sort((a: any, b: any) => Number(!a.needsHelp) - Number(!b.needsHelp));
}
</script>

