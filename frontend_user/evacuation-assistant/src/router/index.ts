import {createRouter, createWebHistory} from '@ionic/vue-router';
import {RouteRecordRaw} from 'vue-router';
import TabsPage from '../views/TabsPage.vue';
import {StorageService} from "@/services/storage.service";

const store = new StorageService();
let role = "";

async function getRole() {
    // call the read method to retrieve the user data
    const userData = await store.read('user');

    if (userData.value !== null) {
        // eslint-disable-next-line
        const userDataParsed = JSON.parse(userData.value!);
        // console.log(userData);
        role = userDataParsed.roles[0];
        console.log("setting role data...");
    }

    return role
}

const routes: Array<RouteRecordRaw> = [
    {
        path: '/',
        redirect: '/login/',
    },
    {
        path: '/tabs/',

        redirect: () => {
            return `/tabs/home/${role}`
        }
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('@/views/LoginPage.vue'),
    },
    {
        path: '/tabs/',
        component: TabsPage,
        children: [

            {
                path: 'home/deputyleader',
                component: () => import('@/views/deputyleader/HomePageDeputyLeader.vue'),
                meta: {role: 'ROLE_DEPUTYLEADER'},
                beforeEnter: async (to, from, next) => {
                    await getRole();
                    // Check if the user is authenticated and has the required role
                    if (role === 'ROLE_DEPUTYLEADER') {
                        next();
                    } else {
                        // Redirect to login page if the user is not authenticated or does not have the required role
                        next('/login');
                    }
                }
            },
            {
                path: 'home/evacleader/note/:answer',
                component: () => import('@/views/evacleader/EvacAvailabilityPage.vue')
            },
            {
                path: 'home/evacleader',
                component: () => import('@/views/evacleader/HomePageEvacLeader.vue'),
                meta: {role: 'ROLE_EVACLEADER'},
                beforeEnter: async (to, from, next) => {
                    await getRole();
                    // Check if the user is authenticated and has the required role
                    if (role === 'ROLE_EVACLEADER') {
                        next();
                    } else {
                        // Redirect to login page if the user is not authenticated or does not have the required role
                        next('/login');
                    }
                }
            },
            {
                path: 'home/user',
                component: () => import('@/views/user/HomePage.vue'),
                meta: {role: 'ROLE_USER'},
                beforeEnter: async (to, from, next) => {
                    await getRole();
                    // Check if the user is authenticated and has the required role
                    if (role === 'ROLE_USER') {
                        next();
                    } else {
                        // Redirect to login page if the user is not authenticated or does not have the required role
                        next('/login');
                    }
                }
            },


            // Deputy Leader
            {
                path: 'tab2/deputyleader',
                component: () => import('@/views/deputyleader/NotificationsPage.vue'),
                meta: {role: 'ROLE_DEPUTYLEADER'},
                beforeEnter: async (to, from, next) => {
                    await getRole();
                    // Check if the user is authenticated and has the required role
                    if (role === 'ROLE_DEPUTYLEADER') {
                        next();
                    } else {
                        // Redirect to login page if the user is not authenticated or does not have the required role
                        next('/login');
                    }
                }
            },
            {
                path: 'tab4/deputyleader',
                component: () => import('@/views/deputyleader/SettingsPage.vue'),
                meta: {role: 'ROLE_DEPUTYLEADER'},
                beforeEnter: async (to, from, next) => {
                    await getRole();
                    // Check if the user is authenticated and has the required role
                    if (role === 'ROLE_DEPUTYLEADER') {
                        next();
                    } else {
                        // Redirect to login page if the user is not authenticated or does not have the required role
                        next('/login');
                    }
                }
            },

            //Evacuation Leader
            {
                path: 'tab2/evacleader',
                component: () => import('@/views/evacleader/NotificationsPage.vue'),
                meta: {role: 'ROLE_EVACLEADER'},
                beforeEnter: async (to, from, next) => {
                    await getRole();
                    // Check if the user is authenticated and has the required role
                    if (role === 'ROLE_EVACLEADER') {
                        next();
                    } else {
                        // Redirect to login page if the user is not authenticated or does not have the required role
                        next('/login');
                    }
                }
            },
            {
                path: 'tab4/evacleader',
                component: () => import('@/views/evacleader/SettingsPage.vue'),
                meta: {role: 'ROLE_EVACLEADER'},
                beforeEnter: async (to, from, next) => {
                    await getRole();
                    // Check if the user is authenticated and has the required role
                    if (role === 'ROLE_EVACLEADER') {
                        next();
                    } else {
                        // Redirect to login page if the user is not authenticated or does not have the required role
                        next('/login');
                    }
                }
            },

            // User
            {
                path: 'tab2/user',
                component: () => import('@/views/user/ScanningPage.vue'),
                meta: {role: 'ROLE_USER'},
                beforeEnter: async (to, from, next) => {
                    await getRole();
                    // Check if the user is authenticated and has the required role
                    if (role === 'ROLE_USER') {
                        next();
                    } else {
                        // Redirect to login page if the user is not authenticated or does not have the required role
                        next('/login');
                    }
                }
            },

            {
                path: 'tab4/user',
                component: () => import('@/views/user/SettingsPage.vue'),
                meta: {role: 'ROLE_USER'},
                beforeEnter: async (to, from, next) => {
                    await getRole();
                    // Check if the user is authenticated and has the required role
                    if (role === 'ROLE_USER') {
                        next();
                    } else {
                        // Redirect to login page if the user is not authenticated or does not have the required role
                        next('/login');
                    }
                }
            },
        ]
    },
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})
export default router