package logic;

import logic.flow.DoorDashFlow;
import logic.flow.GrubHubFlow;

import java.io.IOException;

public class Main
{
    public static void main(String[] args) throws IOException {
//        DoorDashFlow flow = new DoorDashFlow();
//        flow.start();
        GrubHubFlow flow = new GrubHubFlow();
        flow.start();
    }
}