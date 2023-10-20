# travel-agency

## Tools Required
* Java 17
* Gradle
* IntelliJ IDEA (preferred)

## setup (IntelliJ IDEA)

1) Create New project from Version control (File -> New -> Project from Version Control). Choose Git as version control and paste URL:       
   https://github.com/storybehind/travel-agency/

2) Add Configuration.
   
   2.1) Choose Add New Configuration -> Application

   2.2) In Build andd Run section, choose Java SDK 17, provide classpath and main class ('org.example.TravelAgency')

   2.3) Click Apply and run the application

## demo

1) ### Print itinerary of the travel package

   ```
   curl --location 'http://localhost:8080/travel/1'
   ```

   ```
   {
        "status": "success",
        "message": null,
        "data": {
            "travelPackageName": "package 1",
            "itineraries": [
                {
                    "itineraryName": "itinerary 1",
                    "activities": [
                        {
                            "name": "activity 1",
                            "description": "Activity 1",
                            "cost": 10.00,
                            "capacity": 3
                        },
                        {
                            "name": "activity 2",
                            "description": "Activity 2",
                            "cost": 20.00,
                            "capacity": 3
                        }
                    ]
                },
                {
                    "itineraryName": "itinerary 2",
                    "activities": [
                        {
                            "name": "activity 3",
                            "description": "Activity 3",
                            "cost": 30.00,
                            "capacity": 3
                        },
                        {
                            "name": "activity 4",
                            "description": "Activity 4",
                            "cost": 10.00,
                            "capacity": 3
                        }
                    ]
                },
                {
                    "itineraryName": "itinerary 3",
                    "activities": [
                        {
                            "name": "activity 5",
                            "description": "Activity 5",
                            "cost": 10.00,
                            "capacity": 2
                        }
                    ]
                }
            ]
         }
    }
   ```

2) ### Sign Up one or more activities

    ```
        curl --location 'http://localhost:8080/travel/signup' \
        --header 'Content-Type: application/json' \
        --data '{
            "travelPackageId": 1,
            "passengerNumber": 1,
            "activityIds": [1,3]
        }'
    ```

    ```
    {
        "status": "success",
        "message": null,
        "data": {
            "activityResponseModels": [
                {
                    "activityId": 1,
                    "activityName": "activity 1",
                    "costPaid": 10.00
                },
                {
                    "activityId": 3,
                    "activityName": "activity 3",
                    "costPaid": 30.00
                }
            ],
            "balance": 10.00
        }
    }
    ```

3) ### Print the passenger list of the travel package

   ```
     curl --location 'http://localhost:8080/travel/1/passengers'
   ```

   ```
     {
        "status": "success",
        "message": null,
        "data": {
            "travelPackageName": "package 1",
            "passengerCapacity": 3,
            "passengersEnrolled": 1,
            "passengers": [
                {
                    "passengerNumber": 1,
                    "name": "Passenger 1"
                }
            ]
        }
    }
   ```
  
4) #### Print the details of an individual passenger

   ```
   curl --location 'http://localhost:8080/passenger/1'
   ```

   ```
   {
        "status": "success",
        "message": null,
        "data": {
            "name": "Passenger 1",
            "passengerNumber": 1,
            "balance": 10.00,
            "activities": [
                {
                    "activityName": "activity 1",
                    "travelPackageName": "package 1",
                    "pricePaid": 10.00
                },
                {
                    "activityName": "activity 3",
                    "travelPackageName": "package 1",
                    "pricePaid": 30.00
                }
            ]
        }
    }
   ```

5) ### Print the details of all the activities that still have spaces available, including how many spaces are available.

   ```
   curl --location 'http://localhost:8080/activity'
   ```

   ```
   {
        "status": "success",
        "message": null,
        "data": [
            {
                "name": "activity 1",
                "description": "Activity 1",
                "cost": 10.00,
                "capacity": 2
            },
            {
                "name": "activity 2",
                "description": "Activity 2",
                "cost": 20.00,
                "capacity": 3
            },
            {
                "name": "activity 3",
                "description": "Activity 3",
                "cost": 30.00,
                "capacity": 2
            },
            {
                "name": "activity 4",
                "description": "Activity 4",
                "cost": 10.00,
                "capacity": 3
            },
            {
                "name": "activity 5",
                "description": "Activity 5",
                "cost": 10.00,
                "capacity": 2
            },
            {
                "name": "activity 6",
                "description": "Activity 6",
                "cost": 20.00,
                "capacity": 1
            },
            {
                "name": "activity 7",
                "description": "Activity 7",
                "cost": 10.00,
                "capacity": 2
            }
        ]
    }
   ```




