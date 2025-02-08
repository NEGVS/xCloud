package xCloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class XOrderApplication
{
   public static void main( String[] args )
   {
      System.out.println( "Hello XOrderApplication !" );

      SpringApplication.run( XOrderApplication.class, args );
   }
}