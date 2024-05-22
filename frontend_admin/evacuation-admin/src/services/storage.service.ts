import {Preferences} from '@capacitor/preferences';

/**
 * StorageService is used to save some user data locally on the user's phone.
 * Some useful methods are to create a key and value, read/retrieve a key-value pair,
 * update to update key-value, delete to delete key-value and clear to empty the entire storage.
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
