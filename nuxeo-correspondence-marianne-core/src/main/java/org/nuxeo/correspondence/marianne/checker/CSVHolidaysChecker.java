package org.nuxeo.correspondence.marianne.checker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.runtime.model.ComponentInstance;
import org.nuxeo.runtime.model.DefaultComponent;

/**
 * Holidays checker implementation which load the holidays dates in a CVS file.
 * The extension point "" of the component "" allows to specify where is located
 * the CSV file.
 *
 * @author Nicolas Ulrich
 *
 */
public class CSVHolidaysChecker extends DefaultComponent implements
        MarianneHolidaysChecker {

    private static final Log log = LogFactory.getLog(CSVHolidaysChecker.class);

    private static final SimpleDateFormat formater = new SimpleDateFormat(
            "dd/MM/yyyy");

    private static String cvsFileName;

    private static boolean cvsFileInJar;

    private static Set<Date> dates;

    /*
     * (non-Javadoc)
     *
     * @see
     * org.nuxeo.correspondence.marianne.service.checker.MarianneHolidaysChecker
     * #isHoliday(java.util.Date)
     */
    public boolean isHoliday(Date date) {
        return getDates().contains(date);
    }

    /**
     * Read the CVS file in order to load hollidays.
     *
     * @return list of dates contained in the csv file.
     */
    private Set<Date> getDates() {

        if (dates == null) {

            dates = new HashSet<Date>();

            try {

                BufferedReader buffer = null;

                if (cvsFileInJar) {
                    InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(
                            cvsFileName);

                    buffer = new BufferedReader(new InputStreamReader(is));
                } else {
                    buffer = new BufferedReader(new FileReader(cvsFileName));
                }

                String line;
                while ((line = buffer.readLine()) != null) {
                    dates.add(formater.parse(line));
                }

            } catch (FileNotFoundException e) {
                log.error("Unable to find the CSV file", e);
            } catch (IOException e) {
                log.error("Unable to read the CSV file", e);
            } catch (ParseException e) {
                log.error("The CSV file is not formatted", e);
            }

        }

        return dates;
    }

    @Override
    public void registerContribution(Object contribution,
            String extensionPoint, ComponentInstance contributor)
            throws Exception {

        if (extensionPoint.equals("configuration")) {

            CSVConfigurationDescriptor conf = ((CSVConfigurationDescriptor) contribution);
            cvsFileName = conf.csvFilePath;
            cvsFileInJar = conf.embended;
        }

    }

}
