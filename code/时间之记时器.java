package demo;


public class DataDemo extends Thread{
	public long startTime ;   
    
    public static void main(String[] args) {   
        new DataDemo().start();   
    }   
  
    public void run(){   
        startTime = System.currentTimeMillis();   
        System.out.println(startTime);   
        while(true){   
            long longtime = (System.currentTimeMillis()-startTime)/1000;   
            String time = String.format("%02d:%02d:%02d", longtime/3600,longtime/60,longtime%60);   
            System.out.println(time);   
            try {   
                Thread.sleep(1000);   
            } catch (InterruptedException e) {   
                e.printStackTrace();   
            }   
        }   
    }   

}
