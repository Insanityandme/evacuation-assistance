import {CapacitorHttp} from "@capacitor/core";
import {resourceUrl} from "@/data/resourceUrl";

// url to access our API
const url = `${resourceUrl}/api/auth/`;
const urlPositions = `${resourceUrl}/api/sensor/`;
const evacUrl = `${resourceUrl}/api/evacAuth/getDelegationsByUsername/`;

// interface for user data
export interface User {
    username: string,
    email: string,
    password: string
}

/**
 * This function is responsible for making a post request
 * to our backend and return data from our server
 * @param user
 */
export const signInUser = async (user: User) => {
    const options = {
        url: `${url}signin`,
        headers: {"Content-Type": "application/json"},
        data: JSON.stringify(user)
    }

    return CapacitorHttp.post(options);
}

/**
 * This function is responsible for sending positional data to our server
 * @param id of the sensor you are close to
 * @param username of the logged in user
 */
export const sendPositionData = async (id: number, username: string) => {
    const options = {
        url: `${urlPositions}updateUserPos`,
        headers: {"Content-Type": "application/json"},
        data: JSON.stringify({id: id, username: username})
    }

    return CapacitorHttp.post(options);
}

/**
 * This function is responsible for retrieving
 * Positional data of all users
 */
export const getAllUserPositionData = async () => {
    const options = {
        url: `${urlPositions}getAllUserPos`,
        headers: {"Content-Type": "application/json"},
    }

    return CapacitorHttp.get(options);
}

/**
 * This function is responsible for resetting a persons position
 * @param username user currently logged in
 */
export const resetUserPosition = async (username: string) => {
    const options = {
        url: `${urlPositions}updateDefaultUserPos`,
        headers: {"Content-Type": "application/json"},
        data: JSON.stringify({username: username})
    }

    return CapacitorHttp.post(options);
}

/**
 * This function is responsible for getting floor and zone information
 * from a logged in user
 * @param userName of the person currently logged in
 */
export const getFloorAndZone = async (userName: string) => {
    const options = {
        url: `${evacUrl}${userName}`,
        headers: {"Content-Type": "application/json"},
    }

    return CapacitorHttp.get(options);
}

/**
 * This function is responsible for when a person has been helped
 * set that value to true in the database
 * @param username the user currently logged in
 */
export const setHelpedToTrue = async (username: string) => {
    const options = {
        url: `${urlPositions}updateNeedsHelpTrue/${username}`,
        headers: {"Content-Type": "application/json"},
        data: JSON.stringify(username)
    }

    return CapacitorHttp.post(options);
}

/**
 * Gets all sensors that we have stored in our database
 */
export const getAllSensors = async () => {
    const options = {
        url: `${urlPositions}getAllSensors`,
        headers: {"Content-Type": "application/json"},
    }

    return CapacitorHttp.get(options);
}
