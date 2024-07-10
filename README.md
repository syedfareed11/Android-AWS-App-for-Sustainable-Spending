# Android-AWS-App-for-Sustainable-Spending

AWS-powered Android App developed in kotlin that allows users to upload images of bills and helps determine the sustainability of line items in the bill. AWS-S3 is used to store images, AWS-Textract to extract line items and Gemini (through API) to predict sustainability of the line items. AWS DynamoDB was used to for the history feature that allows users to vie previously uploaded bills and their corresponding sustainability information.

## Introduction

This project aims to develop a solution for analyzing sustainable and non-sustainable spending habits by enabling users to scan and digitize their receipts. Leveraging AWS for backend services, Android Studio for mobile application development, Kotlin as the primary programming language, and external APIs for sustainability analysis, users can track their spending habits and make informed decisions to promote sustainable consumption practices.

## Features

- **Receipt Scanning**: Utilize the mobile application to scan physical receipts and convert them into digital format.
- **Sustainable Spending Analysis**: Analyze receipt data to categorize purchases as sustainable or non-sustainable using external APIs.
- **User Authentication**: Ensure secure access to the application with user authentication mechanisms.
- **Cloud Storage**: Utilize AWS services like S3 for storing receipt images securely.
## Technologies Used

- **Android Studio**: Development environment for building the mobile application using Kotlin.
- **AWS (Amazon Web Services)**:
  - **Amazon S3**: Secure cloud storage for receipt images.
  - **Amazon DynamoDB**: NoSQL database for storing user data.
- **External APIs**: Integrate third-party APIs for sustainable spending analysis.
- **Programming Language**: Kotlin.

![image](https://github.com/svkrishna10/Android-AWS-App-for-Sustainable-Spending/assets/92803667/4815273a-8c19-4218-aeae-08474e2e3062)


# UI

<img src="https://github.com/svkrishna10/Android-AWS-App-for-Sustainable-Spending/assets/92803667/c37dfd2e-7d64-4757-aaf6-7a04281f07c0" width=300 height=700/>
<img src="https://github.com/svkrishna10/Android-AWS-App-for-Sustainable-Spending/assets/92803667/e029bdbb-b2d4-4ea8-bb1b-fb19145cac09" width=300 height=700/>
<img src="https://github.com/svkrishna10/Android-AWS-App-for-Sustainable-Spending/assets/92803667/1bc6d257-98e9-48cb-be77-a57cdb985c62" width=300 height=700/>
<img src="https://github.com/svkrishna10/Android-AWS-App-for-Sustainable-Spending/assets/92803667/7fd73422-cde0-4c6e-85df-82bfe775e46b" width=300 height=700/>
<img src="https://github.com/svkrishna10/Android-AWS-App-for-Sustainable-Spending/assets/92803667/1a4be70c-b03a-49e9-a00b-e3aeaf17978a" width=300 height=700/>
