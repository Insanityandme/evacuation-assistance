<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <ion-buttons slot="start">
                    <ion-button href="/tabs/UsersManager"><ion-icon :icon="chevronBack" slot="start"/> back</ion-button>
                </ion-buttons>
                <ion-title>Edit User {{ id }}</ion-title>
            </ion-toolbar>
        </ion-header>
        <ion-content :fullscreen="true">
            <AddEditUserContainer :edit=true :id="id"></AddEditUserContainer>
        </ion-content>
    </ion-page>
</template>

<script setup lang="ts">
import {chevronBack} from "ionicons/icons";
import AddEditUserContainer from "@/components/AddEditUserContainer.vue";
import {useRoute} from "vue-router";

const route = useRoute();
//Utilize the id varialbe that is sent from the UsersContainerAccordion
//component and send it to the AddEditUserContainer component.
//This variable is passed as a URL e.g. http://localhost:8100/tabs/UsersManager/edit/1
//See router/index.ts file to see how the URL should look like.
const { id } : any = route.params;
</script>

<style scoped>

</style>