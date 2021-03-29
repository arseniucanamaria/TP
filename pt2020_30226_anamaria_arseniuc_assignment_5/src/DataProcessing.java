import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataProcessing {

	//--------------------------------------------->citire din fisierul de intrare
	public List<MonitoredData> readFromFile() {
		String fileName = "Activities.txt";
		List<MonitoredData> monitoredData = new ArrayList<>();

		try {
			Stream<String> stream = Files.lines(Paths.get(fileName));
			monitoredData = stream.map(MonitoredData::new).collect(Collectors.toList());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return monitoredData;
	}

	// -------------------------------------------------->task1
	public void task1() {
		List<MonitoredData> monitoredData = readFromFile();
		try {
			BufferedWriter f = new BufferedWriter(new FileWriter("Task_1.txt"));
			for (MonitoredData m : monitoredData) {
				f.write(m.toString());
				f.newLine();
			}

			f.close();
		} catch (IOException e) {
		}
	}

	// -------------------------------------------------->task2
	public Long countDays() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		List<MonitoredData> monitoredData = this.readFromFile();
		List<String> noOfDays = monitoredData.stream().map(m -> dateFormat.format(m.getStartTime()))
				.collect(Collectors.toList());

		return noOfDays.stream().distinct().count();
	}

	public void task2() {
		try {
			BufferedWriter f = new BufferedWriter(new FileWriter("Task_2.txt"));
			f.write("The distinct days that appear in the monitoring data is: ");
			f.write(String.valueOf(countDays()));
			f.close();
		} catch (IOException e) {
		}
	}

	// -------------------------------------------------->task3
	public Map<String, Long> groupByActivityLabel() {
        List<MonitoredData> monitoredData = this.readFromFile();
        Map<String, Long> datas = monitoredData.stream().collect(Collectors.groupingBy(MonitoredData::getActivityLabel, Collectors.counting()));

        return datas;
    }

	public void task3() {

		try {
			BufferedWriter f = new BufferedWriter(new FileWriter("Task_3.txt"));
			Map<String, Long> map = groupByActivityLabel();

			for (Entry<String, Long> entry : map.entrySet()) {
				f.write(entry.getKey() + "=" + entry.getValue().toString());
				f.newLine();
			}
			f.close();
		} catch (IOException e) {
		}
	}

	// -------------------------------------------------->task4
	public Map<Integer, Map<String, Long>> groupByDateAndCountActivityLabel() {
		List<MonitoredData> monitoredData = this.readFromFile();

		Map<Integer, Map<String, Long>> ss = monitoredData.stream()
				.collect(Collectors.groupingBy(MonitoredData::getStartDateTask4,
						Collectors.groupingBy(MonitoredData::getActivityLabel, Collectors.counting())));
		return ss;
	}

	public void task4() {

		try {
			BufferedWriter f = new BufferedWriter(new FileWriter("Task_4.txt"));
			
			Map<Integer, Map<String, Long>> res2 = groupByDateAndCountActivityLabel();
			
			for (Map.Entry<Integer, Map<String, Long>> entry : res2.entrySet()) {
				f.write(String.valueOf(entry));
				f.newLine();
			}
			f.close();
		} catch (IOException e) {
		}
	}

	public int getTime(MonitoredData m) {
		return (int) (m.getEndTime().getTime() - m.getStartTime().getTime());
	}

	// -------------------------------------------------->task5
	public Map<String, Integer> computeForEachActivityDuration() {
		List<MonitoredData> datas = this.readFromFile();

		Map<String, Integer> res = datas.stream().collect(
				Collectors.groupingBy(MonitoredData::getActivityLabel, Collectors.summingInt(m -> getTime(m))));
		return res;
	}

	public static String millisecondsPrint(Integer milliseconds) {
		long diffSeconds = milliseconds / 1000 % 60;
		long diffMinutes = milliseconds / (60 * 1000) % 60;
		long diffHours = milliseconds / (60 * 60 * 1000) % 24;
		long diffDays = milliseconds / (24 * 60 * 60 * 1000);

		return ("DAYS=" + diffDays + "; HOURS=" + diffHours + "; MINUTES=" + diffMinutes + "; SECONDS=" + diffSeconds
				+ " }");
	}

	public void task5() {
		try {
			BufferedWriter f = new BufferedWriter(new FileWriter("Task_5.txt"));

			Map<String, Integer> map = computeForEachActivityDuration();
			for (Map.Entry<String, Integer> entry : map.entrySet()) {
				f.write((entry.getKey() + "-> Duration{ " + millisecondsPrint(entry.getValue())));
				f.newLine();
			}

			f.close();
		} catch (IOException e) {
		}
	}

	// -------------------------------------------------->task6
	public List<String> filterActivities() {
		List<MonitoredData> datas = this.readFromFile();

		Map<String, Long> groupedByd = datas.stream()
				.collect(Collectors.groupingBy(MonitoredData::getActivityLabel, Collectors.counting()));
		Map<String, Long> filter = datas.stream().filter(m -> getMinutes(m) <= 5)
				.collect(Collectors.groupingBy(MonitoredData::getActivityLabel, Collectors.counting()));

		List<String> res = filter.entrySet().stream()
				.filter(e -> (e.getValue() * 100) / groupedByd.get(e.getKey()) > 90).map(e -> e.getKey())
				.collect(Collectors.toList());

		return res;
	}

	public Long getMinutes(MonitoredData m) {
		Long time = (m.getEndTime().getTime() - m.getStartTime().getTime());
		return time / (60 * 1000);
	}

	public void task6() {
		try {
			BufferedWriter f = new BufferedWriter(new FileWriter("Task_6.txt"));

			List<String> list = filterActivities();
			for (String l : list) {
				f.write(l);
				f.newLine();
			}

			f.close();
		} catch (IOException e) {
		}
	}
}
