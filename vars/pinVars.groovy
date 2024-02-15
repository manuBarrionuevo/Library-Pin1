def call() {
    def pinVars = [:]

    pinVars.buildDockerImage = {  ->
        sh """
                docker build -t "$DOCKER_USER"/pin-1jenkins:"$version .
                docker images
            """
    }

    pinVars.pushDockerImage = { imageName, version, directory ->
        sh """
            docker push $imageName:$version
        """
    }

    pinVars.dockerLogin = { registryUrl ->
        withCredentials([usernamePassword(credentialsId: 'dockerHub', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASSWORD')]) {
            withDockerRegistry([url: registryUrl]) {
                return true
            }
            
        }
        return false
    }

    return pinVars
}
