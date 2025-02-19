import it.csi.alm.Constants

def postBuildCommands(build_map) {
    sh """
    cd ${WORKSPACE}/dist_arch
	mkdir -p ${WORKSPACE}/target/dist
    zip -r ${WORKSPACE}/target/dist/pgmeasapp-1.0.1.zip *
    """
}

return this
