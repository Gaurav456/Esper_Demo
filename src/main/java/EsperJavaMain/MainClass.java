package EsperJavaMain;

import com.esperJava.model.BaseDataModel;

import java.util.ArrayList;
import java.util.List;

import com.esper.mysql.MySQLAccess;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;


public class MainClass {
	
	public static void execute(EPRuntime cepRT) throws Exception {
	/*	String[] Source_address = { "gaurav", "nitin", "192.168.49.53", "192.162.48.53", "192.168.43.53",
				"191.168.48.53", "192.168.48.54", "192.168.48.50", "190.168.48.53", "198.168.48.53", "192.168.48.57",
				"197.168.48.53" };
		List<BaseDataModel> list = new ArrayList<>();

		for (int i = 0; i < Source_address.length; i++) {
			BaseDataModel dataModel = new BaseDataModel();
			dataModel.setSource_ip(Source_address[i]);
			list.add(dataModel);
		}*/
		List<BaseDataModel> list = new ArrayList<>();
		MySQLAccess access=new MySQLAccess();
		list=access.readDataBase();
		for (BaseDataModel e : list) {
			cepRT.sendEvent(e);
		}

	}
	
	public static class CEPListener implements UpdateListener {
		public void update(EventBean[] newData, EventBean[] oldData) {

			if (newData != null) {

				for (EventBean f : newData) {
					System.out.println("eventReceived: " + f.get("source_ip")+"--"+f.get("usr_name"));
				}

			}
		}
	}
	public static void main(String[] args) throws Exception {
	
	    
	    
		Configuration cepConfig = new Configuration();
		cepConfig.addEventType("BaseDataModel", BaseDataModel.class.getName());
		EPServiceProvider cep = EPServiceProviderManager.getProvider("myCEPEngine", cepConfig);
		EPRuntime cepRT = cep.getEPRuntime();

		EPAdministrator cepAdm = cep.getEPAdministrator();
		EPStatement cepStatement = cepAdm
				.createEPL("select * from BaseDataModel(source_ip IS NOT NULL AND ec_Activity='logon' AND ec_Outcome = 'failure').std:groupwin(source_ip).win:time_length_batch(10 sec, 3) GROUP BY source_ip HAVING COUNT(*) = 3 ");

		CEPListener ceps = new CEPListener();
		cepStatement.addListener(ceps);

		execute(cepRT);
	  }

}
