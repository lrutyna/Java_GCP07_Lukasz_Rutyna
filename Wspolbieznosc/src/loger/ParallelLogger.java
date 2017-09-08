package loger;

import java.util.ArrayList;
import java.util.List;


public class ParallelLogger implements Logger{
	
	private List<Log> logList;
	private Logger[] loggers;
	

	public ParallelLogger(Logger[] loggers) {
		this.loggers = loggers;
		logList = new ArrayList<Log>();
	}


	@Override
	public void log(Log log) {
		for(Logger el : loggers){
			el.log(log);
		}
	}


	public List<Log> getLogList() {
		return logList;
	}


	public void setLogList(List<Log> logList) {
		this.logList = logList;
	}


}
