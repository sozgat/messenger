# Armut Messaging Service

## Installation Guide

```bash
git clone https://github.com/sozgat/messenger.git
cd messenger
docker-compose up
```

## Endpoint List

* [User Services](#user-services)
  * [Singup](#1-singup)
  * [Get All Users](#2-get-all-users)
  * [Login](#3-login)
  
* [Message Services](#message-services)
  * [Send Message](#send-message)
  * [Get Messaging User List](#get-messaging-user-list)
  * [Get Messages Between Two Users](#get-messages-between-two-user)

* [Blocking Service](#blocking-service)
  * [Block User](#block-user)

## User Services
User information management and check user info 

### 1. Singup

Singup with username and password


***Endpoint:***

```bash
Method: POST
Type: RAW
URL: http://localhost:8080/api/v1/user
```

***Body:***

```js        
{
    "username":"armut",
    "password":"123456"
}
```


***Response:***

```js        
{
    "status": "success",
    "phrase": "OK",
    "timestamp": "06-09-2021 02:04:11",
    "data": {
        "username": "armut",
        "token": null,
        "tokenExpiryDate": null
    }
}
```

### 2. Get All Users

You can list users. 

***Endpoint:***

```bash
Method: GET
URL: http://localhost:8080/api/v1/user
```

***Response:***

```js        
{
    "status": "success",
    "phrase": "OK",
    "timestamp": "06-09-2021 02:05:33",
    "data": [
        {
            "username": "said",
            "token": "99e167e2-3ea7-4164-acd6-f55b9f9fe89b",
            "tokenExpiryDate": "2021-10-05T21:58:21"
        },
        {
            "username": "armut",
            "token": null,
            "tokenExpiryDate": null
        }
    ]
}
```

### 3. Login


Login with username and password


***Endpoint:***

```bash
Method: POST
Type: RAW
URL: http://localhost:8080/api/v1/user/login
```

***Body:***

```js        
{
    "username":"armut",
    "password":"123456"
}
```

***Response:***

```js        
{
    "status": "success",
    "phrase": "OK",
    "timestamp": "06-09-2021 02:06:55",
    "data": {
        "username": "armut",
        "token": "9fb0177b-ed3f-41d9-902e-38dc8afe35cb",
        "tokenExpiryDate": "2021-10-05T23:06:55.562"
    }
}
```

## Message Services
Messages management services. You have to use Bearer Token for authorization and every message API in Postman.

### 1. Send Message

Send message with toUsername and yourMessage

***Endpoint:***

```bash
Method: POST
Type: RAW
URL: http://localhost:8080/api/v1/message
```

***Header:***

```js        
Authorization:Bearer 9fb0177b-ed3f-41d9-902e-38dc8afe35cb
```

***Body:***

```js        
{
	"toUsername":"said",
	"yourMessage":"hi said, i'm armut :)"
}
```


***Response:***

```js        
{
    "status": "success",
    "phrase": "OK",
    "timestamp": "06-09-2021 02:18:01",
    "data": {
        "fromUsername": "armut",
        "toUsername": "said",
        "yourMessage": "hi said, i'm armut :)",
        "messageCreatedTime": "2021-09-05T23:18:01.561"
    }
}
```

### 2. Get Messaging User List

List of your messaging users

***Endpoint:***

```bash
Method: GET
Type: RAW
URL: http://localhost:8080/api/v1/message/userlist
```

***Header:***

```js        
Authorization:Bearer 9fb0177b-ed3f-41d9-902e-38dc8afe35cb
```

***Response:***

```js        
{
    "status": "success",
    "phrase": "OK",
    "timestamp": "06-09-2021 02:22:50",
    "data": [
        "said"
    ]
}
```

### 3. Get Messages Between Two Users

You can get all messages between auth user and user who auth user is messaging.

***Endpoint:***

```bash
Method: POST
Type: RAW
URL: http://localhost:8080/api/v1/message/mymessages
```

***Header:***

```js        
Authorization:Bearer 9fb0177b-ed3f-41d9-902e-38dc8afe35cb
```

***Body:***

```js        
{
	"username":"said"
}
```


***Response:***

```js        
{
    "status": "success",
    "phrase": "OK",
    "timestamp": "06-09-2021 02:24:17",
    "data": [
        {
            "fromUsername": "armut",
            "toUsername": "said",
            "yourMessage": "hi said, i'm armut :)",
            "messageCreatedTime": "2021-09-05T23:18:02"
        }
    ]
}
```

## Blocking Service
You can block the user you don't want to receive messages from.

### 1. Block User

***Endpoint:***

```bash
Method: POST
Type: RAW
URL: http://localhost:8080/api/v1/block
```

***Header:***

```js        
Authorization:Bearer 9fb0177b-ed3f-41d9-902e-38dc8afe35cb
```

***Body:***

```js        
{
	"blockedUsername":"said"
}
```


***Response:***

```js        
{
    "status": "success",
    "phrase": "OK",
    "timestamp": "06-09-2021 02:30:49",
    "data": {
        "blockingUsername": "armut",
        "blockedUsername": "said"
    }
}
```