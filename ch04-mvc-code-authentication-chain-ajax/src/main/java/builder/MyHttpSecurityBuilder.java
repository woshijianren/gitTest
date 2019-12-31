package builder;

/**
 * @author cj
 * @date 2019/12/31
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 模拟httpSecurity对象
 */
public class MyHttpSecurityBuilder {
    List<MyConfigurer> configurerList = new ArrayList<>();
    List<MyFilter> filterChain = new ArrayList<>();
    public List<MyFilter> build(){
        init();

        beforeBuild();
        for (MyConfigurer myConfigurer : configurerList) {
            MyFilter filter = myConfigurer.config();
            filterChain.add(filter);
        }
        afterBuild();
        return filterChain;

    }

    public CsrfConfigurer csrfConfigurer(){

        CsrfConfigurer csrfConfigurer =  new CsrfConfigurer(this);
        configurerList.add(csrfConfigurer);
        return  csrfConfigurer;
    }

    public MyFormLoginConfigurer formLogin(){

        MyFormLoginConfigurer formLoginConfigurer =  new MyFormLoginConfigurer(this);
        configurerList.add(formLoginConfigurer);
        return  formLoginConfigurer;
    }
    protected  void init(){}

    protected void beforeBuild(){}
    protected  void afterBuild(){}
}
