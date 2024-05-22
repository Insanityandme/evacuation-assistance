<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <ion-buttons slot="start">
                    <ion-button href="/tabs/UsersManager" ><ion-icon :icon="chevronBack" slot="start"/>Manage Users</ion-button>
                </ion-buttons>
                <ion-title>Add User</ion-title>
            </ion-toolbar>
        </ion-header>
        <ion-content :fullscreen="true">
            <AddEditUserContainer :sign-up-user="signUp"/>
        </ion-content>
    </ion-page>
</template>

<script lang="ts">
export default {
    name: "AddUserPage",
}
</script>

<script setup lang="ts">
import AddEditUserContainer from '@/components/AddEditUserContainer.vue';
import {chevronBack} from "ionicons/icons";
import {signUpUser, User} from "@/data/user";

const signUp = async (user: User) => {
    // POST request to our backend API
    const response = await signUpUser(user);
    console.log(response.data);
    // if we exist in the backend DB, create a object storing our information
    if (response.status === 200 ) {
        console.log('Successfully registered the user into the database!');
    }
}
</script>

<style scoped>

</style>