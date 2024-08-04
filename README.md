# Keycloak Configuration with Spring Boot

This repository demonstrates how to integrate Keycloak, an Identity and Access Management (IAM) solution, with a Spring Boot application for authentication and authorization.

## Table of Contents
- [Introduction](#introduction)
- [Prerequisites](#prerequisites)
- [Setup Keycloak](#setup-keycloak)
- [Spring Boot Configuration](#spring-boot-configuration)
- [Define Security Config](#define-security-config)
- [Custom Authentication Converter](#custom-authentication-converter)
- [Running the Application](#running-the-application)
- [Testing the Configuration](#testing-the-configuration)

## Introduction
Keycloak is an open-source IAM solution that provides features such as Single Sign-On (SSO), user federation, identity brokering, and more. This guide covers setting up Keycloak and configuring a Spring Boot application to use Keycloak for authentication and authorization.

## Prerequisites
- Java 11+
- Maven 3.6+
- Docker (optional, for running Keycloak)

## Setup Keycloak
1. **Run Keycloak**:
   You can run Keycloak using Docker:
   ```sh
   docker run -p 8080:8080 -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=admin jboss/keycloak
   ```

## Access Keycloak Admin Console

1. Open your browser and go to `http://localhost:8080`.
2. Log in with the username `admin` and password `admin`.

## Create a Realm

1. Go to the `Master` realm and click `Add Realm`.
2. Name your realm (e.g., `myrealm`).

## Create a Client

1. In your new realm, go to `Clients` and click `Create`.
2. Enter the `Client ID` (e.g., `spring-boot-app`).
3. Set `Client Protocol` to `openid-connect`.
4. Set `Access Type` to `confidential`.
5. Save the client.
6. In the `Credentials` tab, note the `Secret`.

## Create a User

1. Go to `Users` and click `Add User`.
2. Enter a username and save.
3. In the `Credentials` tab, set a password for the user.


