import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MonitoredData {
	private Date startTime;
	private Date endTime;
	private String activityLabel;

	public MonitoredData(String line) {
		String[] dataLine = line.split("\t");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			this.startTime = dateFormat.parse(dataLine[0] + " " + dataLine[1]);
			this.endTime = dateFormat.parse(dataLine[2] + " " + dataLine[3]);
			this.activityLabel = dataLine[4];
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public MonitoredData(Date startTime, Date endTime, String activityLabel) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.activityLabel = activityLabel;
	}

	// -------------------------------------------------> startTime
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	// -------------------------------------------------> endTime
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	// ------------------------------------------------> activityLabel
	public String getActivityLabel() {
		return activityLabel;
	}

	public void setActivityLabel(String activityLabel) {
		this.activityLabel = activityLabel;
	}

	public Integer getStartDateTask4() {
		DateFormat dateFormat = new SimpleDateFormat("dd");
		return Integer.parseInt(dateFormat.format(this.startTime));
	}
	
	public String getStartDateAsString() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(this.startTime);
	}

	public String getEndDateAsString() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(this.endTime);
	}

	@Override
	public String toString() {
		return "MonitoredData {" + "startTime = " + getStartDateAsString() + ", endTime = " + getEndDateAsString()
				+ ", activityLabel = '" + activityLabel + '\'' + '}';
	}
}
