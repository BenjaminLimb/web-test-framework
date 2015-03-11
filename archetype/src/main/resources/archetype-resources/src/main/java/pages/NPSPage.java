#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.pages;


import com.syftkog.web.test.framework.HasDriver;
import com.syftkog.web.test.framework.Page;

/**
 *
 * @author BenjaminLimb
 */
public class NPSPage extends Page<NPSPage> {

    public static String url = "http://www.nps.gov/";

    public NPSPage(HasDriver hasDriver) {
        super(hasDriver, url);
    }

}
