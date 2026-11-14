package dao;

import entity.*;
import enums.Role;
import enums.Status;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TwitterDao {

    private List<Profile> profileList = new ArrayList<>();

    private List<Post> postList = new LinkedList<>();

    private List<View> viewList = new ArrayList<>();

    private List<Like<C>> likeList = new LinkedList<>();

    private List<Comment> commentList = new ArrayList<>();

    {
        profileList.add(new Profile(1, "Ali Aliyev", "ali", "123", Role.ADMIN, Status.ACTIVE));
    }


    public void saveProfile(Profile profile){
        profileList.add(profile);
    }

    public void savePost(Post post){
        postList.add(post);
    }

    public void saveView(View view){
        viewList.add(view);
    }

    public void saveLike( Like<C> like){
        likeList.add(like);
    }


    public void saveComment(Comment comment){
        commentList.add(comment);
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public List<Like> getLikeList() {
        return likeList;
    }

    public List<View> getViewList() {
        return viewList;
    }

    public List<Profile> getAllUsers(){
        return profileList;
    }

    public Profile getProfileByUsername(String username){
        for(Profile p : profileList){
            if(p.getUsername().equals(username)){
                return p;
            }
        }
        return null;
    }


    public Profile getProfileById(int id){
        for(Profile p : profileList){
            if(p.getId() == id){
                return p;
            }
        }
        return null;
    }


    public List<Post> getAllPosts() {
        return postList;
    }
}
