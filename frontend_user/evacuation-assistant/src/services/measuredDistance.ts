/**
 * This function is responsible for measuring
 * distance to a particular beacon based on its RSSI (Received Signal Strength Indicator)
 * @param rssi the signal that has been found when scanning a bluetooth beacon
 */
export function measuredDistance(rssi: number): string {
    const txPower = -35;
    const n = 3.0; // attenuation of signal (this is about the air in the environment.)
    const distance = ((txPower - (rssi)) / (10.0 * n));

    return Math.pow(10, distance).toFixed(2);

}
