import {ref} from "vue";

/**
 * A simulated floor counter used to show
 * how a user with the role Deputy Leader
 * could potentially see how many people
 * are in every floor in a building.
 *
 * Note: This would in the end use a hardware
 * from Axiss that counts people with a camera.
 */
export const counter1 = ref(40);
export const counter2 = ref(68);
export const counter3 = ref(95);
export const counter4 = ref(20);
export const counter5 = ref(25);
export const counter6 = ref(100);
export const floorCounters = [counter1, counter2, counter3, counter4, counter5, counter6]

let interval: number | undefined;
export const floorCounter = async () => {
    if (counter6.value > 0) {
        counter6.value--;
        counter5.value++;
    } else if (counter5.value > 0) {
        counter5.value--;
        counter4.value++;
    } else if (counter4.value > 0) {
        counter4.value--;
        counter3.value++;
    } else if (counter3.value > 0) {
        counter3.value--;
        counter2.value++;
    } else if (counter2.value > 0) {
        counter2.value--;
        counter1.value++;
    }
}

export const resetCounter = async () => {
    clearInterval(interval);
    counter6.value = 80;
    counter5.value = 22;
    counter4.value = 68;
    counter3.value = 12;
    counter2.value = 47;
    counter1.value = 31;
}

export const startCounter = async () => {
    interval = setInterval(floorCounter, 200);
}
