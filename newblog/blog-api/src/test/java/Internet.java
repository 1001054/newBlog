import java.util.Scanner;

public class Internet {
    //create two people objects
    //the poster and the receiver
    static People alice = new People("001", "alice");
    static People bob = new People("002","bob");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String option;
        String content;

        new Thread(new MyRunnable()).start();

        //the operating system
        while(true){
            System.out.println("1.Alice writes a letter.");
            System.out.println("2.Bob reads the letter.");
            option = scanner.next();
            System.out.println();

            //alice write the letter
            if("1".equals(option)){
                System.out.println("Type the content:");
                content = scanner.next();
                alice.getComputer().getMailBox().setContent(content);
                System.out.println("The message has be sent.");
                System.out.println();
            //bob read the letter
            }else if("2".equals(option)){
                if(!"".equals(bob.getComputer().getMailBox().getContent())){
                    System.out.println(bob.getComputer().getMailBox().getContent());
                    System.out.println("(The content will be deleted after reading!)");
                }else{
                    System.out.println("There is no messages for you.");
                }
                //delete bob's message
                bob.getComputer().getMailBox().setContent("");
                System.out.println();
            }
        }
    }

    //the thread of sending messages
    static class MyRunnable implements Runnable{
        @Override
        public void run() {
            while(true){
                //send the message every 1 second
                try {
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //get the message from alice and put it to bob
                if(!"".equals(alice.getComputer().getMailBox().getContent())){
                    //copy
                    bob.getComputer().getMailBox().setContent(alice.getComputer().getMailBox().getContent());
                    //delete
                    alice.getComputer().getMailBox().setContent("");
                }
            }
        }
    }
}

class People{
    private String id;
    private String name;
    private Computer computer = new Computer();

    public People(String id, String name) {
        this.id = id;
        this.name = name;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Computer getComputer() {
        return computer;
    }
    public void setComputer(Computer computer) {
        this.computer = computer;
    }
}

class Computer{
    private String id;
    private String type;
    private Packet mailBox = new Packet("");

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Packet getMailBox() {
        return mailBox;
    }
    public void setMailBox(Packet mailBox) {
        this.mailBox = mailBox;
    }
}

class Packet{
    private String content;

    public Packet(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}