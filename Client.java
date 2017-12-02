package zookeeper1;

import java.util.HashMap;
import java.util.List;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooKeeper;

public class ClientDemo {

	private static HashMap<String, String> serverList = null;

	private static ZooKeeper zk = null;
	
	//connect ZooKeeper
	public void connectZooKeeper() throws Exception {
		zk = new ZooKeeper("node0:2181,node1:2181,node2:2181", 2000, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				if(event.getType() == EventType.None) {
					return;
				}
				try {
					getServers();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	//get servers from ZooKeeper
	public void getServers() throws Exception {
		HashMap<String, String> servermap = new HashMap<String,String>();
		List<String> children = zk.getChildren("/serverDemo", true);
		for (String child : children) {
			String data = new String(zk.getData("/serverDemo/" + child, false, null));
			servermap.put(child, data);
		}
		serverList = servermap;
		System.out.println("本客户端查询了一次可用服务器：" + serverList);
	}

	public static void main(String[] args) throws Exception {

		ClientDemo clientDemo = new ClientDemo();
		clientDemo.connectZooKeeper();
		clientDemo.getServers();
		
		Thread.sleep(Long.MAX_VALUE);
			
	}

}
