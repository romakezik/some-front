import java.io.*;
import java.net.*;
import java.awt.*;
import java.lang.*;
class clientThread extends Thread{ //Потоковый класс клиента
  
 DataInputStream dis=null; //Клиент читает данные из сервера на базе
 //DataInputStream
 Socket s=null;
 public clientThread()
   { try{
   s=new Socket("127.0.0.1",2525); //Создаем сокет для клиента
   dis= new DataInputStream(s.getInputStream());//dis используется для 
//чтения из сокета клиента
}
   catch(Exception e)
{  System.out.println("ERROR:"+e);
}}
 public void run()
{ while (true)
  {
     try
  {sleep(100); //клиент подключается к сокету каждые 100 милисекунд
   
   }
   catch(Exception er)
    {System.out.println("BDD"+er);
     }
try{
    String msg=dis.readLine();//Чтение сообщения от сервера
    if(msg==null)
     break;
     System.out.println(msg);//Вывод прочитанного сообщения
}
   catch(Exception e)
    {System.out.println("ERRORR+"+e);
    }
} 
  }
    }

  class Account extends Thread{ //Потоковый класс сервера
      ServerSocket server;
      String amountstring;
      static  int amount=200;
     public void run(){
        try
       {
         server= new ServerSocket(2525); //Сокет сервера
         }
       catch(Exception e)
       { System.out.println("ERRSOCK+"+e);
       }
       while(true)
       { Socket s=null;
         try{
          s=server.accept(); //Прослушивает подключение к сокету клиента
            }
         catch(Exception e)
          {System.out.println("ACCEPTER"+e);}
          try
             {PrintStream ps=new PrintStream(s.getOutputStream());
              int amountcur=((int)(Math.random()*1000)); 
              if (Math.random()>0.5) 
              amount-=amountcur;   //сумма на счете изменяется случайно
              else
              amount+=amountcur;
              Integer x=new Integer(amount);
              amountstring=x.toString();
              ps.println("Account:"+amountstring);//выводит в сокет 
                                                  //сообщение для клиента
              ps.flush();
              s.close();
}
catch(Exception e)
    {System.out.println("PSERROR"+e);
        }
       }
     }
    }

 public class serv extends Frame{ //форма сервера
       public boolean handleEvent(Event evt)
    {
       if (evt.id==Event.WINDOW_DESTROY)
         {System.exit(0);}
        return super.handleEvent(evt);
}
    public boolean mouseDown(Event evt,int x,int y)
{
     new clientThread().start(); //Поток клиента порождается щелчком мыши 
                                 //в окне сервера
     return(true);
}
      public static void main(String args[])
    {
      serv f=new serv();
      f.resize(400,400);
      f.show();
      new Account().start();
       }
    }
