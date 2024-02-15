def call() {
    def pinVars = [:]

    pinVars.buildDockerImage = { imageName, version ->
        sh """
            docker build -t $imageName:$version .
            docker images
        """
    }

    pinVars.pushDockerImage = { imageName, version, directory ->
        sh """
            docker push $imageName:$version
        """
    }

    pinVars.dockerLogin = {  ->
        withCredentials([usernamePassword(credentialsId: 'dockerHub', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASSWORD')]) {
            sh  """
                docker login -u $DOCKER_USER -p $DOCKER_PASSWORD
            """
            pinVarsInstance.buildDockerImage("${DOCKER_USER}/pin-1jenkins", "${version}")
        }
    }

    return pinVars
}
