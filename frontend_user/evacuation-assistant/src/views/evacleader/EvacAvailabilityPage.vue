<!--
This class is responsible for displaying a
notification with a progressbar and yes/no answer when
evacuation is triggered
-->
<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <ion-title>Evacuation Assistant</ion-title>
            </ion-toolbar>
        </ion-header>
        <ion-content :fullscreen="true">
            <ion-header collapse="condense">
                <ion-toolbar>
                    <ion-title size="large">Evacuation Assistant</ion-title>
                </ion-toolbar>
            </ion-header>
            <div>
                <time-countdown :duration="5000" :finalAnswer="answer" @answer="handleAnswer"/>
            </div>
        </ion-content>
    </ion-page>
</template>

<script setup lang="ts">
import {IonPage, IonHeader, IonToolbar, IonContent, IonTitle} from '@ionic/vue';
import TimeCountdown from "@/components/TimeCountdown.vue";
import {useRoute} from "vue-router";

const handleAnswer = async (value: any) => {
    console.log(value);
}

const route = useRoute();
const {answer} = route.params;
console.log("the final answer tapped was: " + answer);
</script>

<style scoped>

</style>