#!groovy
properties([disableConcurrentBuilds()])

pipeline {
agent any
    options {
     buildDiscarder(logRotator(numToKeepStr: '10', artifactNumToKeepStr: '10'))
     timestamps()
    }

    stages {
        stage ("Docker-compose") {
            steps {
                echo " ============== start docker-compose =================="
                bash 'docker-compose up -d'
            }
        }
        stage('Build mvn project') {
            steps {
                echo " ============== build mvn project =================="
//                sleep(time: 20, unit: "SECONDS")
                bash 'mvn clean install'
            }
        }
        stage("Docker login") {
            steps {
                echo " ============== docker login =================="
                withCredentials([usernamePassword(credentialsId: 'docker_hub_maxirage', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                    bash """
                    docker login -u $USERNAME -p $PASSWORD
                    """
                }
            }
        }
        stage("Create docker image") {
            steps {
                echo " ============== start building image =================="
                bash 'docker build -t maxirage/csr:latest . '
            }
        }
        stage("Docker push") {
            steps {
                echo " ============== start pushing image =================="
                bash 'docker push maxirage/csr:latest'
            }
        }
        stage("Run app") {
            steps {
                echo " ============== start app =================="
                bash 'docker-compose up -d --build'
            }
        }
    }
}