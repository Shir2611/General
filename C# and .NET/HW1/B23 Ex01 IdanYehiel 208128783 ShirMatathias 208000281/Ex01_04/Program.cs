using System;

namespace Ex01_04
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
            Console.WriteLine("Insert a input of 6 letters (in English only) or digits (only)");

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
                    Console.WriteLine("Insert a input of 6 letters (in English only) or digits (only)");
                }
            }
            return userInput;
        }
        
        private static void printUserInput(string i_userInput)
        {
            if (isPalindromeRecursive(i_userInput))
            {
                Console.WriteLine("The string is a palindrome");
            }
            else
            {
                Console.WriteLine("The string isn't a palindrome");
            }
            if (onlyDigits(i_userInput))
            {
                if (isMultOfThree(i_userInput))
                {
                    Console.WriteLine("The number is a multiplication of three");
                }
                else
                {
                    Console.WriteLine("The number isn't a multiplication of three");
                }
            }
            if (onlyEngLetters(i_userInput))
            {
                Console.WriteLine(string.Format("The number of lowercase letters in the string is {0}", countLowerCaseLetters(i_userInput)));
            }  
        }

        private static bool isValidInput(string i_userInput)
        {
            return ((onlyDigits(i_userInput) || onlyEngLetters(i_userInput)) && (i_userInput.Length == 6));
        }

        private static bool onlyEngLetters(string i_userInput)
        {
            bool isOnlyEnglish = true;

            foreach (char letter in i_userInput)
            {
                if (!(((letter >= 65) && (letter <= 90)) || ((letter >= 97) && (letter <= 122))))
                {
                    isOnlyEnglish = false;
                }
            }
            return isOnlyEnglish;
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
        private static bool isPalindromeRecursive(string i_userInput)
        {
            bool checkEquality = false;
            bool recursuionStatment = false;
            bool checkLengthOfString = (i_userInput.Length <= 1);

            if (!checkLengthOfString)
            {
                checkEquality = i_userInput[0].Equals(i_userInput[i_userInput.Length - 1]);
                recursuionStatment = isPalindromeRecursive(i_userInput.Substring(1, i_userInput.Length - 2));
            }
            return (checkLengthOfString || (checkEquality && recursuionStatment)); 
        }

        private static bool isMultOfThree(string i_userInput)
        {
            int.TryParse(i_userInput, out int o_userInputInt);
            return (o_userInputInt % 3 == 0);
        }

        private static int countLowerCaseLetters (string i_userInput)
        {
            int countLowerCase = 0;

            foreach (char letter in i_userInput)
            {
                if (Char.IsLower(letter))
                {
                    countLowerCase++;
                }
            }
            return countLowerCase;
        }
    }
}
