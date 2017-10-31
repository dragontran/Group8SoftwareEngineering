﻿// student 1 dev time for factory 45 minutes
// student 3 dev time for factory 15 minutes

namespace SoftEngMidterm
{

    // student 1 dev time for factory 45 minutes
    // student 4 dev time for factory 45 minutes
    // student 3 dev time for factory 15 minutes

    // interface will showcase factory design pattern principles.
    // will allow the creation of StackArray objects without calling on "new".
    // interface for factory method 
    // C14
    interface stackFactory {
        StackArray getStackObject();
    }

    // factory method for stack array object
    class stack : stackFactory
    {
        public StackArray getStackObject()
        {
            return new StackArray();
        }
    }

    // factory method for stack FIFO (queue) object
    // C15
    class fifoStack : stackFactory
    {
        public StackArray getStackObject()
        {
            return new StackFIFO();
        }
    }

    // factory method for hanoi stack object
    // C16
    class hanoiStack : stackFactory
    {
        public StackArray getStackObject()
        {
            return new StackHanoi();
        }
    }
}
