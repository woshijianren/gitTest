package builder;

/**
 * @author cj
 * @date 2019/12/31
 */
public class MyFormLoginConfigurer implements MyConfigurer {

    private MyHttpSecurityBuilder builder;
    private String username ="username";
    private String password = "password";

    public MyFormLoginConfigurer(MyHttpSecurityBuilder builder) {
        this.builder = builder;
    }

    @Override
    public MyFilter config(){

        MyUsernamePasswordFilter filter = new MyUsernamePasswordFilter();
        filter.setPassword(password);
        filter.setUsername(username);
        return  filter;
    }

    public MyFormLoginConfigurer usernameParameter(String username){
        this.username = username;
        return this;
    }

    public MyFormLoginConfigurer passwordParameter(String password){
        this.password = password;
        return this;
    }

    public MyHttpSecurityBuilder and(){
        return this.builder;
    }
}
