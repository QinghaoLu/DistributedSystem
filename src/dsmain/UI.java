package dsmain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import dsblockchain.Blockchain;

public class UI implements Runnable {
    MainClientProgram owner;
    Scanner s;
    public UI(MainClientProgram owner) {
        this.owner = owner;
        this.s = owner.scanner;
    }

    @Override
    public void run() {
        while (true) {
            clear();


            System.out.println(".............<Welcome>.............");
            System.out.println("Menu: ");
            System.out.println("Option 1: Create A New Poll");
            System.out.println("Option 2: vote");
            System.out.println("Option 3: shit");

             

            if (s.nextLine().equals("1")) {
                clear();
                System.out.println(".............<Create A New Poll>.............");
                System.out.println("Enter Vote Information");
                String info = s.nextLine();
                System.out.println("How many options to vote");
                int optionCount = s.nextInt();
                ArrayList<String> voting_options = new ArrayList<String>();
                for(int i = 0; i < optionCount; i++){
                    System.out.println("Enter Option "+i);
                    String temp = s.nextLine();
                    voting_options.add(temp);
                }
                System.out.println("How many selections for voter");
                int selectionCount = s.nextInt();
                System.out.println(info);
                
                if(confirm()){
                    owner.createChain(new Blockchain(info,selectionCount,voting_options,owner.user.name));
                    System.out.println("Vote Created!");
                    
                }  
                else{
                    System.out.println("Vote Canceled!");
                    clear();
                }
                    


            }
            
            clear();
            
        }
        

    }
    public boolean confirm(){
        System.out.println("Confirm Y/N?");
        if(s.nextLine().equals("Y") || s.nextLine().equals("y"))
            return true;
        return false;
    }
    public void clear(){
        System.out.print("\033[H\033[2J");   
        System.out.flush();
    }
    
}
