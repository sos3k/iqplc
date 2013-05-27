package pl.iqsoft.plc.ejb;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import pl.iqsoft.plc.collector.CollectorTask;
import pl.iqsoft.plc.collector.CounterCollectorTask;
import pl.iqsoft.plc.collector.FastFloatCollectorTask;
import pl.iqsoft.plc.collector.SlowFloatCollectorTask;
import pl.iqsoft.plc.collector.StatusCollectorTask;

@Singleton
//@Startup
public class DataCollector {
	
	private Socket connection;
	private BufferedWriter out;
	private BufferedReader in;
	
	private List<CollectorTask> collectorTasks;
	
	public DataCollector() {
	}
	
	@PostConstruct
	public void initConnection() {
		try {
			this.collectorTasks = new LinkedList<>();
			this.connection = new Socket("localhost", 5555);
			
			if (this.connection != null && !this.connection.isClosed()) {
				out = new BufferedWriter(new OutputStreamWriter(this.connection.getOutputStream()));
				in = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
				
				if (this.in != null && this.out != null) {
					this.collectorTasks.add(new FastFloatCollectorTask(connection, out, in));
					this.collectorTasks.add(new SlowFloatCollectorTask(connection, out, in));
					this.collectorTasks.add(new StatusCollectorTask(connection, out, in));
					this.collectorTasks.add(new CounterCollectorTask(connection, out, in));
					
//					startCollecting();
				}
			}
		} 
		catch (Exception e) {
			System.err.println("Nie uda³o siê po³¹czyæ z serwerem ponawiam wiêc próbê...");
			
			try {
				Thread.sleep(10000);
			} 
			catch (InterruptedException iex) {}
			
			initConnection();
		}
	}
	
	public void startCollecting() {
		if (this.collectorTasks != null && this.collectorTasks.size() > 0) {
			ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(4);
			
			for (CollectorTask task : this.collectorTasks) {
				if (task != null) {					
					scheduler.scheduleAtFixedRate(task, 0, 4, TimeUnit.SECONDS);
				}
			}
		}
	}
	
	@PreDestroy
	public void closeConnection() {
		try {
			if (this.connection != null) {
				this.connection.close();
			}

			if (this.out != null) {
				this.out.close();
			}

			if (this.in != null) {
				this.in.close();
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
