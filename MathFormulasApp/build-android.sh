#!/bin/bash

echo "Building Math Formulas App for Android..."

# Install dependencies
echo "Installing dependencies..."
npm install

# Build for Android
echo "Building Android APK..."
npx expo build:android --type apk

echo "Build completed! Check the output for the APK location."