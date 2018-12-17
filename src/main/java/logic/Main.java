package logic;

import logic.flow.DoorDashFlow;
import logic.flow.GrubHubFlow;
import logic.flow.OpenTableFlow;

import java.io.IOException;

public class Main
{
    public static void main(String[] args) throws IOException {
//        DoorDashFlow flow = new DoorDashFlow();
//        flow.start();
        OpenTableFlow flow = new OpenTableFlow();
        flow.start();
    }
}