import {Preferences} from '@capacitor/preferences';

/**
 * This class is responsible for methods
 * to store and retrieve user data.
 * We are using the Preferences plugin in Capacitor
 * for this to work for both iOS and Android.
 */
export class StorageService {
    async create(key: string, value: any) {
        await Preferences.set({key, value});
        console.log("Creating a new key-value pair...")
    }

    async read(key: string) {
        console.log("Retreiving a key-value pair....");
        return (await Preferences.get({key}));
    }

    async update(key: string, value: any) {
        await Preferences.set({key, value});
        console.log("Updating a key-value pair....")
    }

    async delete(key: string) {
        await Preferences.remove({key});
        console.log("Deleting a key-value pair...")
    }

    async clear() {
        await Preferences.clear();
        console.log("Clearing local storage...")
    }


}
