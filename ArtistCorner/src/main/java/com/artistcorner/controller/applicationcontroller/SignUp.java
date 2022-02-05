package com.artistcorner.controller.applicationcontroller;

import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.BuyerBean;
import com.artistcorner.engclasses.bean.UserBean;
import com.artistcorner.engclasses.dao.ArtistDAO;
import com.artistcorner.engclasses.dao.BuyerDAO;
import com.artistcorner.engclasses.dao.GalleryDAO;
import com.artistcorner.engclasses.exceptions.AddArtistException;
import com.artistcorner.engclasses.exceptions.AddBuyerException;
import com.artistcorner.engclasses.exceptions.AddGalleryException;
import com.artistcorner.engclasses.exceptions.DuplicateUserException;
import com.artistcorner.engclasses.query.QueryArtist;
import com.artistcorner.model.ArtGallery;
import com.artistcorner.model.Artist;
import com.artistcorner.model.Buyer;
import com.artistcorner.model.User;

import java.sql.SQLException;

public class SignUp {

    public void registerArtist(UserBean user, ArtistBean art) throws DuplicateUserException{
        User userToAdd = new User(user.getUsername(), user.getPassword(), user.getRole());
        Artist artistToAdd = new Artist(art.getNome(), art.getCognome());

        try {
            ArtistDAO.insertArtist(userToAdd, artistToAdd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void registerGallery(UserBean user, ArtGalleryBean gal) throws DuplicateUserException {
        User userToAdd = new User(user.getUsername(),user.getPassword(), user.getRole());
        ArtGallery artGalleryToAdd = new ArtGallery(gal.getGalleria(),gal.getNome(),gal.getDescrizione(),gal.getIndirizzo());
        try{
            GalleryDAO.insertGallery(userToAdd,artGalleryToAdd);
        }catch (SQLException e1){
            e1.printStackTrace();
        }
    }
    public void registerBuyer(UserBean user, BuyerBean buy)throws DuplicateUserException{
        User userToAdd = new User(user.getUsername(),user.getPassword(), user.getRole());
        Buyer buyerToAdd = new Buyer(buy.getIdBuyer(),buy.getNome(),buy.getCognome());
        try{
            BuyerDAO.insertBuyer(userToAdd,buyerToAdd);
        }catch (SQLException e2){
            e2.printStackTrace();
        }

    }

}
