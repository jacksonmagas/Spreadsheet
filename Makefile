# Makefile for Husksheets Project

# Variables
SRC_DIR := src
BIN_DIR := bin

# Default target
all: build

# Build target
build:
    @echo "Building project..."
    # Add build commands here
    javac -d $(BIN_DIR) $(SRC_DIR)/*.java

# Test target
test:
    @echo "Running tests..."
    # Add test commands here
    # Example: java -cp $(BIN_DIR):path/to/junit.jar org.junit.runner.JUnitCore TestClass

# Docker target
docker:
    @echo "Building Docker image..."
    # Add Docker commands here
    # Example: docker build -t husksheets .

# Clean target
clean:
    @ echo "Cleaning project..."
    rm -rf $(BIN_DIR)/*
