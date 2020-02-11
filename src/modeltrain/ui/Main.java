package modeltrain.ui;

import modeltrain.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        Model model = new Model();
        UserInterface userInterface = new UserInterface(model);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Model successfully initialized");

        do {
            userInterface.executeCommand(reader.readLine());
        } while (!userInterface.isQuit());
    }
}
