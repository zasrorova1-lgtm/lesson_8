package service;

import dao.TwitterDao;
import entity.*;
import enums.Role;
import enums.Status;
import utils.Util;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static utils.Util.postId;
import static utils.Util.userId;

public class TwitterService {

    private final TwitterDao twitterDao = new TwitterDao();

    public boolean register(String fullName, String username, String password) {
        Profile test = twitterDao.getProfileByUsername(username);
        if(test != null) return false;

        Profile profile = new Profile(userId++, fullName, username, password, Role.USER, Status.ACTIVE);
        twitterDao.saveProfile(profile);
        return true;
    }


    public Role login(String username, String password){
        Profile profile = twitterDao.getProfileByUsername(username);
        if(profile == null) return null;
        if(!profile.getPassword().equals(password)) return null;

        Util.currentUserId = profile.getId();
        return profile.getRole();
    }


    public boolean createPost(String title, String content) {
        if(title == null) return false;
        if(title.isBlank()) return false;

        Profile profile = twitterDao.getProfileById(Util.currentUserId);
        if(profile.getStatus().equals(Status.BLOCK))return false;

        Post post = new Post(Util.postId++, title, content, Util.currentUserId);
        twitterDao.savePost(post);
        return true;
    }

    public List<Post> getPostByUserId(int UserId) {
        List<Post> list = twitterDao.getAllPosts();
        List<Post> resList = new ArrayList<>();
        for (Post post : list) {
            if(post.getUserId() == userId){
                resList.add(post);
            }
        }
        return resList;
    }

    public List<Post> getAllPosts(){
        return twitterDao.getAllPosts();
    }


    public List<Profile> getAllUsers() {

        return twitterDao.getAllUsers();
    }

    public Profile getUserById(int id) {
        return twitterDao.getProfileById(id);
    }


    public void changeUserStatusById(int id, Status status) {
        List<Profile> profiles = twitterDao.getAllUsers();
        for (Profile profile : profiles) {
            if(profile.getId() == id){
                profile.setStatus(status);
                break;
            }
        }
    }


    public Post getPostById(int id) {
        List<Post> posts = twitterDao.getAllPosts();
        for (Post post : posts) {
            if(post.getId() == id) return post;
        }
        return null;
    }


    public boolean deletePostById(int id) {
        List<Post> posts = getAllPosts();
        for (Post post : posts) {
            if(post.getId() == id && post.getUserId() == Util.currentUserId){
                posts.remove(post);
                return true;
            }
        }
        return false;
    }

    public boolean changePassword(int currentUserId, String oldPassword, String newPassword) {
        Profile profile = twitterDao.getProfileById(currentUserId);
        if (profile.getPassword().equals(oldPassword)) {
            profile.setPassword(newPassword);
            return true;
        }
        return false;
    }

    public void createView(int UserId, int postId) {
        View view = new View (userId, postId);
        twitterDao.saveView(view);
    }

    public int getViewCountByPostId(int postId) {
        List<View> viewList = twitterDao.getViewList();
        int count = 0;
        for(View view: viewList){
            if(view.getPostId() == postId){
                count++;
            }
        }
        return count;
    }

    public void createLike(int UserId, int postId) {
        List<Like> likeList = twitterDao.getLikeList();
        Iterator<Like> iterator = likeList.iterator();
        while (iterator.hasNext()){
            Like like = iterator.next();
            if(like.getPostId() == postId && like.getUserId() == userId){
                iterator.remove();
                return;
            }
        }
        Like like = new Like(userId, postId);
        twitterDao.saveLike(like);

    }

    public int getLikeCountByPostId(int id) {
        List<Like> likeList = twitterDao.getLikeList();
        int count = 0;
        for (Like like : likeList) {
            if(like.getPostId() == postId) count++;
        }
        return count;
    }

    public void createComment(int userId, int postId, String comment) {
        Comment commentObj = new Comment(userId, postId, comment);
        twitterDao.saveComment(commentObj);
    }


    public List<Comment> getCommentsByPostId(int postId) {
        List<Comment> commentList = twitterDao.getCommentList();
        List<Comment> resList = new ArrayList<>();
        for (Comment comment : commentList) {
            if(comment.getPostId() == postId){
                resList.add(comment);
            }
        }
        return resList;
    }

    public int getCommentCountByPostId(int postId) {
        List<Comment> postsCommentList = getCommentsByPostId(postId);
        return postsCommentList.size();
    }
}
