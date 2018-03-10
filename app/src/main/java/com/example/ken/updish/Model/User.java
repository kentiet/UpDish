package com.example.ken.updish.Model;

import java.util.ArrayList;

/**
 * Created by tanthinh on 3/7/18.
 */

public class User
{
    private String userName;
    private String email;
    private String[] personalList;

    /**
     * Constructor for user
     * @param userName
     * @param email
     * @param personalList
     */
    public User(String userName, String email, String[] personalList)
    {
        this.userName = userName;
        this.email = email;
        this.personalList = personalList;
    }

    /**
     * Post new dish to the database server
     * @param post Post object to get info from
     * @return boolean: true if Uploading is success
     */
    public boolean postNewDish(Post post)
    {
        return false;
    }

    /**
     * Change user's password
     * @param oldpass User's old password
     * @param newpass User's new password
     * @param reEnterNewPass User's new password re-entering
     * @return boolean: true if changed successfully
     */
    public boolean changePassword(String oldpass, String newpass, String reEnterNewPass)
    {
        return false;
    }

    /**
     *
     * @param list List of user's personal interest
     * @return boolean: true if update successfully
     */
    public boolean updatePersonalList(String[] list)
    {
        return false;
    }

    /* GETTER AND SETTER */

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String[] getPersonalList() {
        return personalList;
    }

    public void setPersonalList(String[] personalList) {
        this.personalList = personalList;
    }
}
