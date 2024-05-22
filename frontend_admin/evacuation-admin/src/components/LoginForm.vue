<template>
    <ion-list>
        <ion-item>
            <ion-input placeholder="Username" v-model="v$.username.$model" @blur="v$.username.$touch"></ion-input>
            <ion-label color="danger" v-if="v$.username.$error">Name is required</ion-label>
        </ion-item>

        <ion-item>
            <ion-input placeholder="Email" v-model="v$.email.$model" type="email"></ion-input>
            <ion-label color="danger" v-if="v$.email.$error">Invalid email</ion-label>
        </ion-item>

        <ion-item>
            <ion-input placeholder="Password" v-model="v$.password.$model" type="password"></ion-input>
            <ion-label color="danger" v-if="v$.password.$error">Password is required</ion-label>
        </ion-item>
    </ion-list>
    <ion-button class="ion-margin-top" color="success" expand="block" @click="submitForm()">Sign in</ion-button>
    <ion-button class="ion-margin-top" color="danger" expand="block">Forgot your password?</ion-button>
</template>


<script lang="ts">
export default {
    name: "LoginForm"
}
</script>

<script setup lang="ts">
import {IonButton, IonList, IonItem, IonInput, IonLabel} from '@ionic/vue';
import {reactive} from 'vue';
import {useVuelidate} from '@vuelidate/core';
import {required, email} from '@vuelidate/validators';

const state = reactive({
    username: '',
    email: '',
    password: ''
})
const rules = {
    username: {required},
    email: {required, email},
    password: {required},
}
const v$ = useVuelidate(rules, state);
const props = defineProps({
    signInUser: {
        type: Function,
        required: true
    }
})
const submitForm = async () => {
    const isFormCorrect = await v$.value.$validate();
    if (isFormCorrect) {
        props.signInUser({username: state.username, email: state.email, password: state.password});
    }
}
</script>

<style scoped>

</style>