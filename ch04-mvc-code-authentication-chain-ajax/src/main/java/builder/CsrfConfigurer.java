package builder;

/**
 * @author cj
 * @date 2019/12/31
 */
public class CsrfConfigurer implements MyConfigurer {

    private MyHttpSecurityBuilder builder;
    private String tokenName ="username";


    public CsrfConfigurer(MyHttpSecurityBuilder builder) {
        this.builder = builder;
    }

    @Override
    public MyFilter config(){

        CsrfFilter filter = new CsrfFilter();
        filter.setTokenName(this.tokenName);
        return  filter;
    }

    public CsrfConfigurer tokenParameter(String tokenName){
        this.tokenName = tokenName;
        return this;
    }



    public MyHttpSecurityBuilder and(){
        return this.builder;
    }
}
