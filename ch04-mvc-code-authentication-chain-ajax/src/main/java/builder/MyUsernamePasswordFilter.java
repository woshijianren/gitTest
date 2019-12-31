package builder;

/**
 * @author cj
 * @date 2019/12/31
 */
public class MyUsernamePasswordFilter extends MyFilter{

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void doFilter() {

    }

    @Override
    public String toString() {
        return "MyUsernamePasswordFilter{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
