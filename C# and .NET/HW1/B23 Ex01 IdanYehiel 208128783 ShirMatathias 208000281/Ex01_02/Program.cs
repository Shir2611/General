using System;
using System.Text;

namespace Ex01_02
{
    public class Program
    {
        public static void Main()
        {
            printSandClock(5);
            Console.Read();
        }

        public static void printSandClock(int i_NumOfLines)
        {
            const bool v_isSandClockDescending = true;
            recursiveSandClockPrinter(v_isSandClockDescending, i_NumOfLines, 0, i_NumOfLines);
        }

        private static void recursiveSandClockPrinter(bool i_IsSandClockDescending, int i_NumOfAsterisk, int i_NumOfLine, int i_TargetNumOfLines)
        {
            StringBuilder stringBuilder = new StringBuilder();

            if (i_NumOfAsterisk == 1)
            {
                i_IsSandClockDescending = false;
            }

            if (i_NumOfLine < 0)
            {
                return;
            }

            stringBuilder.Append(' ', i_NumOfLine).Append('*', i_NumOfAsterisk);
            Console.WriteLine(stringBuilder.ToString());

            if (i_IsSandClockDescending)
            {
                recursiveSandClockPrinter(i_IsSandClockDescending, i_NumOfAsterisk - 2, i_NumOfLine + 1, i_TargetNumOfLines);
            }

            else
            {
                recursiveSandClockPrinter(i_IsSandClockDescending, i_NumOfAsterisk + 2, i_NumOfLine - 1, i_TargetNumOfLines);
            }
        }
    }
}