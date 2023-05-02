using System;

namespace Ex01_03
{
    class Program
    {
        public static void Main()
        {
            int numOfLines = getUserInput();
            Ex01_02.Program.printSandClock(numOfLines);
            Console.Read();
        }

        private static int getUserInput()
        {
            bool recievedValidInput = false;
            bool isValidInput;
            int o_numOfLines = 0;
            Console.WriteLine("Insert the number of lines for the sand clock (and then enter)");

            while (!recievedValidInput)
            {
                isValidInput = int.TryParse(Console.ReadLine(), out o_numOfLines);
                
                if (isValidInput && o_numOfLines >= 0)
                {
                    recievedValidInput = true;

                    if (o_numOfLines % 2 == 0)
                    {
                        o_numOfLines++;
                    }
                }
                else
                {
                    Console.WriteLine("Unvalid input!");
                    Console.WriteLine("Insert the number of lines for the sand clock (and then enter)");
                }
            }
            return o_numOfLines;
        }
    }
}
