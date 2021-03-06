package com.artistcorner.engclasses.others.analytics;

import org.apache.commons.lang3.SystemUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ViewLogAnalytics {

    public String pathSystemOSSwitcher(){

        if(SystemUtils.IS_OS_WINDOWS){ return "\\";}
        if(SystemUtils.IS_OS_LINUX){ return "/";}
        if(SystemUtils.IS_OS_MAC){return "/";}

        return "/";
    }


    public List<Commit> initializeCommitArray() throws GitAPIException, IOException {
        ArrayList<Commit> listOfCommit = new ArrayList<>();
        DateFormat df = new SimpleDateFormat();

        FileRepositoryBuilder builder = new FileRepositoryBuilder();

        //Apre la cartella .git nella repository di lavoro corrente.
        Repository repo = builder.setGitDir(new File(System.getProperty("user.dir") + pathSystemOSSwitcher() + ".git")).setInitialBranch("refs/heads/develop").setMustExist(true).build();
        Git git = new Git(repo);

        Iterable<RevCommit> log = git.log().call();


        for (Iterator<RevCommit> iterator = log.iterator(); iterator.hasNext();) {
            RevCommit rev = iterator.next();

            PersonIdent authorIdent = rev.getAuthorIdent();

            // Prende data ed orario del commit.
            Date authorDate = authorIdent.getWhen();

            // Prende nome del committer.
            String nome = authorIdent.getName();

            listOfCommit.add(new Commit(nome, df.format(authorDate), rev.getFullMessage()));

        }

        git.getRepository().close();

        Collections.reverse(listOfCommit);

        return listOfCommit;
    }



}
