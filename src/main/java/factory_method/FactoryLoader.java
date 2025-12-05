package factory_method;
import java.lang.reflect.InvocationTargetException;


public class FactoryLoader {

	public static Object getInstance(String className) throws ClassNotFoundException, IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		@SuppressWarnings("rawtypes")
		Class c = null;
		try {
				c = Class.forName(className);
			} catch (ClassNotFoundException e) {
				System.out.println("El nombre de la clase no existe en el classpath");
				e.printStackTrace();
				return null;
			}
			Object o = null;
			try {
				if (c != null) {
					o = c.newInstance();
				}
			} catch (InstantiationException e) {
				System.out.println("Ha ocurrido un error al invocar el constructor de la clase");
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				System.out.println("Esta clase no tiene constructores disponibles");
				e.printStackTrace();
			}
			return o;
	}
}
