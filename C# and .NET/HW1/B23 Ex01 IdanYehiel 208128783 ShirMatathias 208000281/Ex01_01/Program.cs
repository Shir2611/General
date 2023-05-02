using System;

namespace Ex01_01
{
    class Program
    {
        public const int k_NumOfIntegers = 3;

        public static void Main()
        {
            (string[] o_inputBinaryArray, float[] o_inputDecimalArray) = recieveUserInput();
            printOutput(o_inputBinaryArray, o_inputDecimalArray);
            Console.Read();
        }

        private static void printOutput(string[] i_outputBinaryArray, float[] i_outputDecimalArray)
        {
            Console.WriteLine(string.Format("The decimal numbers are: {0}, {1}, {2}.", i_outputDecimalArray[0], i_outputDecimalArray[1], i_outputDecimalArray[2]));
            Console.WriteLine(string.Format("The average number of zeros is: {0}.", averageOccurences(i_outputBinaryArray, '0')));
            Console.WriteLine(string.Format("The average number of ones is: {0}.", averageOccurences(i_outputBinaryArray, '1')));
            Console.WriteLine(string.Format("There are {0} numbers that are a power of two.", countPowerOfTwo(i_outputBinaryArray)));
            Console.WriteLine(string.Format("There are {0} numbers that are a strictly montone.", countStriclyMonotone(i_outputDecimalArray)));
            Console.WriteLine(string.Format("There are {0} numbers that are a palindrome.", countPalindrome(i_outputDecimalArray)));
            Console.WriteLine(string.Format("The greatest number is: {0}.", getGreatestNum(i_outputDecimalArray)));
            Console.WriteLine(string.Format("The smallest number is: {0}.", getSmallestNum(i_outputDecimalArray)));
        }

        private static (string[], float[]) recieveUserInput()
        {
            string[] o_inputBinaryArray = new string[k_NumOfIntegers];
            float[] o_inputDecimalArray = new float[k_NumOfIntegers];
            int currentIndex = 0;

            while (currentIndex < k_NumOfIntegers)
            {
                Console.WriteLine("Please enter a valid 8 bit binary number (and then press enter)");
                string currentInput = Console.ReadLine();
                bool isInputValid = isGoodInput(currentInput);

                if (isInputValid)
                {
                    o_inputBinaryArray[currentIndex] = currentInput;
                    o_inputDecimalArray[currentIndex] = toDecimal(currentInput);
                    currentIndex++;
                }
                else
                {
                    Console.WriteLine("Unvalid input!");
                }
            }
            return (o_inputBinaryArray, o_inputDecimalArray);
        }

        private static bool isBinary(string i_userInput)
        {
            bool isBinary = true;
            foreach (char character in i_userInput)
            {
                if (character != '0' && character != '1')
                {
                    isBinary = false;
                }
            }
            return isBinary;
        }

        private static bool isGoodInput(string i_userInput)
        {
            int inputLength = i_userInput.Length;
            bool isPositive = false;
            if (inputLength > 0)
            {
                isPositive = (i_userInput[0] == '0');
            }
            return (inputLength == 8 && isPositive && isBinary(i_userInput));
        }

        private static int countOccurences(string i_userInput, char i_requiredNum)
        {
            int countOfNum = 0;
            for (int i = 0; i < i_userInput.Length; i++)
            {
                if (i_userInput[i].Equals(i_requiredNum))
                {
                    countOfNum++;
                }
            }
            return countOfNum;
        }

        private static float averageOccurences(string[] i_outputBinaryArray, char i_digit)
        {
            float numOfOccurences = 0;
            for (int i = 0; i < k_NumOfIntegers; i++)
            {
                numOfOccurences += countOccurences(i_outputBinaryArray[i], i_digit);
            }
            return (numOfOccurences / k_NumOfIntegers);
        }

        private static bool isPowerOfTwo(string i_userInput)
        {
            float userDecimal = toDecimal(i_userInput);
            
            if (userDecimal > 1)
            {
                while (userDecimal % 2 == 0)
                {
                    userDecimal = (userDecimal / 2);
                }
            }
            return (userDecimal == 1);
        }

        private static int countPowerOfTwo(string[] i_outputBinaryArray)
        {
            int countPowerTwo = 0;
            for (int i = 0; i < i_outputBinaryArray.Length; i++)
            {
                if (isPowerOfTwo(i_outputBinaryArray[i]))
                {
                    countPowerTwo++;
                }
            }
            return countPowerTwo;
        }

        private static float toDecimal(string i_userInput)
        {
            float currentOutput = 0;
            for (int i = i_userInput.Length - 1; i >= 0; i--)
            {
                if (i_userInput[i].Equals('1'))
                {
                    currentOutput += (float)Math.Pow(2, i_userInput.Length - i - 1);
                }
            }
            return currentOutput;
        }

        private static bool isPalindrome(float i_userInputDec)
        {
            string userInputDecimalString = i_userInputDec.ToString();
            char leftChar = userInputDecimalString[0];
            char rightChar = userInputDecimalString[userInputDecimalString.Length - 1];
            bool checkPalindrome = true;

            for (int i = 0; i < (userInputDecimalString.Length / 2); i++)
            {
                if (leftChar != rightChar)
                {
                    checkPalindrome = false;
                }
                leftChar = userInputDecimalString[i++];
                rightChar = userInputDecimalString[userInputDecimalString.Length - i - 1];
            }

            return checkPalindrome;
        }

        private static int countPalindrome(float[] i_outputDecimalArray)
        {
            int numOfPalindrome = 0;
            for (int i = 0; i < i_outputDecimalArray.Length; i++)
            {
                if (isPalindrome(i_outputDecimalArray[i]))
                {
                    numOfPalindrome++;
                }
            }
            return numOfPalindrome;

        }

        private static bool isStrictlyMonotone(float i_userInputDec)
        {
            bool isStrictlyMonotone = true;
            float rightDigit = -1;
            int userInputInt = (int)i_userInputDec;

            while (userInputInt > 0) 
            {
                if (userInputInt % 10 <= rightDigit)
                {
                    isStrictlyMonotone = false;
                }

                rightDigit = userInputInt % 10;
                userInputInt = userInputInt / 10;
            }
            return isStrictlyMonotone;
        }

        private static int countStriclyMonotone(float[] i_outputDecimalArray)
        {
            int countStrictlyMonotone = 0;
            for (int i = 0; i < i_outputDecimalArray.Length; i++)
            {
                if (isStrictlyMonotone(i_outputDecimalArray[i]))
                {
                    countStrictlyMonotone++;
                }
            }
            return countStrictlyMonotone;
        }

        private static float getGreatestNum(float[] i_outputDecimalArray)
        {
            return (Math.Max(Math.Max(i_outputDecimalArray[0], i_outputDecimalArray[1]), i_outputDecimalArray[2]));
        }

        private static float getSmallestNum(float[] i_outputDecimalArray)
        {
            return (Math.Min(Math.Min(i_outputDecimalArray[0], i_outputDecimalArray[1]), i_outputDecimalArray[2]));
        }
    }
}
