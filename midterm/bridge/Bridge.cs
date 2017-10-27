using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

// time taken to convert java to c# 40 mins (initially)
// time taken to comment/fix errors/understand 40 mins
namespace DesignPatterns
{
    // A simple node class
    // Contains an int value and the previous/next nodes
    public class Node
    {
        public int value;
        public Node prev, next;

        public Node(int value)
        {
            this.value = value;
        }
    }

    // A stack implementation of an array containing int values
    // Used to encapsulate the interfaces between StackFIFO and StackHanoi
    class StackArray
    {
        private int[] items;
        private int size = -1;

        public StackArray()
        {
            this.items = new int[12];
        }

        public StackArray(int cells)
        {
            if (cells <= 0)
            {
                Console.WriteLine("A number of 0 or less was provided for the number of cells, using the default length instead");
                this.items = new int[12];
            } else {
                this.items = new int[cells];
            }
        }

        // c# distinction
        // virtual to allow children classes to override 
        public virtual void push(int i)
        {
            if(!isFull())
            {
                if (i < 0){
                    Console.WriteLine("A negative number was provided, converting to a positive number");
                    i *= -1;
                }
                items[++size] = i;
            }
        }

        public bool isEmpty()
        {
            return size == -1;
        }

        public bool isFull()
        {
            return size == items.Length - 1;
        }

        public int peek()
        {
            if (isEmpty())
            {
                return -1;
            }

            return items[size];
        }

        // c# distinction
        // virtual to allow children classes to override
        public virtual int pop()
        {
            if (isEmpty())
            {
                return -1;
            }

            return items[size--];
        }
    }

    // A stack implementation of a list containing nodes with int values
    // Independent implementation
    class StackList
    {
        private Node last;
        
        public void push(int i)
        {
            // don't accept negative numbers to preserve isEmpty and pop functionality
            if (i < 0)
            {
                    Console.WriteLine("A negative number was provided, converting to a positive number");
                    i *= -1;
            }
            if (last == null)
            {
                last = new Node(i);
            } else {
                last.next = new Node(i);
                last.next.prev = last;
                last = last.next;
            }
        }

        public bool isEmpty()
        {
            return last == null;
        }

        public bool isFull()
        {
            return false;
        }

        public int peek()
        {
            if (isEmpty())
            {
                return -1;
            }

            return last.value;
        }

        public int pop()
        {
            if (isEmpty())
            {
                return -1;
            }

            int ret = last.value;
            last = last.prev;

            return ret;
        }
    }

    // FIFO Stack (queue) that inherits StackArray methods but using a different implementation
    // c# distinction
    // extends replaced with : to allow for inheritance
    class StackFIFO : StackArray
    {
        private StackArray stackArray = new StackArray();
        
        // c# distinction
        // override keyword required to replace virtual pop method from StackArray
        public override int pop()
        {
            while(!isEmpty())
            {
                // c# distinction
                // super repalced by base to call parent class method
                stackArray.push(base.pop());
            }

            int ret = stackArray.pop();

            while (!stackArray.isEmpty())
            {
                push(stackArray.pop());
            }

            return ret;
        }
    }

    // Hanoi stack (does not push a value unless it is less than/equal to the top of the stack) 
    // that inherits StackArray methods but uses a different impementation
    // c# distinction
    // extends replaced with : to allow for inheritance
    class StackHanoi : StackArray
    {
        private int totalRejected = 0;
        
        public int reportRejected()
        {
            return totalRejected;
        }

        // c# distinction
        // override keyword required to replace virtual push method from StackArray
        public override void push(int i)
        {
            //don't add a value if it is greater than the top value
            if (!isEmpty() && i > peek())
            {
                totalRejected++;
            } else {
                // c# distinction
                // super repalced by base to call parent class method
                base.push(i);
            }
        }
    }

    class BridgeDisc
    {
        static void Main(String[] args)
        {
            // Create an array of type StackArray, StackFIFO and StackHanoi are specific implementations of 
            // Stack arrays with different functionality but are still considered StackArrays due to implementation abstraction
            // being decoupled from interface abstraction (aka Bridge)
            StackArray[] stacks = {new StackArray(), new StackFIFO(), new StackHanoi()};
            StackList stackList = new StackList();

            const int STACK_ARRAY_INDEX = 0;
            const int STACK_FIFO_INDEX = 1;
            const int STACK_HANOI_INDEX = 2;

            const int NUM_VALUES = 15;

            // add the values 1 to NUM_VALUES-1 to the stack array, stack list, and stack fifo
            // note the StackArray was created without a specified size, thus the max size is 12
            for (int i = 1; i < NUM_VALUES; i++)
            {
                stacks[STACK_ARRAY_INDEX].push(i);
                stackList.push(i);
                stacks[STACK_FIFO_INDEX].push(i);
            }

            // randomly (try to) add 14 values in range [0,19] to the stack hanoi
            // values are not added if they are greater than the top value
            Random random = new Random();
            for (int i = 1; i < 15; i++)
            {
                stacks[STACK_HANOI_INDEX].push(random.Next(0,20));
            }

            // pop everything off the stack array and print it
            Console.WriteLine("Stack Array contents");
            while (!stacks[STACK_ARRAY_INDEX].isEmpty())
            {
                Console.Write(stacks[STACK_ARRAY_INDEX].pop() + " ");
            }
            Console.WriteLine();
            
            // pop everything off the stack list and print it
            Console.WriteLine("Stack List contents");
            while (!stackList.isEmpty())
            {
                Console.Write(stackList.pop() + " ");
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
            Console.WriteLine("total rejected is " + ((StackHanoi) stacks[STACK_HANOI_INDEX]).reportRejected());
        }
    }
}