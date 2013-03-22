package poc.nosql.publishedodds.datetimeformat;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeFormatTest {
    @Test
    public void shouldParseDate() throws ParseException {
        String dateString = "2030-05-06T11:00:00.000+01:00";

        SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

        Date parsedDate = sdf.parse(dateString);

        Assert.assertNotNull(parsedDate);
        System.out.println(parsedDate);
    }
}
