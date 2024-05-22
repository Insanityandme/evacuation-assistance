import {ref} from "vue";

const data = ref();
data.value = 0;

/**
 * This function is responsible for setting a counter to true
 * in the tabs page
 * @param count the number of received notifications from users
 */
export function setCounter(count: number) {
    data.value = count;
}

/**
 * Retrieves the value so that we can update this in the tabs page.
 */
export function getCounter() {
    return data.value;
}
