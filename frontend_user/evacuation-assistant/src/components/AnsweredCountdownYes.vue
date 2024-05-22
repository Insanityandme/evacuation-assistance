<!--
This class is responsible for showing a card with
a message after answering yes on TimeCountdown
-->
<template>
    <ion-card>
        <ion-card-header>
            <ion-card-title>Evacuation in progress!</ion-card-title>
        </ion-card-header>
        <ion-card-content>
            You are now active evacuation leader
            <router-link to="/tabs/home/evacleader">
                <ion-button>Ok</ion-button>
            </router-link>
        </ion-card-content>
    </ion-card>
</template>

<script setup lang="ts">
import {IonButton, IonCard, IonCardContent, IonCardHeader, IonCardTitle} from "@ionic/vue";
</script>

<style scoped>
</style>