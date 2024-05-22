export interface Point {
    x: number;
    y: number;
}

export interface Beacon {
    position: Point;
    distance: number;
}

/**
 * This function is responsible for trilateration of three beacons
 * to be able to find your relative position to these three beacons
 * @param beacons the beacons found when scanning
 */
export function trilaterate(beacons: Beacon[]): Point | null {
    if (beacons.length < 3) {
        console.error('At least three beacons are required for trilateration.');
        return null;
    }

    const A = beacons[0].position;
    const B = beacons[1].position;
    const C = beacons[2].position;

    const dA = beacons[0].distance;
    const dB = beacons[1].distance;
    const dC = beacons[2].distance;

    const W = (dA * dA - dB * dB + B.x * B.x - A.x * A.x + B.y * B.y - A.y * A.y) / 2;
    const Z = (dB * dB - dC * dC + C.x * C.x - B.x * B.x + C.y * C.y - B.y * B.y) / 2;

    const x = (W * (C.y - B.y) - Z * (B.y - A.y)) / ((B.x - A.x) * (C.y - B.y) - (C.x - B.x) * (B.y - A.y));
    const y = (W - x * (B.x - A.x)) / (B.y - A.y);

    return {x, y};
}

// Example usage
/*
const beacon1: Beacon = { position: { x: 0, y: 0 }, distance: 5 };
const beacon2: Beacon = { position: { x: 10, y: 0 }, distance: 8 };
const beacon3: Beacon = { position: { x: 5, y: 8 }, distance: 7 };

const position = trilaterate([beacon1, beacon2, beacon3]);
if (position) {
    console.log('Trilateration result:', position);
} else {
    console.error('Trilateration failed.');
}

export function trilateration() {
    beacons.value = [];

    devices.value.forEach((device: any) => {
        if (device.name === "evac-WtW4") {
            const beacon1: Beacon = {position: {x: -2.5, y: -7}, distance: 3.5};
            console.log(device.distance);
            beacons.value.push(beacon1);
        } else if (device.name === "evac-WtW3") {
            const beacon2: Beacon = {position: {x: -4.8, y: 0}, distance: 4.8};
            console.log(device.distance);
            beacons.value.push(beacon2);
        } else if (device.name === "evac-WtW2") {
            const beacon3: Beacon = {position: {x: 2.1, y: 2.2}, distance: 7.25};
            console.log(device.distance);
            beacons.value.push(beacon3);
        }
    })
    position.value = trilaterate(beacons.value);
    console.log(position.value);
}
 */


