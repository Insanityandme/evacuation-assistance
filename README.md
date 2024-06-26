<div align="center">
	
# Evacuation Assistance

[Install](#install) • [Animations](#animations) • [Screenshots](#screenshots)

<img src="https://github.com/Insanityandme/evacuation-assistance/assets/1380257/f78eaeb9-2795-4209-b0c4-08bb4fcc3de2" width="250" height="500"/> 
<img src="https://github.com/Insanityandme/evacuation-assistance/assets/1380257/2b2656c7-97ec-409a-9243-03aad5b6ecd4" width="250" height="500"/>
<img src="https://github.com/Insanityandme/evacuation-assistance/assets/1380257/e4f211ab-165e-4b18-97cb-80295b525f82" width="250" height="500"/>
</div>

---
### Table of Contents
- [Introduction](#introduction)
- [Installation](#install)
	- [Back-end](#back-end)
	- [Front-end User](#front-end-user)
	- [Front-end Admin](#front-end-admin)
- [Android](#android)
- [iOS](#ios)
- [Contact](#contact)

# Introduction
Evacuation assistance is a product designed to make evacuations easier by
creating a smartphone application that can help users involved in an evacuation
communicate better and faster. 

We used bluetooth positioning sensors for indoor positioning.

Note that this is a student project and was used as a learning exercise (at least the first three months of this project was!).

A bunch of new technologies were used such as: Postgres, Vue, Ionic, Java Spring and many more!

# Install
```git clone https://github.com/Insanityandme/evacuation-app.git```

Our application is divided into three seperate parts:

+ backend-evacuation
+ frontend-user
+ frontend-admin

Read our installation guides below to be walked through installing and configuring Evacuation Assistance. 

---
## Back-end
1. Download postgresql server: https://www.postgresql.org/download/
2. Run the postgresql server
3. Open pgadmin and create a database with the name evacdb
4. Create a file named application.properties in: backend-evacuation/src/main/resources with the following code with your postgres username and password:

```
spring.datasource.url = jdbc:postgresql://localhost:5432/evacdb
spring.datasource.username = (insert postgres username)
spring.datasource.password = (insert postgres password)

server.port = 8081

spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation = true
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.PostgreSQLDialect

# Hibernate ddl auto (create, create-drop, validate, update) 
spring.jpa.hibernate.ddl-auto = create # change to update for data to persist

# App Properties
bezkoder.app.jwtSecret = evacSecretKey
bezkoder.app.jwtExpirationMs = 86400000
```

### Enable firebase push notifications (backend)
1. Create an account at https://firebase.google.com/
2. Register an android project into your firebase account
3. Download the google-services.json file and then rename the config file privateKey.json 
4. Put it in the directory: backend-evacuation/src/main/resources

### Running the server
1. Download maven CLl: https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html
2. cd into root folder backend-evacuation
3. Start the server by typing: 
	``mvn spring-boot:run`` 
3. If everything has gone well the server should be running on port 8081.

---
## Front-end User
1. Start by cd into frontend_user/evacuation-assistant
2. run ``npm install`` if you don't have npm: https://nodejs.org/en/download
3. create a file called resourceUrl.ts in frontend_user/evacuation-assistant/src/data and include this:

``export const resourceUrl = 'http://localhost:8081'`` 
5. install ionic: 
    ``npm install -g @ionic/cli``
6. install capacitor in the root of your app
```
npm i @capacitor/core
npm i -D @capacitor/cli
```
7.  run ``ionic serve`` to see it live hosted locally in your browser of choice
8.  NOTE:  it should primarily be used on smartphones and developed there (instructions below)

---
## Front-end Admin
1. cd into evacuation-assistant/frontend_admin
2. run:  ``npm run install``
3. create a file called resourceUrl.ts in frontend_admin/evacuation-assistant/src/data and include this: \
   ``export const resourceUrl = 'http://localhost:8081'`` 
5. run ionic serve and you're good to go!
---

## Android
### Prerequisites: https://developer.android.com/studio
1. start by running ``ionic cap add android`` in frontend_user/evacuation-assistant
2. run ``ionic build`` (builds the whole application to android)
3. run ``ionic cap copy``  (ensure local web assets gets into the android folder)
4. run ``ionic cap sync``  (ensure plugins are added)
5. run ``ionic cap open android`` (opens android studio)
6. Connect smartphone of choice and install on your phone
7. If everything has been correctly installed you should have a device running on your phone!

### Firebase push notifications with custom sound (android)
1. when you have built your android application in ionic
2. download the google-services.json file again from your firebase android project
3. put it in the directory evacuation-assistant/android/app
4. create a directory called raw in evacuation-assistant/android/app/src/main/res
5. add the file custom.mp3 into this folder (which I have given you in a folder called android_assets)
6. open android studio and add this code into AndroidManifest.xml: 
```
<!-- Request permission to display notifications -->  
<uses-permission android:name="android.permission.INTERNET" />  
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />  
<uses-permission android:name="android.permission.VIBRATE" />  
  
<!-- Use a custom sound for notifications -->  
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />  
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />  
<uses-permission android:name="android.permission.MEDIA_CONTENT_CONTROL"  
    tools:ignore="ProtectedPermissions" />  
<uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" />
```
7. in build.gradle (android) remove everything and copy this: 
```
  repositories {  
        google()  
        mavenCentral()  
    }  
    dependencies {  
        classpath 'com.android.tools.build:gradle:8.0.0'  
        classpath 'com.google.gms:google-services:4.3.15'  
  
        // NOTE: Do not place your application dependencies here; they belong  
        // in the individual module build.gradle files    }  
}  
  
apply from: "variables.gradle"  
  
allprojects {  
    repositories {  
        google()  
        mavenCentral()  
    }  
}  
  
task clean(type: Delete) {  
    delete rootProject.buildDir  
}
```   
8. in build.gradle (Module: app) add this code to dependencies: 
```
implementation platform('com.google.firebase:firebase-bom:32.0.0')
```
9. Also add this right below the depencencies: 
```
try {  
    def servicesJSON = file('google-services.json')  
    if (servicesJSON.text) {  
        apply plugin: 'com.google.gms.google-services'  
    }  
} catch(Exception e) {  
    logger.info("google-services.json not found, google-services plugin not applied. Push Notifications won't work")  
}
```
---
## iOS
1. npm install
2. ionic build
3. ionic capacitor add ios
4. ionic capacitor sync ios
5. ionic capacitor open ios
6. Follow the tutorial in this link:
	1. https://ionicframework.com/docs/developing/ios
		1. When Xcode is opened, select the project root, in this case its called 'App'.
		2. Be sure to have logged in to your apple developer account in the settings pane.
			3. Press CMD+, to open settings pane.
			4. in the 'Accounts' tab, if not accounts have been added, press the '+' button to add your apple id that is registered with the Apple developer account which requires an annual fee (this is only required to make sure that the Push Notification feature works).
			5. After signing in, press on 'Download Manual Profiles' button followed by pressing on 'Manage Certificates' button, then press the '+' icon and select 'Apple Development'.
		3. Go to 'Signing & Capabilities' tab, select in the 'team' field your developer account.
		4. If not already registered, press 'register device' to register your device in your developer account.
		5. Make sure the 'Signing Certicate' section says: 'Apple Development: ...'
7. Follow the tutorial in this link:
	1. https://ionicframework.com/docs/native/push-notifications
		1. Go to 'AppDelegate.swift' and add the following lines of code:
``` func application(_ application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data) {
  NotificationCenter.default.post(name: .capacitorDidRegisterForRemoteNotifications, object: deviceToken)
}

func application(_ application: UIApplication, didFailToRegisterForRemoteNotificationsWithError error: Error) {
  NotificationCenter.default.post(name: .capacitorDidFailToRegisterForRemoteNotifications, object: error)
}

```
8. Follow the tutorial in this link:
	1. https://capacitorjs.com/docs/v3/ios/configuration?_gl=1*e2v9xu*_ga*ODkyMDUxMTkwLjE2ODM5NjUyMTg.*_ga_REH9TJF6KF*MTY4NTg5NzU5MC4zMS4xLjE2ODU4OTg1NTcuMC4wLjA.#setting-capabilities
		1.  Go to 'Signing & Capabilities' tab, select the '+ Capability' button and choose 'Push Notifications'
9. Follow the tutorial in this link:
	1. https://firebase.google.com/docs/cloud-messaging/ios/first-message
		1. Create a firebase account
		2. Register an iOS app with Firebase
		3. Download the GoogleService-Info.plist
		4. Place it in the Xcode App directory or `\evacuation-assistance\ios\App\App\GoogleService-Info.plist`
		5. Upload the APNs authentication key to the Firebase and provide the 'Key ID' and 'Team ID'.
		6. Follow the 'Add Firebase SDK' section followed by adding ```import FirebaseCore  
										import FirebaseFirestore  
										import FirebaseAuth'``` lines to the AppDelegate File.
10. In the Info.plist file, add the following keys and values to allow for bluetooth functionality:
	1. UIViewControllerBasedStatusBarAppearance
		1. true
	2. NSBluetoothAlwaysUsageDescription
		1. Uses Bluetooth to connect and interact with peripheral BLE devices.
	3. UIBackgroundModes
		1. bluetooth-central
11. Now with every code change, re-run ionic build followed by ionic capacitor sync ios, then in Xcode build and run the project on your device.
12. Don't forget to add the App Icon to the Assets folder.
13. Don't forget to add the Sound file to the App's main directory for the custom notification sound to work properly.

# Animations
## When an alarm goes off in a building &emsp;&emsp;&emsp; Evacuation Leader View
<table>
  <tr>
    <td><img src="https://github.com/Insanityandme/evacuation-assistance/assets/1380257/9c270d2f-1f41-4fd9-8d47-78d807830aef"  alt="Alert" width=250px height=500px ></td> 
    <td>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</td>
    <td><img src="https://github.com/Insanityandme/evacuation-assistance/assets/1380257/12d00160-3141-428f-b606-329ce4a6a641" alt="Evac" width=250px height=500px></td>
  </tr>
</table>

## Deputy Leader View &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; User View
<table>
  <tr>
    <td><img src="https://github.com/Insanityandme/evacuation-assistance/assets/1380257/6baec89a-27aa-4a81-afec-c0ba0863e323"  alt="Deputy" width=250px height=500px ></td> 
    <td>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</td>
    <td><img src=https://github.com/Insanityandme/evacuation-assistance/assets/1380257/6fb9cd6e-3b90-4557-a4a4-896b3701cffb" alt="User" width=250px height=500px></td>
  </tr>
</table>

---
# Screenshots
<img src="https://github.com/Insanityandme/evacuation-assistance/assets/1380257/54b218cd-c20c-47e3-a21a-775b0feb1b7e" width="250px" height="500px"/>
<img src="https://github.com/Insanityandme/evacuation-assistance/assets/1380257/fb4d9d37-365a-4410-bbf5-b81266e7edc8" width="250px" height="500px"/>
<img src="https://github.com/Insanityandme/evacuation-assistance/assets/1380257/3ecb649c-62c7-40a6-ad64-261e7e035668" width="250px" height="500px"/>
<img src="https://github.com/Insanityandme/evacuation-assistance/assets/1380257/b49c0c29-3386-4539-a517-d55cbe870f58" width="250px" height="500px"/>
<img src="https://github.com/Insanityandme/evacuation-assistance/assets/1380257/6c988eb1-c567-4401-a5ca-cc1cca6ecd9c" width="250px" height="500px"/>
<img src="https://github.com/Insanityandme/evacuation-assistance/assets/1380257/94fb30df-c001-4506-97cf-d93a36aeb1a4" width="250px" height="500px"/>
<img src="https://github.com/Insanityandme/evacuation-assistance/assets/1380257/65e611ae-b713-4048-a36f-bac2e104212e" width="250px" height="500px"/>
<img src="https://github.com/Insanityandme/evacuation-assistance/assets/1380257/f048833d-ced4-4e49-8206-6c8323797562" width="250px" height="500px"/>
<img src="https://github.com/Insanityandme/evacuation-assistance/assets/1380257/528088e3-4043-491c-bf3c-a5c85fba6f84" width="250px" height="500px"/>
<img src="https://github.com/Insanityandme/evacuation-assistance/assets/1380257/0bc1102e-d226-4440-8623-68cf868d5e7c" width="250px" height="500px"/>
<img src="https://github.com/Insanityandme/evacuation-assistance/assets/1380257/3f11a3c8-d758-4ebf-9eff-96d628b04e72" width="250px" height="500px"/>
<img src="https://github.com/Insanityandme/evacuation-assistance/assets/1380257/3ca23922-34c0-432c-9144-efab0df58374" width="250px" height="500px"/>
<img src="https://github.com/Insanityandme/evacuation-assistance/assets/1380257/84fb1b25-6c94-42bb-8526-926c650bbb08" width="250px" height="500px"/>
<img src="https://github.com/Insanityandme/evacuation-assistance/assets/1380257/30177b4c-ff50-47e8-bd61-1bd78942a82c" width="250px" height="500px"/>
<img src="https://github.com/Insanityandme/evacuation-assistance/assets/1380257/8760e4cf-919f-44e3-b75c-76a65c8c4bea" width="250px" height="500px"/>
<img src="https://github.com/Insanityandme/evacuation-assistance/assets/1380257/bf4d0006-3e01-4681-8dc0-dea85f01e3b9" width="250px" height="500px"/>
<img src="https://github.com/Insanityandme/evacuation-assistance/assets/1380257/a3504e28-0ea2-4f16-a84f-78ce1d4c602b" width="250px" height="500px"/>
<img src="https://github.com/Insanityandme/evacuation-assistance/assets/1380257/b842484f-e17c-4b08-9c51-c5befe88df8b" width="250px" height="500px"/>

---
# Contact
You are welcome to contact me at bengtegardbook@gmail.com if you have any questions on how to setup this environment.
There might be some mistakes in our installation guide and I apologize for that if that's the case.
