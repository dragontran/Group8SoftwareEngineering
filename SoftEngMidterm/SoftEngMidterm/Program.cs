using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;


namespace SoftEngMidterm
{
    class Driver
    {
        class Program
        {
            static void Main(string[] args)
            {
                Console.WriteLine("Hello world");

                // Create an array of type StackArray, StackFIFO and StackHanoi are specific implementations of 
                // Stack arrays with different functionality but are still considered StackArrays due to implementation abstraction
                // being decoupled from interface abstraction (aka Bridge)
                // StackArray[] stacks = { new StackArray(), new StackFIFO(), new StackHanoi() };

                /* i dont know if this is right */
                StackArray[] stacks = { (new stack()).getStackObject(), (new fifoStack()).getStackObject(), (new hanoiStack()).getStackObject() };

                const int STACK_ARRAY_INDEX = 0;
                const int STACK_FIFO_INDEX = 1;
                const int STACK_HANOI_INDEX = 2;

                const int NUM_VALUES = 15;

                // add the values 1 to NUM_VALUES-1 to the stack array, stack list, and stack fifo
                // note the StackArray was created without a specified size, thus the max size is 12
                for (int i = 1; i < NUM_VALUES; i++)
                {
                    stacks[STACK_ARRAY_INDEX].push(i);
                    stacks[STACK_FIFO_INDEX].push(i);
                }

                // randomly (try to) add 14 values in range [0,19] to the stack hanoi
                // values are not added if they are greater than the top value
                Random random = new Random();
                for (int i = 1; i < 15; i++)
                {
                    stacks[STACK_HANOI_INDEX].push(random.Next(0, 20));
                }

                // pop everything off the stack array and print it
                Console.WriteLine("Stack Array contents");
                while (!stacks[STACK_ARRAY_INDEX].isEmpty())
                {
                    Console.Write(stacks[STACK_ARRAY_INDEX].pop() + " ");
                }
                Console.WriteLine();

                // pop everything off the stack fifo and print it
                Console.WriteLine("Stack FIFO contents");
                while (!stacks[STACK_FIFO_INDEX].isEmpty())
                {
                    Console.Write(stacks[STACK_FIFO_INDEX].pop() + " ");
                }
                Console.WriteLine();

                // pop everything off the stack hanoi and print it, then report the number of values rejected
                Console.WriteLine("Stack Hanoi contents");
                while (!stacks[STACK_HANOI_INDEX].isEmpty())
                {
                    Console.Write(stacks[STACK_HANOI_INDEX].pop() + " ");
                }
                Console.WriteLine();
                Console.WriteLine("total rejected is " + ((StackHanoi)stacks[STACK_HANOI_INDEX]).reportRejected());




            }
        }
    }
}

