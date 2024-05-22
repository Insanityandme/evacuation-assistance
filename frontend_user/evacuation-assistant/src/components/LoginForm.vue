<template>
    <ion-list>
        <ion-item>
            <ion-input placeholder="Username" v-model="v$.username.$model"
                       @blur="v$.username.$touch"></ion-input>
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
    <ion-button class="ion-margin-top" color="primary" expand="block" @click="submitForm()">Sign in</ion-button>
    <ion-button class="ion-margin-top" size="small" fill="clear" expand="block">Forgot your password?</ion-button>
</template>

<script setup lang="ts">
import {IonButton, IonList, IonItem, IonInput, IonLabel} from '@ionic/vue';
import {reactive} from 'vue';
import {useVuelidate} from '@vuelidate/core';
import {required, email} from '@vuelidate/validators';

/**
 * Responsible for storing form data,
 * this data is reactive and will be stored
 * as soon as you start typing
 */
const state = reactive({
    username: '',
    email: '',
    password: ''
})

/**
 * Rules for validation
 */
const rules = {
    username: {required},
    email: {required, email},
    password: {required},
}

/**
 * This variable uses the plugin Vuelidate to
 * set the rules in relation to our state data.
 * Through this we can bind the data to our HTML tags
 */
const v$ = useVuelidate(rules, state);

/**
 * Used to pass data from our LoginForm.vue
 * to LoginPage.vue
 */
const props = defineProps({
    signInUser: {
        type: Function,
        required: true
    }
})

/**
 * When all rules are correct, submit form data
 * and send it to the LoginPage.vue to check if
 * the data is in our backend database.
 */
const submitForm = async () => {
    const isFormCorrect = await v$.value.$validate();

    if (isFormCorrect) {
        props.signInUser({username: state.username, email: state.email, password: state.password});
    }
}
</script>

<style scoped>

</style>