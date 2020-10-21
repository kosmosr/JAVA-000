import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author kosmosr
 * @date 2020/10/20 21:37
 */
public class HelloClassLoader extends ClassLoader {
    public static void main(String[] args) {
        HelloClassLoader helloClassLoader = new HelloClassLoader();
        try {
            Class<?> helloClass = helloClassLoader.loadClass("Hello");
            Object helloInstance = helloClass.newInstance();
            Method method = helloClass.getDeclaredMethod("hello");
            method.invoke(helloInstance);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        InputStream inputStream = this.getResourceAsStream(name + ".xlass");
        if (inputStream == null) {
            return super.findClass(name);
        }
        try (BufferedInputStream stream = new BufferedInputStream(inputStream)) {
            byte[] bytes = new byte[stream.available()];
            stream.read(bytes);
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                bytes[i] = (byte) (255 - b);
            }
            return defineClass(name, bytes, 0, bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
            return super.findClass(name);
        }
    }
}
