package com.volvo.esw.gradle.gitbranch

import groovy.transform.Memoized
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.internal.storage.file.FileRepository
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.lib.*
import org.gradle.api.Plugin
import org.gradle.api.Project


class GitBranchNamePlugin implements Plugin<Project> {

    // Gradle returns 'unspecified' when it is not a git project
    private static final String UNSPECIFIED_BRANCH = 'unspecified'

    void apply(Project project) {
        project.ext.gitBranchName = {
            println "Branch name is " + gitBranchName(project)
            return gitBranchName(project)
        }
    }

   /* @Memoized
    private File gitDir(Project project) {
        return getRootGitDir(project.rootDir)
    }
    @Memoized
    private Git gitRepo(Project project) {
        return Git.wrap(new FileRepository(gitDir(project)))
    }*/

    @Memoized
    private String gitBranchName(Project project){
        Repository repo = new FileRepositoryBuilder().findGitDir()
        println repo
        try {
            String branch = repo.getBranch() ?: UNSPECIFIED_BRANCH
            return branch
        } catch (Throwable t) {
            return UNSPECIFIED_BRANCH
        }
    }

 /*   @Memoized
    private String gitBranchName(Project project){
        Git git = gitRepo(project)
        try {
            String branch = git.getBranch().call() ?: UNSPECIFIED_BRANCH
            boolean isClean = git.status().call().isClean()
            return version + (isClean ? '' : '.dirty')
        } catch (Throwable t) {
            return UNSPECIFIED_VERSION
        }
    }

    /*private static File getRootGitDir(currentRoot) {
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
    }*/
}
