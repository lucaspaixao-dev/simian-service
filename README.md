# Simian API

This API was develop with: 
  - [Kotlin (JDK 8)](https://kotlinlang.org/)
  - [Ktor](https://ktor.io/)
  - [Koin](https://insert-koin.io/)
  - [MongoDB](https://www.mongodb.com/)
  - [Docker](https://www.docker.com/)
  - [OpenApi 3](https://swagger.io/docs/specification/about/)
  
# How to execute
 - Check if you have install the [docker-compose](https://docs.docker.com/compose/gettingstarted/)
 - Execute the following command in the project root: ```
                                                       $ docker-compose build
                                                       ```
 - Execute the command in the project root: ```
                                            $ docker-compose up
                                            ```
 - For test, execute the following command on terminal: ```
                                        $ curl --location --request GET 'localhost:8182/stats'
                                        ```
                                        
# Libraries
 - [deteKt](https://github.com/arturbosch/detekt) to code complexity
 - [ktlint](https://github.com/JLLeitschuh/ktlint-gradle) to code style analyze
 - [jacoco](https://gist.github.com/mrsasha/384a19f97cdeba5b5c2ea55f930fccd4) to code coverage  ```
                                                                                                   minimum = 0.80
                                                                                               ```                                                                                 
# Documentation
Open spec.yml on root project in [open api web](https://editor.swagger.io/)

# What I wanted...
 - Up project on heroku. 
