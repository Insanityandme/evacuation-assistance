<template>
    <ion-accordion-group :multiple="true">
        <!--This would be used for filtering purposes, this feature hasn't been fully implemented yet.-->
        <!--<ion-accordion value="second" readonly toggle-icon="">
            <ion-item slot="header" color="success">
                <ion-label>
                    <h2><b>High Priority</b></h2>
                </ion-label>
            </ion-item>
        </ion-accordion>-->
        <ion-accordion toggle-icon-slot="end" v-for="user in users" :key="user.id">

            <ion-item slot="header" color="light">
                <ion-label>{{ user.username }}</ion-label>
                <ion-chip color="tertiary">
                    <ion-icon :icon="constructOutline" color="success"></ion-icon>
                    <ion-label><b>{{ user.roles[0].name }}</b></ion-label>
                </ion-chip>
                <div v-if="user.roles[0].id === 3">
                    <ion-chip color="tertiary">
                        <ion-icon :icon="layersOutline" color="primary"></ion-icon>
                        <ion-label><b>{{ fetchFloorName(user.username) }}</b></ion-label>
                    </ion-chip>
                    <div v-if="fetchZoneName(user.username).length>1" style="display: inline">
                        <ion-chip color="tertiary" v-for="zone in fetchZoneName(user.username)" :key="zone">
                            <ion-icon :icon="mapOutline" color="warning"></ion-icon>
                            <ion-label><b>{{ zone }}</b></ion-label>
                        </ion-chip>
                    </div>
                    <div v-else style="display: inline">
                        <ion-chip color="tertiary">
                            <ion-icon :icon="mapOutline" color="warning"></ion-icon>
                            <ion-label><b>{{ fetchZoneName(user.username)[0] }}</b></ion-label>
                        </ion-chip>
                    </div>
                    <ion-chip color="tertiary">
                        <ion-icon :icon="alertOutline" color="danger"></ion-icon>
                        <ion-label><b>{{ fetchPriorityName(fetchPriority(user.id)) }}</b></ion-label>
                    </ion-chip>
                </div>
            </ion-item>

            <div id="usersList" slot="content">
                <ion-list :inset="true">
                    <ion-item>
                        <ion-label>
                            <ion-icon :icon="person" slot="start"/>
                            {{ user.username }}
                        </ion-label>
                        <div style="background-color: rgba(82,96,255,0.12); opacity: 90%; border-radius: 5px;">
                            <ion-buttons>
                                <ion-button fill="clear" class="ion-float-right">
                                    <router-link :to="'/tabs/UsersManager/edit/'+user.id"><ion-icon :icon="pencil"/></router-link>
                                </ion-button>
                                <ion-button fill="clear" class="ion-float-right"
                                            @click="presentActionSheet(user.id, user.username)">
                                    <ion-icon :icon="trash"></ion-icon>
                                </ion-button>
                            </ion-buttons>
                        </div>
                    </ion-item>

                    <ion-item>
                        <ion-label>
                            <ion-icon :icon="mail" slot="start"/>
                            {{ user.email }}
                        </ion-label>
                    </ion-item>
                    <ion-item class="ion-align-items-center" v-if="user.roles[0].id === 3">
                        <ion-chip color="tertiary">
                            <ion-icon :icon="constructOutline" color="success"></ion-icon>
                            <ion-label><b>Role: {{ user.roles[0].name }}</b></ion-label>
                        </ion-chip>
                        <ion-chip color="tertiary">
                            <ion-icon :icon="layersOutline" color="primary"></ion-icon>
                            <ion-label><b>Floor: {{ fetchFloorName(user.username) }}</b></ion-label>
                        </ion-chip>

                        <div v-if="fetchZoneName(user.username).length>1">
                            <ion-chip color="tertiary" v-for="zone in fetchZoneName(user.username)" :key="zone">
                                <ion-icon :icon="mapOutline" color="warning"></ion-icon>
                                <ion-label><b>Zone: {{ zone }}</b></ion-label>
                            </ion-chip>
                        </div>
                        <div v-else>
                            <ion-chip color="tertiary">
                                <ion-icon :icon="mapOutline" color="warning"></ion-icon>
                                <ion-label><b>Zone: {{ fetchZoneName(user.username)[0] }}</b></ion-label>
                            </ion-chip>
                        </div>
                        <ion-chip color="tertiary">
                            <ion-icon :icon="alertOutline" color="danger"></ion-icon>
                            <ion-label><b>Priority: {{ fetchPriorityName(fetchPriority(user.id)) }}</b></ion-label>
                        </ion-chip>
                    </ion-item>
                    <ion-chip color="tertiary" v-else>
                        <ion-icon :icon="constructOutline" color="success"></ion-icon>
                        <ion-label><b>Role: {{ user.roles[0].name }}</b></ion-label>
                    </ion-chip>
                </ion-list>
            </div>
        </ion-accordion>
    </ion-accordion-group>
</template>

<script setup lang="ts">
import {trash, alertOutline, mapOutline, layersOutline, person, mail, pencil, constructOutline} from "ionicons/icons";
import {IonButtons, IonButton, IonList, IonLabel, IonItem, IonIcon, IonChip, IonAccordionGroup, IonAccordion} from '@ionic/vue';
import {actionSheetController} from "@ionic/vue";

import {confirmDeletion, Delegation, getAllDelegations, getAllPriorities, getAllUsers, getPriorityInfo, Priority, PriorityInfo, Users} from "@/data/user";
import {ref} from "vue";

const users = ref<[Users]>();
const delegations = ref<[Delegation]>();
const priorities = ref<[Priority]>();
const priorityName = ref<[PriorityInfo]>();
//Fetch all users' information and save them into the users variable.
const fetchAllUsers = async () => {
    // POST request to our backend API
    const response = await getAllUsers();
    users.value = response.data;
}
//Fetch all delegations' information and save them into the delegations variable.
const fetchAllDelegations = async () => {
    // POST request to our backend API
    const response = await getAllDelegations();
    delegations.value = response.data;
}
//Fetch all priorities information and save them into the priorities variable.
const fetchAllPriorities = async () => {
    // POST request to our backend API
    const response = await getAllPriorities();
    priorities.value = response.data;
}
//Fetch all information regarding priorities, namely the name and id of each priority, and save them into the priorities variable.
const fetchPriorityInfo = async () => {
    // POST request to our backend API
    const response = await getPriorityInfo();
    priorityName.value = response.data;
}
//Perform the following asynchronous API calls when the page loads.
fetchAllUsers();
fetchAllDelegations();
fetchPriorityInfo();
fetchAllPriorities();
//When the user presses the delete button and confirms the deletion, an API call is performed to remove the chosen user from the backend server.
const confirmDeletionButton = async (num: number) => {
    await confirmDeletion(num);
    //Refresh the page to see the changes
    window.location.reload();
}
//Fetch the name of the zone from the previously fetched delegations from the backend into the delegations variable based on the username.
const fetchZoneName = (username: string): Array<string> => {
    const output = Array<string>();
    if (delegations.value !== undefined && delegations.value.length > 0) {
        delegations.value.forEach((delegate: any) => {
            if (delegate.username === username) {
                output.push(delegate.zoneName[0]);
            }
        });
    }
    return output;
}
//Fetch the name of the floor from the previously fetched delegations from the backend into the delegations variable based on the username.
const fetchFloorName = (username: string): string => {
    let output = "";
    if (delegations.value !== undefined && delegations.value.length > 0) {
        delegations.value.forEach((delegate: any) => {
            if (delegate.username === username) {
                output = delegate.floorName;
            }
        });
    }
    return output;
}
//Fetch the name of the priority from the previously fetched priorities from the backend into the priorities variable based on the user ID.
const fetchPriority = (id: number): number => {
    let output = 0;
    if (priorities.value !== undefined && priorities.value.length > 0) {
        priorities.value.forEach((prio: any) => {
            if (id === prio.id) {
                output = prio.priority;
            }
        });
    }
    return output;
}
//Fetch the name of the priority from the previously fetched priorities from the backend into the priorityName variable based on the priority ID.
const fetchPriorityName = (priorityId: number): string => {
    let output = "";
    if (priorityName.value !== undefined && priorityName.value?.length > 0) {
        priorityName.value.forEach((prioName: any) => {
            if (priorityId === prioName.id) {
                output = prioName.name;
            }
        })
    }
    return output;
}
//Present Action Sheet when pressed on the delete button (trash can icon), and present the user with two options and a header asking for deletion confirmation.
const presentActionSheet = async (num: number, name: string) => {
    const actionSheet = await actionSheetController.create({
        header: 'Are you sure you want to delete the user: ' + name,
        buttons: [
            {
                text: 'Cancel',
                role: 'cancel',
                data: {
                    action: 'cancel',
                },
            },
            {
                text: 'Delete',
                role: 'destructive',
                data: {
                    action: 'delete',
                },
                handler: () => {
                    console.log("User chose to delete userid: " + num + " which has username: " + name);
                    confirmDeletionButton(num);
                },
            },
        ],
    });
    await actionSheet.present();
}

</script>

<style scoped>
#container strong {
    font-size: 20px;
    line-height: 26px;
}

#container p {
    font-size: 16px;
    line-height: 22px;
    color: #8c8c8c;
    margin: 0;
}

#container a {
    text-decoration: none;
}


ion-col {
    background-color: #135d54;
    border: solid 1px #fff;
    color: #fff;
    text-align: center;
}

:root {
    --ion-color-rose: #fecdd3;
    --ion-color-rose-rgb: 254, 205, 211;
    --ion-color-rose-contrast: #000000;
    --ion-color-rose-contrast-rgb: 0, 0, 0;
    --ion-color-rose-shade: #e0b4ba;
    --ion-color-rose-tint: #fed2d7;
}

</style>