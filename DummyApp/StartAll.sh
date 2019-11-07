#!/bin/bash

# Remember to change ip for the server in file: ./client/index.html

echo "Installing client dependencies"
npm install --prefix ./client

echo "Installing server dependencies"
npm install --prefix ./server

echo "Starting client and server"
npm start --prefix ./client 8080 &
npm start --prefix ./server 8081 &
