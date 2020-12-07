package dsmain;

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
                System.out.println("How many vote options for voters to select");
                int optionCount = s.nextInt();
                s.nextLine();
                ArrayList<String> voting_options = new ArrayList<String>();
                int i = 0;
                while(i < optionCount){
                    System.out.println("Enter Option "+i);
                    voting_options.add(s.nextLine());
                    i++;
                }
                System.out.println("How many votes allowed per voter");
                int selectionCount = s.nextInt();
               
                Blockchain chain = new Blockchain(info,selectionCount,voting_options,owner.user.name);
                displayPoll(chain);
                if(confirm()){
                    owner.createChain(chain);
                    System.out.println("Vote Created!");
                    checkBack();
                }  
                else{
                    System.out.println("Vote Canceled!");
                    clear();
                    checkBack();
                }
                    


            }
            // VOTE SECTION
            if (seleted.equals("2")) {
                clear();
                System.out.println(".............<Vote>.............");
                System.out.println("Polls List: ");
                showPolls();
                if(owner.getPolls().size() == 0){
                    System.out.println("There are no polls");
                    checkBack();
                }
                else{
                    System.out.println("Select A Poll");
                    int selection = s.nextInt();
                    s.nextLine();
                    ArrayList<Blockchain> chain = owner.getPolls();
                    displayPoll(chain.get(selection));
                    
                    if(chain.get(selection).verifyUser(owner.user.name)){
                        System.out.println("You Already Voted!");
                        checkBack();
                    }
                    else{
                        int numOfSelection = chain.get(selection).getNumOfSelection();
                        int[] votes = new int[numOfSelection];

                        for(int i = 0; i < numOfSelection; i++){
                            System.out.println("Enter Your Selection "+(i+1));
                            votes[i] = s.nextInt();
                            s.nextLine();
                        }
                        owner.vote(selection, votes);
                        
                    }
                }   
                
            }

            // Show Polls
            if (seleted.equals("3")) {
                clear();
                System.out.println(".............<Current Polls>.............");
                showPolls();
                
                if(owner.getPolls().size() == 0){
                    System.out.println("There are no polls");
                    checkBack();
                }
                else{
                    System.out.println("Enter selection to view poll result");
                    int selection = s.nextInt();
                    s.nextLine();
                    showPollResult(selection);
                    checkBack();
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
    public void displayPoll(Blockchain c){
        System.out.println(c.getVotingInfo());
        int idex = 0;
        for(String i : c.getVotingOptions()) {
            System.out.println("<"+(idex++)+">"+" Option: "+i);   
        }

        System.out.println("Only "+c.getNumOfSelection()+" votes allowed");
       
    }
    public void showPolls(){
        int idex = 0;
        ArrayList<Blockchain> polls = owner.getPolls();
        for (Blockchain i : polls) {
            System.out.println("<"+(idex++)+">"+" Poll: "+i.getVotingInfo());         
        }
    }
    
    public void showPollResult(int selection){

        int index = 0;
        ArrayList<String> options = owner.getPolls().get(selection).getVotingOptions();
        int [] results = owner.getPolls().get(selection).getVotes();
        for (String i: options) {
            System.out.println(i+": ["+results[+index++]+"] votes");    
        }
        

    }
    public void checkBack(){
        while(true){
            if(s.nextLine().equals("0"))
                return;
        }
        

    }
}
