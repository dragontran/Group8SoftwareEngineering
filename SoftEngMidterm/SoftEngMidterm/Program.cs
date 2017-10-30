using System;
using System.Collections.Generic;

namespace SoftEngMidterm
{
    class Driver
    {
        // Written by Student number 3 with 3 hours of development time
        class Program
        {
            /// <summary>
            /// Prompts the user for numbers of hanoi stacks and random values to be inserted into each hanoi stack
            /// The results from inserting the random values will be displayed to the user in the end
            /// </summary>
            /// <param name="args"></param>
            static void Main(string[] args)
            {
                // Asks user to provide values for the number of stacks and values to be input into each stack
                int numberOfStacksCreated = GetNaturalNumberInput("Enter an integer greater than 0 for the number of hanoi stacks.");
                int numberOfRandomValues = GetNaturalNumberInput("Enter an integer greater than 0 for the number of random values to be input to each stack");

                // create instance of factory
                hanoiStack factory = new hanoiStack();

                // create instance of iterator set for a set of hanoi stacks
                IteratorSet<StackHanoi> hanoiStacks = new IteratorSet<StackHanoi>();

                // create a random number generator
                Random randomGenerator = new Random();

                // add the specified number of stack objects into the hanoi stack set that have each had 40 random values attempted to be added in
                for (int i = 0; i < numberOfStacksCreated; i++)
                {
                    // generate hanoi stack using the factory object
                    var stackToBeAdded = (StackHanoi)factory.getStackObject();
                    for (int j = 0; j < numberOfRandomValues; j++)
                    {
                        // pushes random value into the stack
                        stackToBeAdded.push(randomGenerator.Next());
                    }
                    // add stack into the iterator set
                    hanoiStacks.add(stackToBeAdded);
                }

                // iterator that will be used to traverse objects within the hanoiStacks set
                IteratorSet<StackHanoi>.Iterator stackIterator = hanoiStacks.createIterator();
                // sets the iterator to the first element of the list
                stackIterator.first();

                // initialize values to be calculated from the list of stacks
                int maxNumberOfRejections = int.MinValue;
                int minNumberOfRejections = int.MaxValue;
                int totalNumberOfRejections = 0;
                List<int> contentsOfBestStack = new List<int>();

                while (!stackIterator.isDone)
                {
                    var currentStack = stackIterator.currentValue;
                    int numberRejected = currentStack.reportRejected();
                    totalNumberOfRejections += numberRejected;
                    // checks to see if this stack has the greatest number of rejections and modifies maxNumberOfRejections accordingly
                    if (maxNumberOfRejections < numberRejected)
                        maxNumberOfRejections = numberRejected;

                    if (minNumberOfRejections > numberRejected)
                    {
                        minNumberOfRejections = numberRejected;
                        // remove all content from previous best stack
                        contentsOfBestStack.Clear();
                        // exhaust the current stack by popping all of its contents into the contentsOfBestStack list
                        while(!currentStack.isEmpty)
                        {
                            contentsOfBestStack.Add(currentStack.pop());
                        }
                    }

                    // move on to next value
                    stackIterator.next();
                }

                // Display result to the user
                Console.WriteLine("The worst stack had " + maxNumberOfRejections + " rejections.");
                Console.WriteLine("The average stack had " + ((double)(totalNumberOfRejections) / numberOfStacksCreated) + " rejections.");
                Console.WriteLine("The best stack had only " + minNumberOfRejections + " rejections.");
                Console.WriteLine("The best stack had the following content:");
                foreach (int i in contentsOfBestStack)
                    Console.WriteLine("\t" + i);                
              
            }

            // Uses the given prompt to ask the user for a natural number (integers greater than 0)
            private static int GetNaturalNumberInput(string prompt)
            {
                int result = 0;
                do
                {
                    // provide user with prompt and immediately display to console
                    Console.WriteLine(prompt);
                    Console.Out.Flush();
                    // read user input
                    var input = Console.ReadLine();
                    int inputValue;
                    // attempt to parse integer from user input
                    if (int.TryParse(input, out inputValue))
                    {
                        // confirm that user input is a positive value
                        if (inputValue > 0)
                        {
                            result = inputValue;
                        }
                        else
                        {
                            Console.WriteLine("ERROR: input must be greater than 0");
                        }
                    }
                    else
                    {
                        Console.WriteLine("ERROR: input must be an integer!"); ;
                    }
                } while (result == 0); // result is set to 0 by default
                return result;
            }
        }
    }
}

