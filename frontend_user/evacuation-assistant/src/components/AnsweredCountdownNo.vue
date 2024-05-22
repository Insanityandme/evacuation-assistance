<!--
This class is responsible for showing a card with
a message after answering no on TimeCountdown
-->

<template>
    <ion-card>
        <ion-card-header>
            <ion-card-title>Evacuation in progress!</ion-card-title>

        </ion-card-header>

        <ion-card-content>
            You answered no to being evacuation leader for current evacuation
        </ion-card-content>

    </ion-card>
</template>

<script>
export default {
    name: "answeredCountdownNo.vue",
}
</script>

<style scoped>

</style>