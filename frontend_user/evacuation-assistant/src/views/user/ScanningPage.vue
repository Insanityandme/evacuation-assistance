<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <ion-title>Scanning</ion-title>
            </ion-toolbar>
        </ion-header>
        <ion-content>
            <ion-button @click="startScan()">Start scanning</ion-button>
            <ion-button @click="stopScan()">Stop scanning</ion-button>
            <ion-list v-for="device in devices" :key="device">
                <ion-item>Device: {{ device.name }}, Rssi: {{ device.rssi }}</ion-item>
                <ion-item>Distance (in m): {{ device.distance }}, Filtered: {{ device.filtered }}</ion-item>
            </ion-list>
        </ion-content>
    </ion-page>
</template>

<script setup lang="ts">
import {IonButton, IonContent, IonHeader, IonItem, IonList, IonPage, IonTitle, IonToolbar} from '@ionic/vue';
import {startScan, stopScan, devices} from "@/services/scanner";
</script>
