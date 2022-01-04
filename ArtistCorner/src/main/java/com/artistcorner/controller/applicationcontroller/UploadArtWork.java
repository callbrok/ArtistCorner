package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.bean.UploadingArtWork;
import com.artistcorner.engclasses.dao.ArtistDAO;


public class UploadArtWork {

    public void uploadImage(UploadingArtWork upArt) throws Exception {
        ArtistDAO.saveArtWork(upArt);
    }



}
