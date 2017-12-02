package zookeeper1;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ServerDemo {

	ZooKeeper zk = null;
	
	//connet ZooKeeper
	public void connectZooKeeper() throws Exception {
		zk = new ZooKeeper("node0:2181,node1:2181,node2:2181",2000,null);
	}
	
	//register ip and port
	public void registerNode(String ip,String port) throws Exception {
		String create = zk.create("/serverDemo/server", (ip+":"+port).getBytes(), Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);
		System.out.println("服务器成功向注册中心注册节点" + create);
	}
	
	public static void main(String[] args) throws Exception {
		ServerDemo serverDemo = new ServerDemo();
		serverDemo.connectZooKeeper();
		serverDemo.registerNode(args[0],args[1]);
		Thread.sleep(Long.MAX_VALUE);
	}

}
