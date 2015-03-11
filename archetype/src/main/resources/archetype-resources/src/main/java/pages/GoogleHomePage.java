#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.pages;

import com.syftkog.web.test.framework.HasDriver;
import com.syftkog.web.test.framework.Page;
import com.syftkog.web.test.framework.elements.Textbox;


/**
 *
 * @author BenjaminLimb
 */
public class GoogleHomePage extends Page<GoogleHomePage> {

    public static String url = "https://google.com";

    public Textbox searchBox = new Textbox(this,"Search Textbox","[name='q']");
    
    
    public GoogleHomePage(HasDriver hasDriver) {
        super(hasDriver, url);
    }

}
