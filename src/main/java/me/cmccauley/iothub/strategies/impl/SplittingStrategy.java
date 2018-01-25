package me.cmccauley.iothub.strategies.impl;

import me.cmccauley.iothub.services.mqtt.MqttService;
import me.cmccauley.iothub.strategies.PublishStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class SplittingStrategy implements PublishStrategy{

    @Autowired
    private MqttService mqttService;

    @Override
    public void publish(String message) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner scanner = new Scanner(System.in);
        String inputString = scanner.next();

        int steps = 0;

        while(!isPalindrome(inputString))
        {
            String reverse = new StringBuilder(inputString).reverse().toString();
            int numericValue = Integer.parseInt(inputString) + Integer.parseInt(reverse);
            inputString = String.valueOf(numericValue);
        }

        System.out.println(inputString + " " + steps);

        String unbalanced = "()()(";

    }

    public boolean isPalindrome(String inputString){
        return inputString.equals(new StringBuilder(inputString).reverse().toString());
    }
}
