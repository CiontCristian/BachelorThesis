# BachelorThesis - Job Recruitment with Intelligent Recommender Engine

## Technologies
- The application is composed of four main components. One server is implemented in python and deals with the recommendation engine, machine learning and processing of the dataset. The second server is implemented in Java, using spring boot and covers all the remaining functionalities related to E-recruitment. A shared PostgreSQL database between the two servers is storing all the data received from the Angular client and from the dataset.
-	The first server is implemented in Python because of the extensive libraries and frameworks that it provides for machine learning. It uses Pandas for selecting only the jobs from the IT domain in the dataset, as well for eliminating the columns that the recommendation engine does not require. All the data extracted from the dataset is vectorized and then normalized using algorithms from Scikit-learn framework. Then it is processed using the NumPy library for greater efficiency and high-performance computing time. To communicate with the frontend client, it utilizes REST API calls from the Flask framework. All the information necessary in the recommendation system is taken from the PostgreSQL database using normal queries.
-	The second server is implemented in Java using the Spring Boot framework and it handles all the functionalities that are not related to the recommendation engine. Every interaction with the PostgreSQL database is handled though Hibernate.
-	The frontend uses the Angular framework, and it communicates through requests with both servers, depending on the context. For achieving a more responsive and interactive UI (User Interface), the Angular Material library was used alongside some refining from HTML and CSS. The AGM (Angular Google Maps) library was used to give the users’ the ability to interact with the world map and the Ngx-Charts library to render and animate some statistics into visual graphs/charts.


## Application flow

![image](https://user-images.githubusercontent.com/61873468/140403659-008ecac1-f61d-4d36-b2d1-cbf5fc5e4306.png)

## Requirements

The application represents an online environment that allows jobseekers to easily browse through various offers to find a suitable job and companies to recruit in an organized and efficient manner. It also incorporates an intelligent system to further enhance the user experience by providing similar job opportunities to his preferences. Thus, there are mainly two types of users, clients/jobseekers, and organizations/companies. In addition, there is a third type of user, the administrator who has full privileges over the entire software.
	Some of the available functionalities:
-	Visualize/browse available job offers.
-	Search/Filter jobs on various criteria.
-	Like/Dislike an offer.
-	Apply to jobs.
-	Visualize/browse similar jobs to a specific one.
-	Login / Change profile settings.
-	Get recommendations based on your personal background and the ratings you give.
-	Visualize the distance in kilometers between you and the headquarters of the organization the current job opening belongs to (if both you and the company have marked their locations on the map).
-	Enlist your organization / Modify organization data.
-	Post job openings / Manage the details of your own offers / Remove applications.
-	Visualize candidates and their information that applied to your openings.
-	Search/Filter jobs on various criteria.  
-	Visualize various charts/graphs with statistics.
