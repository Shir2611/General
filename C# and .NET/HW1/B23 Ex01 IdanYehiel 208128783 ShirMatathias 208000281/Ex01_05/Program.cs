using System;

namespace Ex01_05
{
    class Program
    {
        public static void Main()
        {
            string userInputString = getUserInput();
            printUserInput(userInputString);
            Console.Read();
        }

        private static string getUserInput()
        {
            bool recievedValidInput = false;
            string userInput = "";
            Console.WriteLine("Insert a input of 6 digits only");

            while (!recievedValidInput)
            {
                userInput = Console.ReadLine();

                if (isValidInput(userInput))
                {
                    recievedValidInput = true;
                }
                else
                {
                    Console.WriteLine("Unvalid input!");
                    Console.WriteLine("Insert a input of 6 digits only");
                }
            }
            return userInput;
        }

        private static bool isValidInput(string i_userInput)
        {
            return (onlyDigits(i_userInput) && (i_userInput.Length == 6));
        }

        private static bool onlyDigits(string i_userInput)
        {
            bool isOnlyDigits = true;

            foreach (char letter in i_userInput)
            {
                if (!Char.IsDigit(letter))
                {
                    isOnlyDigits = false;
                }
            }
            return isOnlyDigits;
        }

        private static void printUserInput(string i_userInput)
        {
            Console.WriteLine(string.Format("The smallest digit is: {0}.", getSmallestDigit(i_userInput)));
            Console.WriteLine(string.Format("The average of the digits is: {0}.", computeAverageOfDigits(i_userInput)));
            Console.WriteLine(string.Format("There are {0} numbers that are divisible by 3.", countDigitsDivByThree(i_userInput)));
            Console.WriteLine(string.Format("There are {0} numbers that are smaller than the unit's digit ({1}).", countSmallerThanUnits(i_userInput), getUnitsDigit(i_userInput)));
        }
        private static int getSmallestDigit(string i_userInput) 
        {
            int smallestDigit = 10;
            int o_currentDigit = 0;
            foreach (char letter in i_userInput)
            {
                int.TryParse(letter.ToString(), out o_currentDigit);

                if (o_currentDigit < smallestDigit)
                {
                    smallestDigit = o_currentDigit;
                }
            }
            return smallestDigit;
        }

        private static float computeAverageOfDigits(string i_userInput)
        {
            float sumOfDigits = 0;
            foreach (char letter in i_userInput)
            {
                sumOfDigits += float.Parse(letter.ToString());
            }
            return (sumOfDigits / 6);
        }
       
        private static bool isDigitDivByThree(char i_digit)
        {
            int.TryParse(i_digit.ToString(), out int o_userDigitInt);
            return (o_userDigitInt % 3 == 0);
        }

        private static int countDigitsDivByThree(string i_userInput)
        {
            int countDivByThree = 0;
            foreach (char letter in i_userInput)
            {
                if (isDigitDivByThree(letter))
                {
                    countDivByThree++;
                }
            }    
            return countDivByThree;
        }

        private static int getUnitsDigit(string i_userInput)
        {
            return int.Parse(i_userInput[i_userInput.Length - 1].ToString());
        }

        private static int countSmallerThanUnits(string i_userInput)
        {
            int unitDigit = getUnitsDigit(i_userInput);
            int countSmallThanUnit = 0;
            int o_currentDigit = 0;

            foreach(char letter in i_userInput)
            {
                int.TryParse(letter.ToString(), out o_currentDigit);

                if (unitDigit > o_currentDigit) 
                { 
                    countSmallThanUnit++;
                }
            }
            return countSmallThanUnit;
        }
    }
}
