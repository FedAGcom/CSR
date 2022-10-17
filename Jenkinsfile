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
                sh 'sudo chmod ugo+xwr /var/run/docker.sock'
                sh 'docker-compose up -d'
            }
        }
        stage('Build mvn project') {
            steps {
                echo " ============== build mvn project =================="
//                sleep(time: 20, unit: "SECONDS")
//                sh 'mvn clean install'
//                sh 'mvn liquibase:dropAll'
                sh 'mvn liquibase:update'
            }
        }
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
                sh 'sudo chmod --recursive ugo+xwr /var/lib/jenkins/workspace/csr/postgres-data/'
                sh 'sudo chmod --recursive ugo+xwr /var/lib/jenkins/workspace/csr/postgres-data/pg_stat_tmp'
                sh 'docker build -t maxirage/csr:latest . '
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