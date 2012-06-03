/**
 * 
 */
import com.xiao.project.controller.AddressBookController;
import com.xiao.project.po.Person;
import org.junit.Test;


/**
 * @author XiaoTing
 * 2012-5-20
 */
public class AddressBookControllerTest {

	private static AddressBookController abc = AddressBookController.getInstance();
	
	@Test
	public void testQueryPerson(){
		abc.queryPerson("name", "jia");
		abc.queryPerson("name", "c.*");
		abc.queryPerson("mobile", "13411111111");
	}
	
	@Test
	public void testSavePerson(){
		Person p = new Person("ttt","13499999999","addddddddddd");
		abc.savePerson(p);
		Person p2 = new Person("a","13499999999","");
		abc.savePerson(p2);
	}
	
	@Test
	public void testDeletePerson(){
		abc.deletePerson("name", "a");
		abc.deletePerson("name", ".*ba.*");
		abc.deletePerson("address", "addddddddddd");
		abc.deletePerson("name", ".*xiao.*");
	}
	
	@Test
	public void testPrintHelpInfo(){
		abc.printHelpInfo();
	}
}
