class AndroidLibPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.task("sample") {
            doLast {
                print("i am a sample plugin")
            }
        }
    }

}