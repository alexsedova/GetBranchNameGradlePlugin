# GetBranchNamePlugin
This gradle plugin has a project extension gitBranchName. This extension contains the information about current git branch,
if the gradle project has no .git repo the branch name will be unspecified

Usage:

Clone this repo
<pre><code>git clone ... </code></pre>

Go to git repository

Build project and publish it to MavenLocal repository by command:
<pre><code>./gradlew publishMavenPublicationToMavenLocal </code></pre>

You can check if *.jar has been published in
~/.m2/repository/com/volvo/esw/gradle-git-branch/...

In build.gradle file of a project you want to use artifact-metadata classes
add this code:

<pre><code>
buildscript {
    repositories {
        mavenLocal()
    }
    dependencies {
            classpath group: 'com.volvo.esw', name: 'gradle-git-branch',
                    version: '<current-version>'
        }
}

apply plugin: 'gitbranchname'

task printCurrentBranch {
        println "CurrentBranch is: " + gitCurrentBranch()
}
</code></pre>
