package dsmain;

import java.rmi.Remote;

import dsblockchain.Block;
import java.util.ArrayList;

public interface ClientComInterface extends Remote {
    ArrayList<Block> getUpdate();
    String requestVote(Block b);

}