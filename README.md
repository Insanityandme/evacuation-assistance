<div align="center">
	
# Evacuation Assistance

[Install](#install) • [Animations](#animations) • [Screenshots](#screenshots)

<img src="https://github.com/Insanityandme/evacuation-app/assets/1380257/9a86799e-c340-4dd5-a801-df85c15e177f" width="250" height="500"/> 
<img src="https://github.com/Insanityandme/evacuation-app/assets/1380257/2523238d-edfa-4ee5-8e60-05545d99b775" width="250" height="500"/>
<img src="https://github.com/Insanityandme/evacuation-app/assets/1380257/529eeff8-8714-4af3-aa88-d681341d0fd0" width="250" height="500"/>
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
    <td><img src="https://github.com/Insanityandme/evacuation-app/assets/1380257/8352bbed-7954-43fb-8a35-171e37705cec"  alt="Alert" width=250px height=500px ></td> 
    <td>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</td>
    <td><img src="https://github.com/Insanityandme/evacuation-app/assets/1380257/88f31aea-0521-4bd8-bd7c-322b9b71ff96" alt="Evac" width=250px height=500px></td>
  </tr>
</table>

## Deputy Leader View &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; User View
<table>
  <tr>
    <td><img src="https://github.com/Insanityandme/evacuation-app/assets/1380257/508a406c-abce-41a6-b19f-da0f15c82a00"  alt="Deputy" width=250px height=500px ></td> 
    <td>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</td>
    <td><img src="https://github.com/Insanityandme/evacuation-app/assets/1380257/2e15666a-9210-445f-a7f7-7c457f519388" alt="User" width=250px height=500px></td>
  </tr>
</table>

---
# Screenshots
<img src="https://github.com/Insanityandme/evacuation-app/assets/1380257/6a88b7c0-5684-4f6e-bada-822122da5751" width="250px" height="500px"/>
<img src="https://github.com/Insanityandme/evacuation-app/assets/1380257/bcd74ea8-c594-46b7-999e-db4822cce344" width="250px" height="500px"/>
<img src="https://github.com/Insanityandme/evacuation-app/assets/1380257/e9fa19a2-12e8-4eda-9c10-9d1f84772384" width="250px" height="500px"/>
<img src="https://github.com/Insanityandme/evacuation-app/assets/1380257/ee28b4f3-852a-457e-95f3-629e7b7d5370" width="250px" height="500px"/>
<img src="https://github.com/Insanityandme/evacuation-app/assets/1380257/bc83d1a7-c934-4ef5-89a6-599e6d7cf26f" width="250px" height="500px"/>
<img src="https://github.com/Insanityandme/evacuation-app/assets/1380257/09790778-c166-4ec4-8e72-930f47c5f28e" width="250px" height="500px"/>
<img src="https://github.com/Insanityandme/evacuation-app/assets/1380257/ad4b20bb-271a-4810-991d-71a5733afc00" width="250px" height="500px"/>
<img src="https://github.com/Insanityandme/evacuation-app/assets/1380257/a38fe5c8-4234-4fd2-abbc-4ee11eb8372d" width="250px" height="500px"/>
<img src="https://github.com/Insanityandme/evacuation-app/assets/1380257/a818ed33-3191-484d-be95-1f3c297126c7" width="250px" height="500px"/>
<img src="https://github.com/Insanityandme/evacuation-app/assets/1380257/e87a9bef-6575-45e7-9c8f-5092d1e9a74d" width="250px" height="500px"/>
<img src="https://github.com/Insanityandme/evacuation-app/assets/1380257/eb268834-a44e-47b5-8073-855e934ffc9b" width="250px" height="500px"/>
<img src="https://github.com/Insanityandme/evacuation-app/assets/1380257/5255eef5-b1a8-4d29-a5b7-b22b3584d7ac" width="250px" height="500px"/>
<img src="https://github.com/Insanityandme/evacuation-app/assets/1380257/e9bd89a1-299c-4b9e-a9ea-ddef501991d9" width="250px" height="500px"/>
<img src="https://github.com/Insanityandme/evacuation-app/assets/1380257/4521e13f-597c-407e-a320-68505ea49829" width="250px" height="500px"/>
<img src="https://github.com/Insanityandme/evacuation-app/assets/1380257/b2d22496-1a15-490b-89d5-85577f095c21" width="250px" height="500px"/>
<img src="https://github.com/Insanityandme/evacuation-app/assets/1380257/f8443f18-c833-4ca1-975b-3e7a45cea87e" width="250px" height="500px"/>
<img src="https://github.com/Insanityandme/evacuation-app/assets/1380257/912d9fd5-3e6d-4eaa-b822-e25b8dcd6649" width="250px" height="500px"/>
<img src="https://github.com/Insanityandme/evacuation-app/assets/1380257/d71630eb-ad0e-4d23-8e28-59d4f7620550" width="250px" height="500px"/>

---
# Contact
You are welcome to contact me at bengtegardbook@gmail.com if you have any questions on how to setup this environment.
There might be some mistakes in our installation guide and I apologize for that if that's the case.
