using System;

// Student 5: Convert java to c# 1 hour 15 mins of dev time
// Student 5: Comment code to show c# distinctions 30 mins of dev time
// Student 5: Modify class to satisfy tests 1 hour of dev time
namespace SoftEngMidterm
{
    // A stack implementation of an array containing int values
    // Used to encapsulate the interfaces between StackFIFO and StackHanoi
    // C1
    public class StackArray
    {
        private int[] items;
        public int size { get; private set; }

        // C2
        public StackArray()
        {
            size = -1;
            this.items = new int[12];
        }

        // C3
        public StackArray(int cells)
        {
            size = -1;
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
        // C4
        public virtual void push(int i)
        {
            if (!isFull)
            {
                if (i < 0) {
                    Console.WriteLine("A negative number was provided, converting to a positive number");
                    i *= -1;
                }
                items[++size] = i;
            }
        }

        // C5
        public bool isEmpty { get { return size == -1; } }

        // C6
        public bool isFull { get { return size == items.Length -1; } }

        // C7
        public int peek()
        {
            if (isEmpty)
            {
                return -1;
            }

            return items[size];
        }

        // c# distinction
        // virtual to allow children classes to override
        // C8
        public virtual int pop()
        {
            if (isEmpty)
            {
                return -1;
            }

            return items[size--];
        }

        public int GetItemLength()
        {
            return items.Length;
        }
    }


    // FIFO Stack (queue) that inherits StackArray methods but using a different implementation
    // c# distinction
    // extends replaced with : to allow for inheritance
    // C9
    class StackFIFO : StackArray
    {
        private StackArray stackArray = new StackArray();
        
        // c# distinction
        // override keyword required to replace virtual pop method from StackArray
        // C10
        public override int pop()
        {
            while(!isEmpty)
            {
                // c# distinction
                // super repalced by base to call parent class method
                stackArray.push(base.pop());
            }

            int ret = stackArray.pop();

            while (!stackArray.isEmpty)
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
    // C11
    class StackHanoi : StackArray
    {
        private int totalRejected = 0;
        
        // C12
        public int reportRejected()
        {
            return totalRejected;
        }

        // c# distinction
        // override keyword required to replace virtual push method from StackArray
        // C13
        public override void push(int i)
        {
            //don't add a value if it is greater than the top value
            if (!isEmpty && i > peek())
            {
                totalRejected++;
            } else {
                // c# distinction
                // super repalced by base to call parent class method
                base.push(i);
            }
        }
    }

    //class BridgeDisc
    //{
    //    static void Main(String[] args)
    //    {
    //        // Create an array of type StackArray, StackFIFO and StackHanoi are specific implementations of 
    //        // Stack arrays with different functionality but are still considered StackArrays due to implementation abstraction
    //        // being decoupled from interface abstraction (aka Bridge)
    //        StackArray[] stacks = {new StackArray(), new StackFIFO(), new StackHanoi()};
    //        StackList stackList = new StackList();

    //        const int STACK_ARRAY_INDEX = 0;
    //        const int STACK_FIFO_INDEX = 1;
    //        const int STACK_HANOI_INDEX = 2;

    //        const int NUM_VALUES = 15;

    //        // add the values 1 to NUM_VALUES-1 to the stack array, stack list, and stack fifo
    //        // note the StackArray was created without a specified size, thus the max size is 12
    //        for (int i = 1; i < NUM_VALUES; i++)
    //        {
    //            stacks[STACK_ARRAY_INDEX].push(i);
    //            stackList.push(i);
    //            stacks[STACK_FIFO_INDEX].push(i);
    //        }

    //        // randomly (try to) add 14 values in range [0,19] to the stack hanoi
    //        // values are not added if they are greater than the top value
    //        Random random = new Random();
    //        for (int i = 1; i < 15; i++)
    //        {
    //            stacks[STACK_HANOI_INDEX].push(random.Next(0,20));
    //        }

    //        // pop everything off the stack array and print it
    //        Console.WriteLine("Stack Array contents");
    //        while (!stacks[STACK_ARRAY_INDEX].isEmpty())
    //        {
    //            Console.Write(stacks[STACK_ARRAY_INDEX].pop() + " ");
    //        }
    //        Console.WriteLine();
            
    //        // pop everything off the stack list and print it
    //        Console.WriteLine("Stack List contents");
    //        while (!stackList.isEmpty())
    //        {
    //            Console.Write(stackList.pop() + " ");
    //        }
    //        Console.WriteLine();

    //        // pop everything off the stack fifo and print it
    //        Console.WriteLine("Stack FIFO contents");
    //        while (!stacks[STACK_FIFO_INDEX].isEmpty())
    //        {
    //            Console.Write(stacks[STACK_FIFO_INDEX].pop() + " ");
    //        }
    //        Console.WriteLine();

    //        // pop everything off the stack hanoi and print it, then report the number of values rejected
    //        Console.WriteLine("Stack Hanoi contents");
    //        while (!stacks[STACK_HANOI_INDEX].isEmpty())
    //        {
    //            Console.Write(stacks[STACK_HANOI_INDEX].pop() + " ");
    //        }
    //        Console.WriteLine();
    //        Console.WriteLine("total rejected is " + ((StackHanoi) stacks[STACK_HANOI_INDEX]).reportRejected());
    //    }
    //}
}