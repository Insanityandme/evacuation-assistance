<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-title>Manage Users</ion-title>
          <ion-buttons slot="end">
              <!--<ion-button color="primary" id="open-modal"><ion-icon :icon="filterOutline" color="primary"/>&nbsp;Filter</ion-button>-->
              <ion-button color="primary" @click="setAlarmToActive()"><ion-icon :icon="alertOutline" color="warning"></ion-icon>Start Evacuation Simulation</ion-button>
              <ion-button color="primary" @click="setAlarmToDeActive()"><ion-icon :icon="alertOutline" color="warning"></ion-icon>Stop Evacuation Simulation</ion-button>
              <ion-button color="primary" href="/tabs/UsersManager/add"><ion-icon :icon="add" size="large" color="primary"/></ion-button>
          </ion-buttons>
      </ion-toolbar>
    </ion-header>
    <ion-content :fullscreen="true">

        <UsersContainerAccordion/>
        <!--This functionality was not implemented due to lack of time,
        however, it would be cool to have the ability to filter the user
        data in the AddEditUserContainer component.-->
        <!--<ion-content class="ion-padding">
            <ion-modal ref="modal" trigger="open-modal">
                <ion-content>

                    <ion-toolbar>
                        <ion-title>Filter</ion-title>
                        <ion-buttons slot="end">
                            <ion-button color="light" @click="dismiss()">Close</ion-button>
                        </ion-buttons>
                    </ion-toolbar>

                    <ion-list>
                        <ion-item>
                            <ion-icon :icon="person" slot="start"/>
                            <ion-label>
                                <h2>Fullname</h2>
                                <p>Filter after Fullname</p>
                            </ion-label>
                        </ion-item>
                        <ion-item>
                            <ion-icon :icon="layersOutline" slot="start" color="primary"/>
                            <ion-label>
                                <h2>Floor</h2>
                                <p>Filter after Floor level</p>
                            </ion-label>
                        </ion-item>
                        <ion-item>
                            <ion-icon :icon="mapOutline" slot="start" color="warning"/>
                            <ion-label>
                                <h2>Zone</h2>
                                <p>Filter after Zone</p>
                            </ion-label>
                        </ion-item>
                        <ion-item>
                            <ion-icon :icon="alertOutline" slot="start" color="danger"/>
                            <ion-label>
                                <h2>Priority</h2>
                                <p>Filter after Priority</p>
                            </ion-label>
                        </ion-item>
                    </ion-list>

                </ion-content>
            </ion-modal>
        </ion-content>-->
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import {IonPage, IonHeader, IonIcon} from '@ionic/vue';
import UsersContainerAccordion from "@/components/UsersContainerAccordion.vue";
import {add, alertOutline} from "ionicons/icons";
import {activateAlarm, deActivateAlarm, sendNotifications} from "@/data/alarm";

/**
 * Method to be called when "Start Evacuation Simulation" button is clicked, in which "sendNotifications" and "deActivateAlarm" method from data/user file.
 */
const setAlarmToActive = async () => {
    await sendNotifications();
    await activateAlarm();
}
/**
 * Method to be called when "Stop Evacuation Simulation" button is clicked, in which deActivateAlarm method from data/user file.
 */
const setAlarmToDeActive = async () => {
    await deActivateAlarm();
}

</script>
<style>
ion-modal {
    --height: 50%;
    --border-radius: 16px;
    --box-shadow: 0 10px 15px -3px rgb(0 0 0 / 0.1), 0 4px 6px -4px rgb(0 0 0 / 0.1);
}

ion-modal::part(backdrop) {
    background: rgba(209, 213, 219);
    opacity: 1;
}

ion-modal ion-toolbar {
    --background: rgb(14 116 144);
    --color: white;
}
</style>