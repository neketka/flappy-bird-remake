import java.util.ArrayList;

/**
 * Created by Nikita on 4/11/14.
 */
public class Logger {
    private static Logger ourInstance = new Logger();

    public static Logger getInstance() {
        return ourInstance;
    }

    private ArrayList<String> log = new ArrayList<>();

    private Logger() {
    }

    public void log(String s){
        log.add(s);
    }

    public void log(Exception e){
        log.add(e.toString());
        log.add("");
        for(StackTraceElement i : e.getStackTrace()){
            log.add(i.toString());
        }
    }

    public final ArrayList<String> getLog(){
        return log;
    }

    public void clear(){
        log.clear();
    }
}
