package servicii;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AuditServiciu {
    private static AuditServiciu instanta = null;
    private ScrieInFisier scrieInFisier = ScrieInFisier.getInstance();
    private AuditServiciu() {}

    public static AuditServiciu getInstance() {
        if (instanta == null) {
            instanta = new AuditServiciu();
        }
        return instanta;
    }

    public void log(String numeActiune) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        ArrayList<String> continut = new ArrayList<String>();
        continut.add(numeActiune);
        continut.add(timestamp);
        scrieInFisier.scrie("./date/Audit.csv", continut);
    }
}
