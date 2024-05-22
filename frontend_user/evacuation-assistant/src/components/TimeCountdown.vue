<template>
    <div>
        <template v-if="myvariables.answeredYes">
            <AnsweredCountdownYes/>
        </template>
        <template v-else-if="myvariables.answeredNo">
            <AnsweredCountdownNo></AnsweredCountdownNo>
        </template>
        <template v-else>
            <ion-progress-bar :value="myvariables.progress"></ion-progress-bar>
            <ion-card>
                <ion-card-header>
                    <ion-card-title>Evacuation in progress!</ion-card-title>
                </ion-card-header>
                <ion-card-content>
                    Evacuation in progress.
                    Are you available?
                    {{ myvariables.finalAnswer }}
                </ion-card-content>
                <ion-button @click="answer('yes')">Yes</ion-button>
                <ion-button @click="answer('no')">No</ion-button>
            </ion-card>
        </template>

    </div>
</template>

<script setup lang="ts">
import {changeActiveTrue} from "@/data/changeActive";
import {reactive} from "vue";
import {IonProgressBar, IonButton, IonCard, IonCardContent, IonCardHeader, IonCardTitle,} from "@ionic/vue";
import AnsweredCountdownYes from "@/components/AnsweredCountdownYes.vue";
import AnsweredCountdownNo from "@/components/AnsweredCountdownNo.vue";
import {StorageService} from '@/services/storage.service'
import router from "@/router";

// create a StorageService object
const store = new StorageService();

let myInterval: number | undefined;

const myvariables = reactive({
    progress: 1,
    interval: undefined,

    duration: {
        type: 1,
        required: true,
    },
    //finalAnswer: "noAnswerYet",
    answeredYes: false,
    answeredNo: false,
})

/**
 * gets data for logged in user,
 * sends username as parameter to changeActiveTrue method
 * for calling the backend.
 * @param value
 */
const submitForm = async (value: string) => {
    const userData = await store.read('user');
    // eslint-disable-next-line
    const userDataParsed = JSON.parse(userData.value!);
    await changeActiveTrue(userDataParsed.username);
    console.log(value)
}
/**
 * method used to set boolean answeredYes/answeredNo to true
 * if corresponding buttons in ion-card are clicked
 * progress of progressbar set to 1 to
 * @param value String yes or no depending on button pressed
 */
const answer = async (value: string) => {
    clearInterval(myInterval);

    if (value === "yes") {
        myvariables.answeredYes = true;
        myvariables.progress = 1;
        await submitForm(value);
    } else if (value === "no") {
        myvariables.answeredNo = true;
        myvariables.progress = 1;
    }
}


/**
 * starts the countdown on the progress bar
 * if progress < 0, redirect to /tabs/home/evacleader
 */
const startCountdown = async () => {
    const step = 0.01;

    myInterval = setInterval(() => {
        myvariables.progress -= step;
        if (myvariables.progress < 0) {
            router.replace('/tabs/home/evacleader');
            myvariables.progress = 1;
        }
    }, 100);
}

const props = defineProps({
    finalAnswer: String,
})

if (props.finalAnswer === "yes") {
    console.log("And now the final answer is: " + props.finalAnswer);
    answer(props.finalAnswer);
}
if (props.finalAnswer === "no") {
    console.log("And now the final answer is: " + props.finalAnswer);
    answer(props.finalAnswer);
}

startCountdown();

</script>
