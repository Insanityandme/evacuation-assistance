import {CapacitorHttp} from "@capacitor/core";
import {ref} from "vue";
import {resourceUrl} from "@/data/resourceUrl";

const authUrl = 'api/auth';
const evacAuthUrl = 'api/evacAuth';
const userAuth = 'api/userAuth';
//User interface with ID
export interface User {
    //id: number,
    username: string,
    email: string,
    password: string,
    role: [
        name: string
    ],
}
//User interface without ID
export interface Users {
    id: number,
    username: string,
    email: string,
    password: string,
    roles: [
        id: number,
        name: string
    ],
}
//Responsibility interface
export interface Responsibility {
    floorname: string,
    zone: string[]
}
//Delegation interface
export interface Delegation {
    id: number,
    username: string,
    floorName: string,
    zoneName: [
        name: string
    ],
}
//Priority interface without Priority ID
export interface UserPriority {
    priority: number,
}
//Priority interface with Priority ID
export interface Priority {
    priority: number,
    id: number,
}
//Priority interface with Priority Name
export interface PriorityInfo {
    id: number,
    name: string,
}
//Handicap interface with name
export interface Handicap {
    id: number,
    name: string,
}
//Handicap interface without name
export interface HandicapID {
    id: number,
}
//Handicap interface without ID
export interface HandicapName {
    name: string,
}
//Method to make a REST API-request to send user data for sign-in purposes.
export const signInUser = async (user: User) => {
    const options = {
        url: `${resourceUrl + authUrl}/signin`,
        headers: {"Content-Type": "application/json"},
        data: JSON.stringify(user)
    }

    return CapacitorHttp.post(options);
}
//Method to make a REST API-request to fetch all user data.
export const getAllUsers = async () => {
    const options = {
        url: `${resourceUrl + authUrl}/getAllUsers`,
        headers: {"Content-Type": "application/json"}
    }

    return CapacitorHttp.get(options)
}
//Method to make a REST API-request to fetch all handicap data.
export const getAllHandicaps = async () => {
    const options = {
        url: `${resourceUrl + userAuth}/getAllHandicaps`,
        headers: {"Content-Type": "application/json"}
    }

    return CapacitorHttp.get(options)
}
//Method to make a REST API-request to set handicap to a user from the user ID.
export const setHandicapByID = async (userId: number, handicapId: number) => {
    const handicapData = ref<HandicapID>({id:0});
    handicapData.value.id = handicapId;

    const options = {
        url: `${resourceUrl + userAuth}/setHandicapToUser/${userId}`,
        headers: {"Content-Type": "application/json"},
        data: JSON.stringify(handicapData.value)//handicapData
    }
    console.log(JSON.stringify(handicapData.value));
    console.log(options.data);
    return CapacitorHttp.post(options);
}
//Method to make a REST API-request to add a handicap name.
export const addHandicap = async (name: string) => {
    const handicapData = ref<HandicapName>({name:''});
    handicapData.value.name = name;
    const options = {
        url: `${resourceUrl + userAuth}/addHandicap`,
        headers: {"Content-Type": "application/json"},
        data: JSON.stringify(handicapData.value)
    }
    console.log(options.data);
    return CapacitorHttp.post(options);
}
//Method to make a REST API-request to fetch all delegations for all users.
export const getAllDelegations = async () => {
    const options = {
        url: `${resourceUrl + evacAuthUrl}/getAllDelegations`,
        headers: {"Content-Type": "application/json"}
    }

    return CapacitorHttp.get(options)
}
//Method to make a REST API-request to set delegation for a user based on the user ID.
export const setDelegationByID = async (id: number, responsibilities: Responsibility) => {
    const options = {
        url: `${resourceUrl + evacAuthUrl}/delegateById/${id}`,
        headers: {"Content-Type": "application/json"},
        data: JSON.stringify(responsibilities)
    }
    console.log(options.data);
    return CapacitorHttp.post(options);
}
//Method to make a REST API-request to get user delegation based on the username.
export const getDelegationsByUsername = async (username: string) => {
    const options = {
        url: `${resourceUrl + evacAuthUrl}/getDelegationsByUsername/` + username,
        headers: {"Content-Type": "application/json"}
    }

    return CapacitorHttp.get(options)
}
//Method to make a REST API-request to set priority to a user from the user ID.
export const setPriorityByID = async (id:number, userPriority: UserPriority) => {
    const options = {
        url: `${resourceUrl + evacAuthUrl}/setPriorityToEvacuationLeader/${id}`,
        headers: {"Content-Type": "application/json"},
        data: JSON.stringify(userPriority)
    }
    console.log(options.data);
    return CapacitorHttp.post(options);
}
//Method to make a REST API-request to get All priorities for the leaders.
export const getAllPriorities = async () => {
    const options = {
        url: `${resourceUrl + evacAuthUrl}/getAllLeadersAndPriorities`,
        headers: {"Content-Type": "application/json"}
    }

    return CapacitorHttp.get(options)
}
//Method to make a REST API-request to get all priority information (such as the priority ID and name).
export const getPriorityInfo = async () => {
    const options = {
        url: `${resourceUrl + evacAuthUrl}/getAllPriorities`,
        headers: {"Content-Type": "application/json"}
    }

    return CapacitorHttp.get(options)
}
//Method to make a REST API-request to Sign Up a user, by sending the user data to the server.
export const signUpUser = async (user: User) => {
    const options = {
        url: `${resourceUrl + authUrl}/signup`,
        headers: {"Content-Type": "application/json"},
        data: JSON.stringify(user)
    }
    console.log(options.data);
    return CapacitorHttp.post(options);
}

//Method to make a REST API-request to set an evacuation leader to active status.
export const setEvacLeaderActive = async (username: string) => {
    const options = {
        url: `${resourceUrl + authUrl}/changeActiveTrue/${username}`,
        headers: {"Content-Type": "application/json"}
    }

    return CapacitorHttp.get(options)
}
//Custom interface to interact with editUsername method below.
export interface USERNAMECONST {
    username: string,
}
//Custom interface to interact with editUserEmail method below.
export interface EMAILCONST {
    email: string,
}
//Custom interface to interact with editUserPassword method below.
export interface PASSWORDCONST {
    password: string,
}
//Custom interface to interact with editUserRole method below.
export interface ROLECONST {
    role: [string],
}
//Method to edit Username by providing the user id and the new username
export const editUserName = async (id: number, username: USERNAMECONST) => {
    const options = {
        url: `${resourceUrl + authUrl}/changeUserNameById/${id}`,
        headers: {"Content-Type": "application/json"},
        data: JSON.stringify(username)
    }
    return CapacitorHttp.put(options);
}
//Method to edit email by providing the user id and the new email
export const editUserEmail = async (id: number, email: EMAILCONST) => {
    const options = {
        url: `${resourceUrl + authUrl}/changeEmailById/${id}`,
        headers: {"Content-Type": "application/json"},
        data: JSON.stringify(email)
    }
    return CapacitorHttp.put(options);
}
//Method to edit password by providing the user id and the new password
export const editUserPassword = async (id: number, password: PASSWORDCONST) => {
    const options = {
        url: `${resourceUrl + authUrl}/changePasswordById/${id}`,
        headers: {"Content-Type": "application/json"},
        data: JSON.stringify(password)
    }
    return CapacitorHttp.put(options);
}
//Method to edit role by providing the user id and the new role
export const editUserRole = async (id: number, role: ROLECONST) => {
    const options = {
        url: `${resourceUrl + authUrl}/changeRoleById/${id}`,
        headers: {"Content-Type": "application/json"},
        data: JSON.stringify(role)
    }
    return CapacitorHttp.put(options);
}
//Method that removes the user, where the user has already been asked for confirmation and asnwered yes, this method sends the user id to the server to be deleted.
export const confirmDeletion = async (id: number) => {
    const options = {
        url: `${resourceUrl + authUrl}/deleteById/` + id,
        headers: {"Content-Type": "application/json"}
    }
    console.log(options);
    return CapacitorHttp.delete(options);
}