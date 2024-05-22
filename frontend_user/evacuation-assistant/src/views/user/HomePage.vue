<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <ion-title>Home</ion-title>
            </ion-toolbar>
        </ion-header>
        <ion-content>
          <ion-header collapse="condense">
            <ion-toolbar>
              <ion-title size="large" email="wow">Welcome!</ion-title>
            </ion-toolbar>
          </ion-header>
            <ion-item>
                <ion-label style="font-size: 19px">Logged in as: {{ username }}</ion-label>
            </ion-item>
            <ion-card>
                <ion-img style="width:50%" src="../assets/img/holding-phone-2.jpg"></ion-img>
                <ion-card-header>
                    <ion-card-title>How to use</ion-card-title>
                </ion-card-header>
                <ion-card-content>
                  Hold your phone like in the picture. <br>
                  Press the button. <br>
                  Stand still for 3 seconds. <br>
                  Wait until you receive an alert.
                </ion-card-content>
            </ion-card>
          <div class="button-container">
          <ion-button size="large"  @click="sendData()">Help me</ion-button>
          </div>
        </ion-content>
    </ion-page>
</template>

<script setup lang="ts">
import {
    IonImg, IonButton, IonContent, IonHeader, IonItem, IonLabel,
    IonPage, IonTitle, IonToolbar, alertController,
    IonCard, IonCardHeader, IonCardContent, IonCardTitle
} from '@ionic/vue';
import {ref} from "vue";
import {startScan, statusCode} from "@/services/scanner";
import {StorageService} from "@/services/storage.service";

const username = ref('');
const storage = new StorageService();

/**
 * This function retrieves a users username
 */
const getUserName = async () => {

    const userData = await storage.read('user');

    // eslint-disable-next-line
    const userDataParsed = JSON.parse(userData.value!);
    username.value = userDataParsed.username;
}

getUserName();

/**
 * This function is responsible for presenting an alert
 * to the user depending on the status code of the startScan() function
 */
const sendData = async () => {
    await startScan();

    setTimeout(async () => {
        if (statusCode.value === 200) {
            await presentAlert('Successfully sent', 'Please wait where you are, help is coming shortly');
        } else {
            await presentAlert('Unsuccessful', 'No connection, try again')
        }
    }, 3500)
}

/**
 * This function presents modified alert to the user
 * @param subHeader
 * @param message
 */
const presentAlert = async (subHeader: string, message: string) => {
    const alert = await alertController.create({
        header: 'Alert',
        subHeader: subHeader,
        message: message,
        buttons: ['OK']
    });

    await alert.present();
}

</script>

<style scoped>
.button-container {
  display: flex;
  justify-content: center;
  margin-top: 10px; /* Adjust the margin as needed */
  margin-bottom: 10px;
}

.userinfo-container {
  display: flex;
  justify-content: center;
}
</style>