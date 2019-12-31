package builder;

import javax.servlet.Filter;
import java.util.List;

/**
 * @author cj
 * @date 2019/12/31
 */
public class Main {
    public static void main(String[] args) {
        MyHttpSecurityBuilder builder = new MyHttpSecurityBuilder();
        builder.formLogin().usernameParameter("uname")
                .passwordParameter("pwd")
                .and()
                .csrfConfigurer().tokenParameter("dafd");

        List<MyFilter> chain =  builder.build();
        for (MyFilter filter : chain) {
            System.out.println("-----debug: filter = " + filter);
        }
    }
}
