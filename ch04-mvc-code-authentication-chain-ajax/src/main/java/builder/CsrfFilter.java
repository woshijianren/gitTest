package builder;

/**
 * @author cj
 * @date 2019/12/31
 */
public class CsrfFilter extends MyFilter{

    private String tokenName;

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    @Override
    public void doFilter() {

    }

    @Override
    public String toString() {
        return "CsrfFilter{" +
                "tokenName='" + tokenName + '\'' +
                '}';
    }
}
