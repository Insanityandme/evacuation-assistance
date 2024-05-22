import {MovingAverageFilter} from "@/services/movingAverageFilter";
import {measuredDistance} from "@/services/measuredDistance";
import {ref} from "vue";
import {BleClient} from "@capacitor-community/bluetooth-le";
import {getAllSensors, sendPositionData} from "@/data/user";
import {StorageService} from "@/services/storage.service";

// local storage on our device
const storage = new StorageService();

// identifier for all the beacons that are in use for this project.
const BEACON_SERVICES = '0000feaa-0000-1000-8000-00805f9b34fb';

// amount of signals to average
const WINDOW_SIZE = 20;

const CUT_OFF_PERCENTAGE = 10;
const AMOUNT_OF_DEVICES_TO_SCAN = 3;

const filter = new MovingAverageFilter(WINDOW_SIZE, CUT_OFF_PERCENTAGE);
export const devices: any = ref([])
export const statusCode = ref();

/**
 * This function is responsible for finding beacons/bluetooth sensors
 * and to store that data so that we can use that to approximate
 * your relative position to them.
 */
export async function startScan() {
    devices.value = [];

    try {
        await BleClient.initialize();

        await BleClient.requestLEScan({
            services: [BEACON_SERVICES],
            scanMode: 2,
            allowDuplicates: true
        }, (result) => {
            // amount of devices to store
            if (devices.value.length < AMOUNT_OF_DEVICES_TO_SCAN) {
                if (result.rssi != null) {
                    const index = devices.value.findIndex((x: {
                        name: string | undefined;
                    }) => x.name == result.localName);

                    index === -1 ? devices.value.push({
                        name: result.localName,
                        rssi: result.rssi,
                        distance: measuredDistance(result.rssi),
                        filtered: measuredDistance(filter.getFilteredValue())
                    }) : console.log("object already exists");
                }
            } else {
                devices.value.forEach((device: any) => {
                    if (device.name == result.localName) {
                        if (result.rssi != null) {
                            filter.addValue(result.rssi);
                            device.rssi = result.rssi;
                            device.distance = measuredDistance(result.rssi);
                            device.filtered = measuredDistance(filter.getFilteredValue());
                        }
                    }
                })
            }
        });

        // after a three second scan send it to the backend server
        setTimeout(async () => {
            await sendPositionalData();
        }, 3000)

    } catch (error) {
        console.log(error);
    }
}

/**
 * This function is responsible for sorting the distance
 * and to pick the one closes to our device and send this
 * data to the backend server.
 */
async function sendPositionalData() {
    const allSensorPosition = await getAllSensors();

    devices.value.sort((a: any, b: any) => {
        return a.distance - b.distance
    });

    for (const sensor of allSensorPosition.data) {
        if (devices.value[0].name === sensor.sensorName) {
            const userData = await storage.read('user');

            // eslint-disable-next-line
            const userDataParsed = JSON.parse(userData.value!);
            const sentData = await sendPositionData(sensor.id, userDataParsed.username);

            statusCode.value = sentData.status;
            await stopScan();
        }
    }
}

/**
 * This function is responsible for stopping the
 * scans of the beacon devices.
 */
export async function stopScan() {
    await BleClient.stopLEScan()
}