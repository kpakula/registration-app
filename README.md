# Registration app

## Requirements
* Java 11+
* Maven 3.6.3+
* Node 16.13.1+
* NPM 8.3.2+

## How to run backend - Spring
1. In project root directory execute:

```bash
mvn spring-boot:run
```

2. App should be available under:
```bash
localhost:8080
```

## How to run frontend - React
1. Move from root to webapplication directory and execute: 
```bash
cd src/webapplication/
npm install
npm start
```
2. App should be available under:
```bash
localhost:3000
```

# Endpoint - localhost:8080
```bash
POST: /api/user

{
    "username": "",
    "password": ""
}
```