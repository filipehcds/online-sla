@Library('gcscPipelineResources')

import br.com.cielo.gcsc.jenkins.pipeline.SigoEntrypoint

node { 
    env.JAVA_HOME = tool 'jdk1.8.0_131'
    env.DOCKER_HOME = tool 'docker-jenkins'
    env.PATH = "${env.JAVA_HOME}/bin:${env.PATH}:%{DOCKER_HOME}"

    configFileProvider( [configFile(fileId: 'global-sigo', variable: 'm3Settings')] ) {
        env.mavenSettings = m3Settings
        sh "cp ${m3Settings} settings.xml"
    }
}

new SigoEntrypoint(steps: steps, env: env, scm: scm, docker: docker).run()