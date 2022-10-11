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
                sh 'docker-compose up -d'
            }
        }
//        stage('Build mvn project') {
//            steps {
//                echo " ============== build mvn project =================="
////                sleep(time: 20, unit: "SECONDS")
//                sh 'mvn clean install'
//            }
//        }
        stage("Docker login") {
            steps {
                echo " ============== docker login =================="
                withCredentials([usernamePassword(credentialsId: 'docker_hub_maxirage', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                    sh """
                    docker login -u $USERNAME -p $PASSWORD
                    """
                }
            }
        }
        stage("Create docker image") {
            steps {
                echo " ============== start building image =================="
                sh 'docker build -t maxirage/csr:latest . '
                sh 'chmod --recursive ugo+xwr pg_stat_tmp'
            }
        }
        stage("Docker push") {
            steps {
                echo " ============== start pushing image =================="
                sh 'docker push maxirage/csr:latest'
            }
        }
        stage("Run app") {
            steps {
                echo " ============== start app =================="
                sh 'docker-compose up -d --build'
            }
        }
    }
}