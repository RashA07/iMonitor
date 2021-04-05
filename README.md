# iMonitor
DSC Global Solution Challenge 2021 Project

Youtube Link: https://youtu.be/TEH-ciudb-8

### To run code
Run emulator on Android Studio with minimum android version 26 installed

Note: 
In order to add a new Patient, it would require a new registered Arduino Device. Since we cannot provide a device for this matter, we invite you to use an existing device under the login: 

(case sensitive)
Username: Aqeel  
Number: 1234

## Why we decided on topic
As Covid is prevalent and highlighted many issues in the healthcare system in recent years, we started to notice that the reason that low income families were drastically affected by the pandemic is because they do not have the proper equipment or knowledge to keep track on their health issues, specifically Covid. Most of these peoples need to engage in daily labour for their survival and couldn’t take care of their dependents while providing for their families. Thus, we are choosing this topic to hopefully resolve the issue.



## Who we are
We are Computer Science undergraduates from Monash University Malaysia, taking part in the DSC MUM club as well. We formed the team in mid-February and began to work on the challenge.



## What we built and our SDG
We built an Health Monitoring app on Android that paired with a device containing arduino sensors, essentially the device will measure the health data of the patient and the app allows 
the patient to monitor their own health measurement constantly, meanwhile alerting them when the readings reach dangerous ranges. There is an option for patients to connect with 
caretakers or clinics nearby to share their health data with them. Calling or messaging caretakers or clinics are also available if the patient wants some enquiries on their health. 
Once the connection between caretaker and patient is formed, caretaker is allowed to monitor their sick dependents remotely and also be notified if patient's reading goes dangerous. 
Although there existed similar apps prior to this, our main priorities are to lower the cost of the sensors, lower the data usage and also the battery usages of devices. 

With this app, we hope to resolve the difficulties as stated in SDG Goal 3 (Good Health and Wellbeing) by providing a handy health monitoring service and SDG Goal 10 (Reduced Inequalities) 
by making the service affordable for low income families.


## Extensions
Below are the features that can be added in our app as extensions
- More disease detection besides covid, as sensor thresholds can be customized
- Additional sensors can be added to allow more health data types
- Patient health data can be shared for expert analysis
- Create a model to predict symptoms and patients in risk with anonymized data


## Firebase Structure:
```
|--SENSOR OBJECT
 |--"ARDUINO" :
    |--"data" :
      |--"-DATA OBJECT" :
        |--"blood_oxygen",
        |--"date",
        |--"heart_rate",
        |--"id",
        |--"temperature",
        |--"time",
        
 |--"Clinics" : 
    |--"-CLINIC OBJECT" : 
      |--"name",
      |--"patients":
        |--"-PATIENT OBJECT",
      |--"phoneNo",
      |--"status",

 |--"Patients" :
    |--"PATIENT OBJECT" :
      |--"clinics" : 
        |--"CLINIC OBJECT",
      |--"details" :
        |--"name",
        |--"phoneNo",
      |--"device" :
        |--"name",
        |--"status",
								
```
