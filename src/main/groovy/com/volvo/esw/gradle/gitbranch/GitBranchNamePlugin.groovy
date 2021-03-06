package com.volvo.esw.gradle.gitbranch

import org.eclipse.jgit.internal.storage.file.FileRepository
import org.eclipse.jgit.lib.*
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.gradle.api.Plugin
import org.gradle.api.Project


class GitBranchNamePlugin implements Plugin<Project>{

    // Gradle returns 'unspecified' when it is not a git project
    private static final String UNSPECIFIED_BRANCH = 'unspecified'
    private static final String SNAPSHOT = 'snapshot'


    void apply(Project project) {
        project.ext.gitCurrentBranch = {
            if(gitExists(project)){
                println "Branch name is " + gitBranchName(project)
                return gitBranchName(project)
            }
            return SNAPSHOT
        }
    }
    private static Boolean gitExists(Project project){
        File gitDir = scanForRootGitDir(project.rootDir)
        if (gitDir.exists()) {
            return true
        }
        return false
    }
    private String gitBranchName(Project project){
        Repository gitRepo = gitRepo(project)
        try {
            String branch = gitRepo.getBranch() ?: UNSPECIFIED_BRANCH
            return branch
        } catch (Throwable t) {
            return UNSPECIFIED_BRANCH
        }
    }
    private File gitDir(Project project) {
        return getRootGitDir(project.rootDir)
    }

    private Repository gitRepo(Project project) {
        return new FileRepository(gitDir(project))
    }

    private static File getRootGitDir(currentRoot) {
        File gitDir = scanForRootGitDir(currentRoot)
        if (!gitDir.exists()) {
            throw new IllegalArgumentException('Cannot find \'.git\' directory')
        }
        return gitDir
    }

    private static File scanForRootGitDir(File currentRoot) {
        File gitDir = new File(currentRoot, '.git')

        if (gitDir.exists()) {
            return gitDir
        }
        // stop at the root directory, return non-existing File object
        if (currentRoot.parentFile == null) {
            return gitDir
        }
        // look in parent directory
        return scanForRootGitDir(currentRoot.parentFile)
    }
}