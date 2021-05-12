stage('pull source code') {
    node('master'){
        git([url: 'git@github.com:little-success/xxl-job.git', branch: 'master'])
    }
}

stage('maven compile & package') {
    node('master'){
        sh "/usr/local/apache-maven-3.6.3/bin/mvn clean install "
    }
}

stage('clean docker environment') {
    node('master'){
        try{
            sh 'docker stop job'
        }catch(exc){
            echo 'job is not running!'
        }

        try{
            sh 'docker rm job'
        }catch(exc){
            echo 'job does not exist!'
        }
        try{
            sh 'docker rmi job:v1.0'
        }catch(exc){
            echo 'job:v1.0 image does not exist!'
        }
    }
}

stage('make new docker image') {
    node('master'){
        try{

            sh 'docker build -t job:v1.0 xxl-job-admin/.'
        }catch(exc){
            echo 'Make job:v1.0 docker image failed, please check the environment!'
        }
    }
}

stage('start docker container') {
    node('master'){
        try{
            sh 'docker run --name job -d -p 8803:8803  -p 8804:8804 job:v1.0'
        }catch(exc){
            echo 'Start docker image failed, please check the environment!'
        }
    }
}