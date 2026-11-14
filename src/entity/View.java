package entity;

public class View {
    private int userId;
    private int postId;

    public View(int userId, int postId) {
        this.userId = userId;
        this.postId = postId;
    }

    public View() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
}
