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
            System.out.println("Option 2: Vote");
            System.out.println("Option 3: View Polls");
            String seleted = s.nextLine();
             
            // Create A Poll
            if (seleted.equals("1")) {
                clear();
                System.out.println(".............<Create A New Poll>.............");
                System.out.println("Enter Vote Information");
                String info = s.nextLine();
                System.out.println("How many options to vote");
                int optionCount = s.nextInt();
                s.nextLine();
                ArrayList<String> voting_options = new ArrayList<String>();
                int i = 0;
                while(i < optionCount){
                    System.out.println("Enter Option "+i);
                    voting_options.add(s.nextLine());
                    i++;
                }
                System.out.println("How many selections for voter");
                int selectionCount = s.nextInt();
               
                Blockchain chain = new Blockchain(info,selectionCount,voting_options,owner.user.name);
                displayPoll(chain);
                if(confirm()){
                    owner.createChain(chain);
                    System.out.println("Vote Created!");
                }  
                else{
                    System.out.println("Vote Canceled!");
                    clear();
                }
                    


            }
            // VOTE SECTION
            if (seleted.equals("2")) {
                clear();
                System.out.println(".............<Vote>.............");
                System.out.println("Polls List: ");
                showPolls();
                System.out.println("Select A Poll");
                int selection = s.nextInt();
                s.nextLine();
                ArrayList<Blockchain> chain = owner.getPolls();
                displayPoll(chain.get(selection));
                
                int index = 0;
                for (String i : chain.get(selection).getVotingOptions()) {
                    System.out.println("Option-"+index+": "+i);
                }
                if(chain.get(selection).verifyUser(owner.user.name)){
                    System.out.println("You Already Voted!");
                }
                else{
                    int numOfSelection = chain.get(selection).getNumOfSelection();
                    int[] votes = new int[numOfSelection];
                    System.out.println("You Can Make "+numOfSelection+" selections");
                    for(int i = 0; i < numOfSelection; i++){
                        System.out.println("Enter Your"+(i+1)+"Selection");
                        votes[i] = s.nextInt();
                        s.nextLine();
                    }
                    owner.vote(selection, votes);
                    
                }
                
                
            }

            // Show Polls
            if (seleted.equals("3")) {
                clear();
                System.out.println(".............<Current Polls>.............");
                showPolls();
                checkBack();
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
    public void displayPoll(Blockchain c){
        System.out.println(c.getVotingInfo());
        for(String i : c.getVotingOptions()) {
            System.out.println("Option: "+i);   
        }

        System.out.println(c.getNumOfSelection()+"Selection Allowed");
       
    }
    public void showPolls(){
        int idex = 0;
        for (Blockchain i : owner.getPolls()) {
            System.out.println("Poll number-"+(idex++)+": "+i.getVotingInfo());         
        }
    }
    
    
    public void checkBack(){
        while(true){
            if(s.nextLine().equals("0"))
                return;
        }
        

    }
}
