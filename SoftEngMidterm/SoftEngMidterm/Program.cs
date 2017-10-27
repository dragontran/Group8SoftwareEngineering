using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;


namespace SoftEngMidterm
{
    class Driver
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Hello World!");
            Bridge.StackArray[] stacks = { new Bridge.StackArray(), new Bridge.StackFIFO(), new Bridge.StackHanoi() };
            Bridge.StackList stackList = new Bridge.StackList();
            for (int i = 1; i < 15; i++)
            {
                stacks[0].push(i);
                stackList.push(i);
                stacks[1].push(i);
            }

            Random random = new Random();
            for (int i = 1; i < 15; i++)
            {
                stacks[2].push(random.Next(0, 20));
            }
            while (!stacks[0].isEmpty())
            {
                Console.Write(stacks[0].pop() + " ");
            }
            Console.WriteLine();
            while (!stackList.isEmpty())
            {
                Console.Write(stackList.pop() + " ");
            }
            Console.WriteLine();
            while (!stacks[1].isEmpty())
            {
                Console.Write(stacks[1].pop() + " ");
            }
            Console.WriteLine();
            while (!stacks[2].isEmpty())
            {
                Console.Write(stacks[2].pop() + " ");
            }
            Console.WriteLine();
            Console.WriteLine("total rejected is " + ((Bridge.StackHanoi)stacks[2]).reportRejected());
        }
    }
}

