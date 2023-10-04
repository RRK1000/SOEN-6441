# SOEN-6441 - WARZONE RISK GAME

[![Java CI with Gradle](https://github.com/RRK1000/SOEN-6441/actions/workflows/gradle-ci.yml/badge.svg?branch=main)](https://github.com/RRK1000/SOEN-6441/actions/workflows/gradle-ci.yml)

This project is a command line implementation of the [warzone](https://www.warzone.com/) game for [Concordia University's](https://www.concordia.ca/) SOEN 6441 course, Advanced Programming Practices.
Warzone applies the concept of Risk, where a world map is defined as a connected graph and the goal of the players is to take over all the countries. Each player is assigned a certain number of armies at each turn and they can attack their neighboring countries to capture them. The orders given by players are executed in a round-robin fashion, until 1 player owns all the countries. 

## Build 1 Goal and Architecture

We have structured our application into several essential modules to ensure clarity, maintainability, and scalability.
- The **controller** module serves as the core of our game’s logic, handling user inputs to parse and take game decisions that updates the state of the game.
- The **global**  module provides a centralized location for resources, configuration  to be accessed by the entire application, promoting code consistency.
- The **game module** serves as the entry point of the game, with a single GameEngine class running an input loop.
- The  **models** module defines the artifacts of the game engine to represent the fundamental building blocks of our system. This includes the Player, Order, Country, Continent and Map objects.
- The **util** model holds the static methods required throughout the project. It includes the MapUtil class, which implements various map related functionality like loading, validating and saving the map.

## Team Members

- Rishi Ravikumar
- Anuja Ajay Somthankar
- Yusuke Ishii
- Nimisha Jadav
- Abhigyan Singh
  
