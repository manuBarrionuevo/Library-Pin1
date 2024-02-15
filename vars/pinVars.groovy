def call() {
    def pinVars = [:]

    pinVars.buildDockerImage = { imageName, version ->       
        withDockerRegistry([url: 'https://registry.example.com']) {
            sh """
                docker login -u $DOCKER_USER -p $DOCKER_PASSWORD
                docker build -t $imageName:$version .
                docker images
            """
        }
    }

    pinVars.pushDockerImage = { imageName, version, directory ->
        withDockerRegistry([url: 'https://registry.example.com']) {
            sh """
                docker login -u $DOCKER_USER -p $DOCKER_PASSWORD
                docker push $imageName:$version
            """
        }
    }

    pinVars.dockerLogin = { registryUrl ->
        withCredentials([usernamePassword(credentialsId: 'dockerHub', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASSWORD')]) {
            withDockerRegistry([url: registryUrl]) {
                sh  """
                    docker login -u $DOCKER_USER -p $DOCKER_PASSWORD
                """
            }
        }
    }

    return pinVars
}
