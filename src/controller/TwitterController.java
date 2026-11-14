package controller;

import entity.Comment;
import entity.Like;
import entity.Post;
import entity.Profile;
import enums.Role;
import enums.Status;
import service.TwitterService;

import java.util.List;

import static utils.Util.*;

public class TwitterController {

    private final TwitterService twitterService = new TwitterService();


    public void start() {
        while (true) {
            System.out.println("""
                    1.Login
                    2.Register
                    3.Exit""");
            int menu = inputInt("Choose one");
            switch (menu) {
                case 1 -> login();
                case 2 -> register();
                case 0 -> {
                    return;
                }
            }
        }




    }

    private void login() {
        String username = inputStr("Enter your username");
        String password = inputStr("Enter your password");

        Role role = twitterService.login(username, password);

        if(role == null)System.out.println("Username or password is wrong try again");
        else if (role.equals(Role.ADMIN)) adminMenu();
        else if (role.equals(Role.USER)) userMenu();
    }

    private void register() {
        String fullName = inputStr("Enter your full name");
        String username = inputStr("Enter your username");
        String password = inputStr("Enter your password");

        boolean res = twitterService.register(fullName, username, password);

        if(res) System.out.println("Successfully registered");
        else System.out.println("Failed to register");
    }


    /*----------------------------User Menu------------------------------------*/
    public void userMenu(){
        while (true){
            System.out.println("""
                    1.Add post
                    2.Show posts
                    3.Show global posts
                    4.Delete post
                    5.Change password
                    0.Logout""");
            int menu = inputInt("Choose one");
            switch (menu){
                case 1 -> addPost();
                case 2 -> showPosts();
                case 3 -> showGlobalPosts();
                case 4 -> deletePost();
                case 5 -> changePassword();
                case 0 -> {return;}
            }
        }
    }

    private void addPost() {
        String title = inputStr("Enter post title");
        String content = inputStr("Enter post content");
        boolean res = twitterService.createPost(title, content);
        if(res) System.out.println("Successfully added post");
        else System.out.println("Failed to add post");
    }


    private void showPosts(){
        List<Post> posts = twitterService.getPostByUserId(currentUserId);
        postInfo(posts);
    }


    public void showGlobalPosts(){
        List<Post> posts = twitterService.getAllPosts();
        postInfo(posts);
    }

    public void postInfo(List<Post> posts){
        for (Post post : posts){
            System.out.println(post);
        }

        int id = inputInt("Choose one by id");
        Post post = twitterService.getPostById(id);

        if (post == null) {
            System.out.println("Post not found");
            return;
        }

        Profile profile = twitterService.getUserById(post.getUserId());

        int viewCount = twitterService.getViewCountByPostId(post.getId());
        int likeCount = twitterService.getLikeCountByPostId(post.getId());
        int commentCount = twitterService.getCommentCountByPostId(post.getId());

        System.out.println("--------" + profile.getUsername() + "-----------");
        System.out.println("Title: " + post.getTitle());
        System.out.println("Content: " + post.getContent());
        System.out.printf("View: %d  Like: %d  Comment: %d \n" , viewCount, likeCount, commentCount);

        System.out.println("""
                1.Like
                2.Comment""");


        int postMenu = inputInt("Choose one");
        if(postMenu == 1){
            twitterService.createLike(currentUserId, post.getId());
        }
        else if(postMenu == 2){
            writeComment(currentUserId, post.getId());
        }


        twitterService.createView(currentUserId, post.getId());

    }

    public void writeComment(int userId, int postId){
        List<Comment> list = twitterService.getCommentsByPostId(postId);
        System.out.println("----------Comments-------------");
        for (Comment comment : list) {
            System.out.println(comment);
        }

        String comment = inputStr("Enter comment");

        twitterService.createComment(userId, postId, comment);
    }








    public void deletePost(){
        List<Post> posts = twitterService.getPostByUserId(currentUserId);
        for (Post post : posts) {
            System.out.println(post);
        }
        int id = inputInt("Choose one by id");
        boolean res = twitterService.deletePostById(id);
        if(res) System.out.println("Successfully deleted post");
        else System.out.println("wrong id");
    }



    public void changePassword(){
        String oldPassword = inputStr("Enter old password");
        String newPassword = inputStr("Enter new password");
        boolean res = twitterService.changePassword(currentUserId, oldPassword, newPassword);
        if(res) System.out.println("Successfully changed password");
        else System.out.println("Wrong password");
    }

    /*----------------------------------Admin Menu------------------------------------------------------*/
    public void adminMenu(){
        while (true){
            System.out.println("""
                    1.Show all users
                    2.Block user
                    3.Active user
                    4.Delete user
                    5.Change password
                    0.Logout""");
            int menu = inputInt("Choose one");
            switch (menu){
                case 1 -> showAllUsers();
                case 2 -> changeUserStatus(Status.BLOCK);
                case 3 -> changeUserStatus(Status.ACTIVE);
                case 4 -> {}
                case 5 -> changePassword();
                case 0 -> {return;}

            }
        }
    }


    private void showAllUsers() {
        List<Profile> profiles = twitterService.getAllUsers();
        for (Profile profile : profiles) {
            System.out.println(profile);
        }
    }


    private void changeUserStatus(Status status){
        showAllUsers();
        int id = inputInt("Choose id");
        twitterService.changeUserStatusById(id, status);
        System.out.println(status);
    }


}
