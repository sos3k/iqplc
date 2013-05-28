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
	
	private ScheduledExecutorService scheduler;	
	private List<CollectorTask> collectorTasks;
	
	public DataCollector() {
	}
	
	@PostConstruct
	public void initConnection() {
		try {
			this.scheduler = new ScheduledThreadPoolExecutor(4);
			this.collectorTasks = new LinkedList<>();
			this.connection = new Socket("localhost", 5555);
			
			if (this.connection != null && !this.connection.isClosed()) {
				this.out = new BufferedWriter(new OutputStreamWriter(this.connection.getOutputStream()));
				this.in = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
				
				if (this.in != null && this.out != null) {
					this.collectorTasks.add(new FastFloatCollectorTask(this.connection, this.out, this.in));
//					this.collectorTasks.add(new SlowFloatCollectorTask(connection, out, in));
//					this.collectorTasks.add(new StatusCollectorTask(connection, out, in));
//					this.collectorTasks.add(new CounterCollectorTask(connection, out, in));
				}
			}
		} 
		catch (Exception e) {
			System.err.println("Nie uda³o siê po³¹czyæ z serwerem ponawiam wiêc próbê...");
			
			try {
				Thread.sleep(1000);
			} 
			catch (InterruptedException iex) {}
			
			initConnection();
		}
	}
	
	public void startCollecting() {
		if (this.scheduler != null) {
			if (this.collectorTasks != null && this.collectorTasks.size() > 0) {
				for (CollectorTask task : this.collectorTasks) {
					if (task != null) {					
						this.scheduler.scheduleAtFixedRate(task, 0, 4, TimeUnit.SECONDS);
					}
				}				
			}
		}
	}
	
	public void stopCollecting() {
		if (this.scheduler != null) {
			this.scheduler.shutdown();
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
