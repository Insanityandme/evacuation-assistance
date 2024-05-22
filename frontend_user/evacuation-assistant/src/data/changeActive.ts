import {CapacitorHttp} from "@capacitor/core";
import {resourceUrl} from "@/data/resourceUrl";

const url = `${resourceUrl}/api/evacAuth`;

/**
 * Changes an evacuations leaders activity to true when
 * the user has answered if that person is available or not
 * @param username
 */
export const changeActiveTrue = async (username: string) => {
    const options = {
        url: `${url}/changeActiveTrue/${username}`,
        headers: {"Content-Type": "application/json"},
        data: JSON.stringify(username)
    }

    return CapacitorHttp.put(options);
}
