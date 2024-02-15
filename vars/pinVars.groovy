// En pinVars.groovy

def call() {
    def pinVars = [:]

    pinVars.buildDockerImage = { imageName, version, directory ->
        echo "Directorio actual: ${pwd()}"
        dir(directory) {
            sh '''
                ls -la
            '''
        }
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
